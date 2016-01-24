package com.vaiuu.alquran.util;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Ali PC on 6/15/2015.
 */
public class SalahTimeParser {

    public static String[] getSalahTime(String result,String date) {
         String[] namazTime = new String[20];

        Log.i("Result are",result);
        Log.i("date are ",date);

        try {
            final JSONObject mainJsonObject = new JSONObject(result);
            Appconstant.salahMonthLength=mainJsonObject.length();
            final JSONObject jsonObject=new JSONObject(mainJsonObject.getString(date));
            namazTime[0]=jsonObject.getString("fajr");
            namazTime[1]=jsonObject.getString("sunrise");
            namazTime[2]=jsonObject.getString("zuhr");
            namazTime[3]=jsonObject.getString("asr");
            namazTime[4]=jsonObject.getString("maghrib");
            namazTime[5]=jsonObject.getString("isha");
            Log.i("date are ",""+mainJsonObject.length());

        } catch (final Exception e) {
            e.printStackTrace();
        }


        return namazTime;
    }
}
