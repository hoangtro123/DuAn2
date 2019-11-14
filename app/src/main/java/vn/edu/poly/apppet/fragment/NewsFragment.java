package vn.edu.poly.apppet.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.rss.AdapterNews;
import vn.edu.poly.apppet.rss.News;


public class NewsFragment extends Fragment {

    private View n;



    private RecyclerView lvListNews;
    private AdapterNews adapterNews;
    private LinearLayoutManager layoutManager;
    private List<News> news;


    private String rssLink;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        n = inflater.inflate (R.layout.fragment_news, container, false);

        lvListNews = n.findViewById(R.id.lvListNews);
        //thêm link rss
        rssLink = "https://vnexpress.net/rss/giai-tri.rss";


        LoadRssFromInternetTask loadRssFromInternetTask = new LoadRssFromInternetTask(getActivity ());
        loadRssFromInternetTask.execute(rssLink);

        return n;
    }

    class LoadRssFromInternetTask extends AsyncTask<String, Long, List<News>> {


        private Context context;

        public LoadRssFromInternetTask(Context context) {
            this.context = context;
            Log.e("START", "START");

        }


        // ham xu ly ngam
        @Override
        protected List<News> doInBackground(String... strings) {


            ArrayList<News> newsArrayList = new ArrayList();

            try {
                URL url = new URL(strings[0]);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");


                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                 * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
                 * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
                 * so we should skip the "<title>" tag which is a child of "<channel>" tag,
                 * and take in consideration only "<title>" tag which is a child of "<item>"
                 *
                 * In order to achieve this, we will make use of a boolean variable.
                 */

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                String text = "";

                News news = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nameTag = xpp.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:

                            Log.e("Name", xpp.getName());
                            if (nameTag.equalsIgnoreCase("item")) {
                                news = new News();
                                Log.e("CREATE","NEWS");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            text = xpp.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (nameTag.equals("item"))
                                newsArrayList.add(news);
                            else if (news!=null & nameTag.equalsIgnoreCase("title"))
                                news.title = text.trim();
                            else if (news!=null & nameTag.equalsIgnoreCase("description"))
                                news.description = text.trim();
                            else if (news!=null & nameTag.equalsIgnoreCase("pubDate"))
                                news.pubDate = text.trim();
                            else if (news!=null & nameTag.equalsIgnoreCase("link"))
                                news.link = text.trim();
                            else if (news!=null & nameTag.equalsIgnoreCase ("image"))
                                news.image = text.trim ();


                            Log.e("END_TAG " + nameTag, text + "");
                            break;

                        default:
                            break;

                    }
                    eventType = xpp.next(); //move to next element
                }

                Log.e("SIZE", newsArrayList.size() + "");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsArrayList;
        }

        private InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<News> news) {
            super.onPostExecute(news);

            adapterNews = new AdapterNews(context, news);
            layoutManager = new LinearLayoutManager(context);

            lvListNews.setLayoutManager(layoutManager);
            lvListNews.setAdapter(adapterNews);


        }


    }


}
