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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vaiuu.alquran.databse.AssetDatabaseOpenHelper;
import com.vaiuu.alquran.databse.DataBaseHelper;
import com.vaiuu.alquran.util.Appconstant;
import com.vaiuu.alquran.util.SharedPreferencesHelper;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends ActionBarActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView mDrawerList;
    ViewPager pager;
    private String titles[] = new String[]{"আল-কোরান", "কিবলা", "মসজিদ অবস্থান", "নামাজের সময়"
    };
    private Toolbar toolbar;

    SlidingTabLayout slidingTabLayout;
    private Context context;
    boolean flag = true;
    private File sdCard = Environment.getExternalStorageDirectory();
    private File QuranDbFolder = new File(sdCard.getAbsolutePath()
            + Appconstant.DB_BASE_URL);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        context = this;

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
                "আজান", "তসবি", "রোজার সময়", "মসজিদ অবস্থান", "নামাজের সময়", "মসজিদ অবস্থান", "নামাজের সময়"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.blue));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 1:
                        Intent intent = new Intent(HomeActivity.this, TasbishActivity.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 2:

                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 3:

                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
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
