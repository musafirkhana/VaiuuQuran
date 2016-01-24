package com.vaiuu.alquran.main;


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

public class TasbishActivity extends ActionBarActivity {
    private android.widget.Button reset_button, set_timer;
    private ImageView counter_li;
    private TextView count_text;
    private EditText su;
    int angka = 0;
    int max = 1000;
    private AlertDialog.Builder builder;
    private SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper();
    private Context context;
    private Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih);
        context=this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_tashbiah);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(context.getResources().getString(R.string.text_tasbiah));
        initUI();
    }

    public void initUI() {
        counter_li = (ImageView) findViewById(R.id.counter_li);
        reset_button = (android.widget.Button) findViewById(R.id.reset_button);
        set_timer = (android.widget.Button) findViewById(R.id.set_timer);

        count_text = (TextView) findViewById(R.id.count_text);

        if(!sharedPreferencesHelper.getCount(context).equalsIgnoreCase("")){

            count_text.setText(sharedPreferencesHelper.getCount(context).toString());

            angka=(Integer.valueOf(sharedPreferencesHelper.getCount(context).toString()));

        }
        //count_button.setOnClickListener(cListener);
        counter_li.setOnClickListener(cListener);
        reset_button.setOnClickListener(rListener);
        set_timer.setOnClickListener(setClick);

    }


    public void Back(View v){
        finish();
    }
    //method tombol count
    private View.OnClickListener cListener = new View.OnClickListener() {


        public void onClick(View v) {



            count_text.setText(String.valueOf(++angka));
            if (angka == max) {
                stopActivity();
                Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v1.vibrate(300);
                Toast.makeText(TasbishActivity.this, "alhamdulilah  " + max + " Reach", Toast.LENGTH_LONG).show();

            }
        }
    };
    private View.OnClickListener setClick = new View.OnClickListener() {


        public void onClick(View v) {
            Toast.makeText(TasbishActivity.this, "Saved", Toast.LENGTH_LONG).show();
            sharedPreferencesHelper.setCount(context, count_text.getText().toString());
            TasbishActivity.this.finish();
        }
    };
    //method  reset
    private View.OnClickListener rListener = new View.OnClickListener() {


        public void onClick(View v) {
            sharedPreferencesHelper.setCount(context,"0");
            count_text.setText(String.valueOf(angka = 0));
            counter_li.setOnClickListener(cListener);
        }
    };

    //stop count listener
    public void stopActivity() {
        counter_li.setOnClickListener(null);
    }


    //method tombol oke di layout others
    public void om_mput(View view) {
        max = Integer.valueOf(su.getText().toString()).intValue();
        this.setContentView(R.layout.activity_tasbih);
        initUI();
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            TasbishActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
