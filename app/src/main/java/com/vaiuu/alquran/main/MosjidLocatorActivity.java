package com.vaiuu.alquran.main;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vaiuu.alquran.util.CustomProgressDialog;
import com.vaiuu.alquran.util.GPSTracker;
import com.vaiuu.alquran.util.PlaceJSONParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MosjidLocatorActivity extends ActionBarActivity implements LocationListener {

    private GoogleMap mMap;
    double mLatitude = 0;
    double mLongitude = 0;
    private Context context;
    private GPSTracker gpsTracker = null;
    private Toolbar mToolbar;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosjid_locator);
        context = this;
        bannerADD();

        customProgressDialog = new CustomProgressDialog(context, "Loading Location...", true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_musjid);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(context.getResources().getString(R.string.text_mosjid));

        gpsTracker = new GPSTracker(context);
        setUpMapIfNeeded();
    }
    private void bannerADD(){
        AdView mAdView = (AdView) findViewById(R.id.mosjid_locator_addview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    /**
     * *Setup Google Map***********
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (gpsTracker.canGetLocation()) {
            PlacesTask placesTask = new PlacesTask();
            placesTask.execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                    "location=" +
                    gpsTracker.getLatitude() +
                    "," +
                    gpsTracker.getLongitude() +
                    "&radius=5000&types=mosque&sensor=true&key=" +
                    "AIzaSyBqAN-YHlzFT9zxvIJpisDoBFhNI_BiX34" +
                    "");
            if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

                int requestCode = 10;
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
                dialog.show();

            } else {

                SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mMap = fragment.getMap();
                mMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);

                if (location != null) {
                    onLocationChanged(location);
                }

                locationManager.requestLocationUpdates(provider, 20000, 0, this);
            }
        } else {
            gpsTracker.showSettingsAlert();
        }

    }

    /**
     * **************************Get Google Api Return String***********
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }


    /**
     * **************  Google Api  Async Task*****************
     */
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
                Log.i("Data Are ", "" + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            customProgressDialog.dismiss();
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }

    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;



        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
            try {
                jObject = new JSONObject(jsonData[0]);
                places = placeJsonParser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            mMap.clear();

            for (int i = 0; i < list.size(); i++) {

                // Creating a marker
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.mosque);
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = list.get(i);
                final double lat = Double.parseDouble(hmPlace.get("lat"));
                final double lng = Double.parseDouble(hmPlace.get("lng"));
                String name = hmPlace.get("place_name");
                final String vicinity = hmPlace.get("vicinity");
                LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng).icon(icon);
                markerOptions.title(name + " : " + vicinity);
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        try {
                            drawLine(gpsTracker.getLatitude(),gpsTracker.getLongitude(),lat,lng,R.id.map,"You Are Here",vicinity);
                        } catch (Exception e) {
                        }

                        return true;
                    }

                });
            }

        }

    }


    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }

    public void onProviderDisabled(String provider) {

    }


    public void onProviderEnabled(String provider) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            MosjidLocatorActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /****************************************************************************************************
     * Draw Path into map From source to Destination
     ****************************************************************************************************/

    public void drawLine(double fromLat, double fromLong, double tolat, double toLong, int map, String sourceTitle, String destinationTitle) {
        ArrayList<LatLng> markerPoints;
        markerPoints = new ArrayList<LatLng>();
        mMap = ((MapFragment) getFragmentManager().findFragmentById(map)).getMap();
        //mMap.clear();
        LatLng positionFrom;
        LatLng positionTo;
        positionTo = new LatLng(tolat, toLong);
        positionFrom = new LatLng(fromLat, fromLong);
        mMap.addMarker(new MarkerOptions().position(positionTo)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title(destinationTitle));
        // For Location
        Log.i("Lat Long Are ", fromLat + "  Long   " + fromLong);
        mMap.setMyLocationEnabled(true);
        positionFrom = new LatLng(fromLat, fromLong);
        double distance = CalculationByDistance(positionFrom, positionTo);
        String str = " (" + String.valueOf(distance) + " meters)";
        mMap.addMarker(new MarkerOptions().position(positionFrom)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(sourceTitle).
                        snippet("Distance Away " + String.format("%.2f", distance) + " KM")).showInfoWindow();


            // Move the camera instantly to hamburg with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionFrom, 20));
            // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        if (markerPoints.size() > 1) {
            mMap.clear();
        }
        String url = getDirectionsUrl(fromLat, fromLong, tolat, toLong);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }
    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }
    private String getDirectionsUrl(double fromLat, double fromLong, double tolat, double toLong) {
        // Origin of route
        /*
         * String str_origin = "origin=" + origin.latitude + "," +
		 * origin.longitude;
		 */
        String str_origin = "origin=" + fromLat + "," + fromLong;
        // Destination of route
        // String str_dest = "destination=" + dest.latitude + "," +
        // dest.longitude;
        String str_dest = "destination=" + tolat + "," + toLong;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}
