package com.vaiuu.alquran.main;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.vaiuu.alquran.adapter.LeftDrawerAdapter;
import com.vaiuu.alquran.databse.AssetDatabaseOpenHelper;
import com.vaiuu.alquran.databse.DataBaseHelper;
import com.vaiuu.alquran.util.Appconstant;
import com.vaiuu.alquran.util.SharedPreferencesHelper;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends ActionBarActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    InterstitialAd mInterstitialAd;

    private ListView mDrawerList;
    ViewPager pager;
    private String titles[] = new String[]{"আল-কোরান", "কিবলা", "নামাজের সময়","আল্লাহর নাম","কালিমা"};
    private Toolbar toolbar;

    SlidingTabLayout slidingTabLayout;
    private Context context;
    boolean flag = true;
    private File sdCard = Environment.getExternalStorageDirectory();
    private File QuranDbFolder = new File(sdCard.getAbsolutePath()
            + Appconstant.DB_BASE_URL);
    private LeftDrawerAdapter leftDrawerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        context = this;
        bannerADD();


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2958576242618647/6786470419");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });

        requestNewInterstitial();
        if (!QuranDbFolder.exists()) {
            QuranDbFolder.mkdirs();
        }
        flag = SharedPreferencesHelper.getFirstTime(context);
        new LoaddbAsyncTask().execute();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }
        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));

        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        String[] values = new String[]{
                "আজান", "তসবি", "মসজিদ অবস্থান", "নামাজের জন্যে সূরা", "ছালাতের সংক্ষিপ্ত নিয়ম", "যরূরী দো‘আ সমূহ"
        };
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);*/
        leftDrawerAdapter=new LeftDrawerAdapter(this,values);
        mDrawerList.setAdapter(leftDrawerAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent1=new Intent(context,WebviewActivity.class);
                        intent1.putExtra("DETAIL", getResources().getString(R.string.azan_text));
                        intent1.putExtra("HEADER", getResources().getString(R.string.azan));
                        startActivity(intent1);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 1:
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        Intent intent = new Intent(HomeActivity.this, TasbishActivity.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);

                        break;
                    case 2:
                        Intent mLocator = new Intent(HomeActivity.this, MosjidLocatorActivity.class);
                        startActivity(mLocator);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);

                        break;
                    case 3:
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        Intent sura = new Intent(HomeActivity.this, NamazerSuraActivity.class);
                        startActivity(sura);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);

                        break;
                    case 4:
                        Intent intent5=new Intent(context,WebviewActivity.class);
                        intent5.putExtra("DETAIL", getResources().getString(R.string.salah_detail));
                        intent5.putExtra("HEADER", getResources().getString(R.string.salah));
                        startActivity(intent5);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 5:
                        Intent intent6=new Intent(context,WebviewActivity.class);
                        intent6.putExtra("DETAIL", getResources().getString(R.string.dua_detail));
                        intent6.putExtra("HEADER", getResources().getString(R.string.dua));
                        startActivity(intent6);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                }

            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    private void bannerADD(){
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    class LoaddbAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... aurl) {
            loadDataBase();
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        // ALL List POPUP Button setup here
        @SuppressLint("NewApi")
        protected void onPostExecute(String getResult) {
            AssetDatabaseOpenHelper databaseOpenHelper = new AssetDatabaseOpenHelper(
                    context);
            SQLiteDatabase db = databaseOpenHelper.openDatabase();
            Cursor cursor = db
                    .rawQuery("select * from quran_sura_ayah;", null);
            Log.i("MyApp", "cnt: " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {

                } while (cursor.moveToNext());
            }
            db.close();


        }

    }

    public void loadDataBase() {

        DataBaseHelper myDbHelper = new DataBaseHelper(context);
        myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new RuntimeException(ioe);

        }

        try {

            try {
                myDbHelper.openDataBase();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            //if (flag) {
                myDbHelper.copyDataBase();
           // }

        } catch (SQLException sqle) {

            throw new Error(sqle);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
