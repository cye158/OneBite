package com.ironsquishy.biteclub;

import android.content.Context;

import java.util.Random;

/**
 * Created by Allen Space on 6/22/2015.
 */
public class Randomizer {

    /**
     * Data Fields
     */
    private static Random mRandom;
    private static String mReturnString;
    private static String mFindString;
    private static String[] stringArray = {"Ice Cream", "Donut", "Burgers", "Pizza",
                                            "Sushi", "Lollipop", "Burrito", "Tacos"};
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

    /**
     * @author Allen Space
     */
    public Randomizer() {
        mRandom = new Random();
    }


    /**
     * @author Allen Space
     */

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
    /**
     * @return A random string.
     * @author Allen Space
     */
    public String getRandomString() {
        int i = mRandom.nextInt(stringArray.length);

        return stringArray[i];
    }
}
