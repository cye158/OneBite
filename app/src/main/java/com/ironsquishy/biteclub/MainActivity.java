package com.ironsquishy.biteclub;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import apiHelpers.LocationHandler;


public class MainActivity extends ActionBarActivity{

    private static LocationHandler mLocation;
    private static double mLongitude;
    private static double mLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLocation = new LocationHandler(this);

        Log.i("OnCreate" , "Successful call.");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mLocation.startConnect();

        Log.i("OnStart", "Successful");
    }
}
