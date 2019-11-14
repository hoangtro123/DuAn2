package vn.edu.poly.apppet.rss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.bumptech.glide.Glide;

import vn.edu.poly.apppet.R;

public class AdapterNews extends RecyclerView.Adapter<HolderNews> {

    private static final String TAG = "IMG" ;
    private Context context;

    private List<News> news;


    public AdapterNews(Context context, List<News> news) {
        this.context = context;
        this.news = news;

    }



    @NonNull
    @Override
    public HolderNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);

        return new HolderNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderNews holder, int position) {
        final News itemNews = news.get(position);



        holder.tvTitle.setText(itemNews.title);
        holder.tvDate.setText(itemNews.pubDate);
        holder.tvDes.setText(itemNews.description);




        // load image to ImageView by Glide library
        Glide.with(context).load(itemNews.link).into(holder.imgThumbs);

        String duongdan =news.get(position).image;
        Log.e ("linkImage", duongdan);
        Taihinhanh taihinhanh =new Taihinhanh();
        taihinhanh.execute(duongdan);
        Glide.with(context).load(duongdan).into(holder.imgThumbs);



        try {
            holder.imgThumbs.setImageBitmap(taihinhanh.get ());


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



//
        holder.tvTitle.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, WebView.class);
                intent1.putExtra ("link", itemNews.link);

                context.startActivity (intent1);

            }
        });




    }

    @Override
    public int getItemCount() {
        if (news == null) return 0;
        return news.size();
    }



    public Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }


    public class Taihinhanh extends AsyncTask<String,Void,Bitmap> {

        Bitmap hinhanh;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                hinhanh = BitmapFactory.decodeStream(inputStream);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return hinhanh;
        }
    }

}
