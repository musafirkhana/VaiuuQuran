package com.vaiuu.alquran.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class WebviewActivity extends ActionBarActivity {


    private String DETAIL = "";
    private String HEADER = "";
    private TextView header;
    private Toolbar mToolbar;

    /**
     * Called when the activity is first created.
     */
    @SuppressWarnings("static-access")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_webview);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");
        bannerADD();

        DETAIL = getIntent().getStringExtra("DETAIL");
        HEADER = getIntent().getStringExtra("HEADER");
        header = (TextView) findViewById(R.id.header);
        header.setText(HEADER);
        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, DETAIL, "text/html", "UTF-8", null);

//			webView.loadUrl("file:///android_asset/speach.htm");
//			mPlayer.start();


    }

    private void bannerADD(){
        AdView mAdView = (AdView) findViewById(R.id.adView_webview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // this.finish();
        // stopAudio();
        super.onResume();
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            WebviewActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
