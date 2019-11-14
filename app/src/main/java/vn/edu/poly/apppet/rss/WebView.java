package vn.edu.poly.apppet.rss;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import vn.edu.poly.apppet.R;

public class WebView extends AppCompatActivity {

    private android.webkit.WebView browser;

    private String rlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        rlink = getIntent ().getStringExtra ("link");

        Toast.makeText (this, rlink, Toast.LENGTH_SHORT).show ();

        browser = findViewById(R.id.webkit);
        browser.loadUrl(rlink);
        browser.getSettings().setJavaScriptEnabled(false);

    }

}
