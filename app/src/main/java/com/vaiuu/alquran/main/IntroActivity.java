package com.vaiuu.alquran.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.vaiuu.alquran.util.Appconstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class IntroActivity extends ActionBarActivity {
    private String bismilahPath = android.os.Environment
            .getExternalStorageDirectory()
            + File.separator
            + Appconstant.folderName;

        Handler h;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            File file = new File(bismilahPath + File.separator + "bismillah.mp3");
            if (!file.exists()) {
                final int mSongs = R.raw.bismillah;
                try {
                    CopyRAWtoSDCard(mSongs, bismilahPath + File.separator
                            + "bismillah.mp3");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }


            h = new Handler();
            h.postDelayed(irun, 4000);
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



    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

}

