package com.vaiuu.alquran.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaiuu.alquran.main.R;
import com.vaiuu.alquran.util.AlertDialogHelper;
import com.vaiuu.alquran.util.Appconstant;
import com.vaiuu.alquran.util.CustomProgressDialog;
import com.vaiuu.alquran.util.GPSTracker;
import com.vaiuu.alquran.util.HTTPHandler;
import com.vaiuu.alquran.util.SalahTimeParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SalahTimeFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private Context context;
    private CustomProgressDialog customProgressDialog;
    private String respones_results;
    private GPSTracker gpsTracker;
    Calendar now = Calendar.getInstance();
    private int date = now.get(Calendar.DAY_OF_MONTH);
    /**
     * ******* Declare UI Variable*********
     */
    private TextView tvFajrNamgeTime;
    private TextView tvSuriseNamgeTime;
    private TextView tvDuhurNamgeTime;
    private TextView tvAsrNamgeTime;
    private TextView tvMaghribNamgeTime;
    private TextView tvIshaNamgeTime;
    private TextView dateTextview;
    private TextView month_textview;
    private ImageView leftArrowImageView;
    private ImageView rightArrowImageView;


    public static SalahTimeFragment newInstance(int position) {
        SalahTimeFragment f = new SalahTimeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.salahtime_fragment, container, false);

        context = getActivity();
        customProgressDialog = new CustomProgressDialog(context, "Loading", true);
        gpsTracker = new GPSTracker(context);

        initUI(rootView);


        return rootView;
    }

    private void initUI(View view) {

        Log.i("salahURL Are ", ""+salahURL(""+now.get(Calendar.YEAR), ""+now.get(Calendar.MONTH)));

            new loadSalahTime().execute(salahURL(""+now.get(Calendar.YEAR), ""+(now.get(Calendar.MONTH)+1)));


        tvFajrNamgeTime = (TextView) view.findViewById(R.id.tvFajrNamgeTime);
        tvSuriseNamgeTime = (TextView) view.findViewById(R.id.tvSuriseNamgeTime);
        tvDuhurNamgeTime = (TextView) view.findViewById(R.id.tvDuhurNamgeTime);
        tvAsrNamgeTime = (TextView) view.findViewById(R.id.tvAsrNamgeTime);
        tvMaghribNamgeTime = (TextView) view.findViewById(R.id.tvMaghribNamgeTime);
        tvIshaNamgeTime = (TextView) view.findViewById(R.id.tvIshaNamgeTime);
        dateTextview = (TextView) view.findViewById(R.id.dateTextview);
        month_textview = (TextView) view.findViewById(R.id.month_textview);
        leftArrowImageView = (ImageView) view.findViewById(R.id.leftArrowImageView);
        rightArrowImageView = (ImageView) view.findViewById(R.id.rightArrowImageView);
        setDateMonth();


        rightArrowImageView.setOnClickListener(clickListener);
        leftArrowImageView.setOnClickListener(clickListener);

    }

    JSONObject mainJsonObject = null;
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                mainJsonObject = new JSONObject(Appconstant.getSalahResponse);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            switch (v.getId()) {
                case R.id.rightArrowImageView:

                    date = date + 1;
                    if (date <= mainJsonObject.length()) {
                        dateTextview.setText("" + date);
                        month_textview.setText("" + new SimpleDateFormat("MMM").format(now.getTime()) + "  " + now.get(Calendar.YEAR));
                        setData(SalahTimeParser.getSalahTime(Appconstant.getSalahResponse, String.valueOf(date)));
                    } else {
                        AlertDialogHelper.showAlert(context, "End Of the month");
                    }

                    break;
                case R.id.leftArrowImageView:
                    date = date - 1;
                    if (date >= 0) {
                        dateTextview.setText("" + date);
                        month_textview.setText("" + new SimpleDateFormat("MMM").format(now.getTime()) + "  " + now.get(Calendar.YEAR));
                        setData(SalahTimeParser.getSalahTime(Appconstant.getSalahResponse, String.valueOf(date)));
                    } else {
                        AlertDialogHelper.showAlert(context, "End Of the month");
                    }

                    break;
            }
        }
    };

    private void setDateMonth() {
        dateTextview.setText("" + now.get(Calendar.DAY_OF_MONTH));
        month_textview.setText("" + (new SimpleDateFormat("MMM").format(now.getTime()) + "  " + now.get(Calendar.YEAR)));
    }

    private void setData(String[] data) {
        tvFajrNamgeTime.setText(data[0]);
        tvSuriseNamgeTime.setText(data[1]);
        tvDuhurNamgeTime.setText(data[2]);
        tvAsrNamgeTime.setText(data[3]);
        tvMaghribNamgeTime.setText(data[4]);
        tvIshaNamgeTime.setText(data[5]);

    }

    class loadSalahTime extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {

            try {
                respones_results = HTTPHandler.GetDataFromURL(aurl[0]);
                Appconstant.getSalahResponse = respones_results;
                Log.i("respones_results Are", respones_results);
                //if (SuraListParser.connect(getActivity().getApplicationContext(), aurl[0])) ;

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
            customProgressDialog.dismiss();
            setData(SalahTimeParser.getSalahTime(respones_results, "" + now.get(Calendar.DAY_OF_MONTH)));


        }

    }

    private String salahURL(String YEAR,String MONTH) {

        String URL="http://api.xhanch.com/islamic-get-prayer-time.php?" +
                "lng=" +
               "90.4125180" +
                "&lat=" +
                "23.8103320" +
                "&yy=" +
                YEAR +
                "&mm=" +
                MONTH +
                "&gmt=6" +
                "&m=json";
        return URL;


    }
}