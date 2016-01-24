package com.vaiuu.alquran.main;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vaiuu.alquran.util.SharedPreferencesHelper;

public class KalimaDetailActivity extends ActionBarActivity {

    private Context context;
    private TextView arabic_text;
    private TextView pronunciation_text;
    private TextView translation_text;
    private TextView engtranslation_text;
    public String kalima_detail[];
    private String kalimaTitle = "";
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalima_detail);
        context = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar_privacypolicy);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);



        kalimaTitle = getIntent().getStringExtra("title");
        if (kalimaTitle.equalsIgnoreCase("kalima1")) {
            kalima_detail = context.getResources().getStringArray(R.array.kalima_1);
            ab.setTitle(context.getResources().getString(R.string.kalima_1));
        } else if (kalimaTitle.equalsIgnoreCase("kalima2")) {
            kalima_detail = context.getResources().getStringArray(R.array.kalima_2);
            ab.setTitle(context.getResources().getString(R.string.kalima_2));
        } else if (kalimaTitle.equalsIgnoreCase("kalima3")) {
            kalima_detail = context.getResources().getStringArray(R.array.kalima_3);
            ab.setTitle(context.getResources().getString(R.string.kalima_3));
        } else if (kalimaTitle.equalsIgnoreCase("kalima4")) {
            kalima_detail = context.getResources().getStringArray(R.array.kalima_4);
            ab.setTitle(context.getResources().getString(R.string.kalima_4));
        } else if (kalimaTitle.equalsIgnoreCase("kalima5")) {
            kalima_detail = context.getResources().getStringArray(R.array.kalima_5);
            ab.setTitle(context.getResources().getString(R.string.kalima_5));
        }

        initUI();
    }

    public void initUI() {

        arabic_text = (TextView) findViewById(R.id.arabic_text);
        pronunciation_text = (TextView) findViewById(R.id.pronunciation_text);
        translation_text = (TextView) findViewById(R.id.translation_text);
        engtranslation_text = (TextView) findViewById(R.id.engtranslation_text);
        setData();

    }

    private void setData() {

        arabic_text.setText(kalima_detail[0]);
        pronunciation_text.setText(kalima_detail[1]);
        translation_text.setText(kalima_detail[2]);
        engtranslation_text.setText(kalima_detail[3]);
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            KalimaDetailActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
