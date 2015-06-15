package com.vaiuu.alquran.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vaiuu.alquran.adapter.SuraListAdapter;
import com.vaiuu.alquran.databse.AssetDatabaseOpenHelper;
import com.vaiuu.alquran.holder.AllSuraDetailList;
import com.vaiuu.alquran.main.QuranPlayerActivity;
import com.vaiuu.alquran.main.R;
import com.vaiuu.alquran.model.SuraDetailModel;
import com.vaiuu.alquran.parser.SuraListParser;
import com.vaiuu.alquran.util.Appconstant;

import java.io.IOException;
import java.io.InputStream;


public class QuranFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    private ProgressDialog progressDialog;
    private SuraListAdapter suraListAdapter;
    private Context context;
    private ListView sura_listview;
    private AssetDatabaseOpenHelper databaseOpenHelper;

    public static QuranFragment newInstance(int position) {
        QuranFragment f = new QuranFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.quran_mainfragment, container, false);
        progressDialog = new ProgressDialog(getActivity());
        context = getActivity();
        initUI(rootView);
        return rootView;
    }

    private void initUI(View view) {
        sura_listview = (ListView) view.findViewById(R.id.sura_listview);
        new loadQuranList().execute(loadJSONFromAsset("sura_list.txt"));

        sura_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v,
                                    int position, long arg3) {
                Log.i("Position Are  ", "" + (position + 1));
                new LoaddbAsyncTask().execute("" + (position + 1));
                Appconstant.suraPosition = position + 1;

            }
        });
    }

    public String loadJSONFromAsset(String jsonFileName) {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open(jsonFileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    class loadQuranList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading  Please Wait ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            Log.i("Position Are", aurl[0]);
            try {

                if (SuraListParser.connect(getActivity().getApplicationContext(), aurl[0])) ;

            } catch (final Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        // ALL List POPUP Button setup here
        @SuppressLint("NewApi")
        protected void onPostExecute(String getResult) {
            progressDialog.dismiss();
            suraListAdapter = new SuraListAdapter(context);
            sura_listview.setAdapter(suraListAdapter);
            suraListAdapter.setNotifyOnChange(true);


        }

    }

    AllSuraDetailList allSuraDetailList = new AllSuraDetailList();
    int index = 0;

    class LoaddbAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            index = 0;
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("Loading Database Please Wait ...");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            Log.i("Position Are", aurl[0]);

            if (aurl[0].equalsIgnoreCase("1")) {
                suraFatiha(aurl[0]);
            } else {
                databaseOpenHelper = new AssetDatabaseOpenHelper(context);
                SQLiteDatabase db = databaseOpenHelper.openDatabase();
                Cursor cursor = db.rawQuery(
                        "select * from quran_sura_ayah where sura ='"
                                + Integer.parseInt(aurl[0]) + "';", null);

                // final Vector<HomeListModel> homeListModels = new
                // Vector<HomeListModel>();

                allSuraDetailList.removeSuraDetailList();
                firstData();

                if (cursor.moveToFirst()) {
                    do {
                        if (index == 0) {
                            index = 1;

                            SuraDetailModel detailModel = new SuraDetailModel();
                            String info = cursor.getString(2);
                            String info2 = cursor.getString(4);
                            info = info.replace( getActivity().getResources().getString(R.string.bismillag_arb), "");
                            info2 = info2 .replace( getActivity().getResources().getString(R.string.bismillah_eng), "");
                            detailModel.setArabicText(info);
                            detailModel.setBngText(info2);

                            allSuraDetailList.setSuraDetailList(detailModel);
                            detailModel = null;
                        } else {
                            SuraDetailModel detailModel = new SuraDetailModel();
                            detailModel.setArabicText(cursor.getString(2));
                            detailModel.setEngText(cursor.getString(3));
                            detailModel.setBngText(cursor.getString(4));

                            allSuraDetailList.setSuraDetailList(detailModel);
                            detailModel = null;
                        }

                    } while (cursor.moveToNext());
                }
                db.close();

            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        // ALL List POPUP Button setup here
        @SuppressLint("NewApi")
        protected void onPostExecute(String getResult) {
//            progressDialog.dismiss();

            Intent intent = new Intent(getActivity(),
                    QuranPlayerActivity.class);
            startActivity(intent);

        }

    }

    public void suraFatiha(String aurl) {

        databaseOpenHelper = new AssetDatabaseOpenHelper(context);
        SQLiteDatabase db = databaseOpenHelper.openDatabase();
        Cursor cursor = db.rawQuery( "select * from quran_sura_ayah where sura ='" + Integer.parseInt(aurl) + "';", null);

        allSuraDetailList.removeSuraDetailList();

        if (cursor.moveToFirst()) {
            do {

                SuraDetailModel detailModel = new SuraDetailModel();
                detailModel.setArabicText(cursor.getString(2));
                detailModel.setEngText(cursor.getString(3));
                detailModel.setBngText(cursor.getString(4));
                allSuraDetailList.setSuraDetailList(detailModel);
                detailModel = null;

            } while (cursor.moveToNext());
        }
        db.close();

    }

    public void firstData() {
        SuraDetailModel detailModel = new SuraDetailModel();
        detailModel.setArabicText(""+getActivity().getResources().getString(R.string.bismillag_arb));
        detailModel .setBngText("" + getActivity().getResources().getString(R.string.bismillah_eng));

        allSuraDetailList.setSuraDetailList(detailModel);
    }

}