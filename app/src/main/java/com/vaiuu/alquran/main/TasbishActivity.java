package com.vaiuu.alquran.main;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vaiuu.alquran.util.SharedPreferencesHelper;

public class TasbishActivity extends Activity {
    private android.widget.Button reset_button, set_timer;
    private ImageView counter_li;
    private TextView count_text;
    private EditText su;
    int angka = 0;
    int max = 1000;
    private AlertDialog.Builder builder;
    private SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper();
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih);
        context=this;
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
            sharedPreferencesHelper.setCount(context,count_text.getText().toString());
        }
    };
    //method  reset
    private View.OnClickListener rListener = new View.OnClickListener() {


        public void onClick(View v) {
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


    public void exit() {
        this.finish();
    }
}
