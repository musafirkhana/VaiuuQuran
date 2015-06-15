package com.vaiuu.alquran.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vaiuu.alquran.main.R;


public class QiblaFragment extends Fragment implements SensorEventListener{

    private static final String ARG_POSITION = "position";

    private int position;

private Context context;
    private TextView txt_no_sensor;
    private LinearLayout /*lin_macca,*/lin_ivarrow;
    private RelativeLayout compass_relative;
    // private Button mGotoButton;
    private double Degree = 0;
    // define the display assembly compass picture
    private ImageView imageViewCompass, ivarrow/*,img_macca*/;
    private double kilometer = 0;
    private Location sourceLocation, destLocation;
    // record the compass picture angle turned
    private float currentDegree = 0f;
    private float eventDegree = 0;
    private float arrowIni = 0;
    private float arrowMacca=0;
    // device sensor manager
    private SensorManager mSensorManager;
    public static QiblaFragment newInstance(int position) {
        QiblaFragment f = new QiblaFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.qibla_mainfragment, container, false);

        context = getActivity();
        initUI(rootView);
        bearing();

        if(isCompassSensorAvailable()){
            compass_relative.setVisibility(View.VISIBLE);
            txt_no_sensor.setVisibility(View.GONE);
        }else{
            compass_relative.setVisibility(View.GONE);
            txt_no_sensor.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private void initUI(View view) {
        imageViewCompass = (ImageView)view.findViewById(R.id.imageViewCompass);
        lin_ivarrow= (LinearLayout)view.findViewById(R.id.lin_ivarrow);
        compass_relative=(RelativeLayout)view.findViewById(R.id.compass_relative);
        txt_no_sensor = (TextView) view.findViewById(R.id.txt_no_sensor);



        RotateAnimation ra1 = new RotateAnimation(0,(float) Degree,Animation.RELATIVE_TO_SELF, 0.6f, Animation.RELATIVE_TO_SELF,0.4f); // how long the animation will take place
        RotateAnimation ra12 = new RotateAnimation((float)-Degree, 0,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        // set the animation after the end of the reservation status
        ra1.setFillAfter(true);
        ra12.setFillAfter(true);

        if (ivarrow != null) {
            if (ra1 != null) {
                ivarrow.startAnimation(ra1);
            }
        }
        currentDegree = (float) Degree;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
    }

    @SuppressLint("NewApi")
    public void setInitRotaion()
    {
        if (Build.VERSION.SDK_INT < 11) {
            RotateAnimation animation = new RotateAnimation(0, 180);
            animation.setDuration(100);
            animation.setFillAfter(true);
            lin_ivarrow.startAnimation(animation);
        } else {
            lin_ivarrow.setRotation(180);
        }
    }
    public boolean isCompassSensorAvailable()
    {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            // Success! There's a magnetometer.
            return true;
        } else {
            // Failure! No magnetometer.
            return false;
        }
    }
    public void onSensorChanged(SensorEvent event) {
        // // get the angle around the z-axis rotated
        eventDegree = Math.round(event.values[0]);
        RotateAnimation rotateAnimation = new RotateAnimation((currentDegree), -eventDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        // how long the animation will take place
        rotateAnimation.setDuration(210);
        // set the animation after the end of the reservation status
        rotateAnimation.setFillAfter(true);
        currentDegree = -eventDegree;
        // Start the animation
        imageViewCompass.startAnimation(rotateAnimation);
        float diffDegree=(float) (Degree - eventDegree);
        // to rotate macca image
        RotateAnimation rotateAnimation2 = new RotateAnimation((arrowIni),diffDegree, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation2.setDuration(100);
        lin_ivarrow.startAnimation(rotateAnimation2);
        Handler rotationHandler=new Handler();
        rotationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RotateAnimation rotateAnimation= new RotateAnimation((arrowMacca),eventDegree+90,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                arrowMacca=eventDegree+90;
                rotateAnimation.setDuration(1);
                rotateAnimation.setFillAfter(true);
            }
        }, rotateAnimation2.getDuration());
        // set the animation after the end of the reservation status
        rotateAnimation2.setFillAfter(true);
        arrowIni = (float) Degree - eventDegree;
        // If we don't have a Location, we break out
        if (sourceLocation == null)
            return;
        float azimuth = event.values[0];
        GeomagneticField geoField = new GeomagneticField(Double.valueOf(
                sourceLocation.getLatitude()).floatValue(), Double.valueOf(
                sourceLocation.getLongitude()).floatValue(), Double.valueOf(
                sourceLocation.getAltitude()).floatValue(),
                System.currentTimeMillis());
        azimuth += geoField.getDeclination(); // converts magnetic north into
        // Correct the azimuth
        azimuth = azimuth % 360;
        // This is where we choose to point it
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();
        if (mSensorManager != null) {
            // for the system's orientation sensor registered listeners
            mSensorManager.registerListener( this,mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            // to stop the listener and save battery
            mSensorManager.unregisterListener(this);
        }
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
    public void bearing() {
        //		Mecca Lat log
        double destLat = 21.416667;
        double destLng = 39.816667;
        double sourceLat = 0;
        double sourceLng = 0;
        sourceLocation = new Location("reverseGeocoded");
        sourceLocation.setLatitude(23.0395677);
        sourceLocation.setLongitude(72.5660045);
        destLocation = new Location("reverseGeocoded");
        destLocation.setLatitude(destLat);
        destLocation.setLongitude(destLng);
        // Store the bearingTo in the bearTo variable
        float bearTo = sourceLocation.bearingTo(destLocation);
        // If the bearTo is smaller than 0, add 360 to get the rotation
        // clockwise.
        if (bearTo < 0) {
            bearTo = bearTo + 360;
        }
        Degree = bearTo;
        kilometer =  (float)kilometers(destLat, destLng, sourceLat, sourceLng);
    }
    private double kilometers (double lat1, double long1, double lat2, double long2){
        double degToRad= Math.PI / 180.0;
        double phi1 = lat1 * degToRad;
        double phi2 = lat2 * degToRad;
        double lam1 = long1 * degToRad;
        double lam2 = long2 * degToRad;
        return 6371.01 * Math.acos( Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(lam2 - lam1) );
    }


}