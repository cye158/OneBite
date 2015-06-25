package com.ironsquishy.biteclub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
/**
 * Created by Allen Space on 6/22/2015.
 */
public class Randomizer  {

    /*** Data Fields*/
    private Thread mThread;
    //private SQLiteHandlers
    private static String mReturnString;
    private static String mFindString;
    //private static GoogleApiClient mGoogleApiClient;

    //================================
    //***Constructors*****
    //================================
    //***Setters**********
    //================================
    //***Getters***********
    //================================
    //***Helpers***********
    //================================
    //***Thread run********
    //================================

    /***
     * @author Allen Space
     * */
    public Randomizer(Context context)
    {
        /*mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) context)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) context)
                .addApi(LocationServices.API)
                .build();*/
    }


    /***
     * @author Allen Space
     * */
    public static void setmReturnString(String mReturnString) {
        Randomizer.mReturnString = mReturnString;
    }

    /**
     * @author Allen Space
     * */
    public static void setmFindString(String mFindString) {
        Randomizer.mFindString = mFindString;
    }

    protected synchronized void buildGoogleApiClient()
    {

    }
    /**
     * @author Allen Space
     * *
    @Override
    public void run()
    {

    }*/
}
