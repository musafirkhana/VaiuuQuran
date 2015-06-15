package com.vaiuu.alquran.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class IntroActivity extends ActionBarActivity {


        Handler h;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intro);



            h = new Handler();
            h.postDelayed(irun, 4000);
            Toast.makeText(getApplicationContext(), "로딩중입니다", Toast.LENGTH_SHORT).show();
        }

        Runnable irun = new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(IntroActivity.this, HomeActivity.class);
                startActivity(i);
                finish();

            }
        };

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            h.removeCallbacks(irun);


        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

