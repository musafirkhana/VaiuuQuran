package com.vaiuu.alquran.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NamazerSuraActivity extends ActionBarActivity {

    private Context context;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namazsura);
        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_mushjid);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(context.getResources().getString(R.string.txt_namaz_sura));

    }

    public void SURA1(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL", getResources().getString(R.string.detail_1));
        intent.putExtra("HEADER", getResources().getString(R.string.sura_1));
        startActivity(intent);
    }
    public void SURA2(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL", getResources().getString(R.string.detail_2));
        intent.putExtra("HEADER", getResources().getString(R.string.sura_2));
        startActivity(intent);
    }
    public void SURA3(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_3));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_3));
        startActivity(intent);
    }
    public void SURA4(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_4));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_4));
        startActivity(intent);
    }
    public void SURA5(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_5));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_5));
        startActivity(intent);
    }
    public void SURA6(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_6));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_6));
        startActivity(intent);
    }
    public void SURA7(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_7));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_7));
        startActivity(intent);
    }
    public void SURA8(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_8));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_8));
        startActivity(intent);
    }
    public void SURA9(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_9));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_9));
        startActivity(intent);
    }
    public void SURA10(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_10));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_10));
        startActivity(intent);
    }
    public void SURA11(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_11));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_11));
        startActivity(intent);
    }
    public void SURA12(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_12));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_12));
        startActivity(intent);
    }
    public void SURA13(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_13));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_13));
        startActivity(intent);
    }
    public void SURA14(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_14));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_14));
        startActivity(intent);
    }
    public void SURA15(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_15));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_15));
        startActivity(intent);
    }
    public void SURA16(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_16));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_16));
        startActivity(intent);
    }
    public void SURA17(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_17));
        intent.putExtra("HEADER",getResources().getString(R.string.sura_17));
        startActivity(intent);
    }
    public void SURA18(View view){
        Intent intent=new Intent(this,WebviewActivity.class);
        intent.putExtra("DETAIL",getResources().getString(R.string.detail_18));
        intent.putExtra("HEADER", getResources().getString(R.string.sura_18));
        startActivity(intent);
    }



    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NamazerSuraActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
