package ApiManagers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import apihelpers.googleapis.FetchLocationAddress;

/**
 * Created by Allen Space on 6/22/2015.
 */
public class LocationHandler implements ConnectionCallbacks, OnConnectionFailedListener {

    /**
     * Data Fields
     */
    private static GoogleApiClient mGoogleClient;
    private static Context mContext;
    // private static MapFragment mapFragment;

    //Following double's are set to San Francisco.
    private static double mLongitude = -122.431297;
    private static double mLatitude = 37.773972;

    public static String streetAddress = null;
    public static String cityAddress = null;
    public static Location mLocation;

    //Singletion Object.
    private static LocationHandler singleLocationHandler = null;

    private static final String TAG = "LOCATION";

    //================================
    //***Constructors*****
    //================================
    //***Setters**********
    //================================
    //***Getters***********
    //================================
    //***Helpers, Overrides****
    //================================


    /**
     * @author Allen Space
     * Description: Standard singleton design constructor.
     * */
    public static LocationHandler getInstance()
    {
        if (singleLocationHandler == null)
        {
            singleLocationHandler = new LocationHandler();
        }

        return singleLocationHandler;
    }


    /**
     * @author Allen Space
     * Description: private constructor
     * */
    private LocationHandler()
    {
        //empty for singleton.
    }

    /**
     * @author Allen Space
     * Description: Set connection to Google Api client.
     * @param pContext A Context object.
     * */
    public  void setGoogleApiConnection(Context pContext)
    {
        mGoogleClient = new GoogleApiClient.Builder(pContext.getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mContext = pContext.getApplicationContext();

        Log.i(TAG, "Set Google client object.");
    }

    /**
     * @author Allen Space
     * @param mGoogleClient Google Api object.
     * Description: Start the Connection for google services.
     */
    public static void setmGoogleClient(GoogleApiClient mGoogleClient) {
        LocationHandler.mGoogleClient = mGoogleClient;
    }

    /**
     * @param mLongitude Set new longitude coordinates.
     * @author Allen Space
     */
    public static void setmLongitude(double mLongitude) {
        LocationHandler.mLongitude = mLongitude;
    }


    /**
     * @param mLatitude Double mLatitude.
     *
     *                  Description: Setter for mLatitude data member.
     * @author Allen Space
     */
    public static void setmLatitude(double mLatitude) {
        LocationHandler.mLatitude = mLatitude;
    }


    /**
     * @return Returns the mGoogleClient object.
     * @author Allen space
     */
    public static GoogleApiClient getmGoogleClient() {
        return mGoogleClient;
    }

    /**
     * @author Allen Space
     * @return Returns the mLocation object.
     *
     * Description: That may be intended to be used with the GeoCoder.
     * */
    public Location getLocation()
    {
        return this.mLocation;
    }

    /**
     * @return Returns the mLongitude value.
     * @author Allen Space
     */
    public static double getmLongitude() {
        return mLongitude;
    }

    /**
     * @return Double mLatitude value.
     * @author Allen Space
     */
    public static double getmLatitude() {
        return mLatitude;
    }

    /**
     * @author Allen Space
     * Description: Initials the Google API service connection.
     */
    public static void startConnect() {
        mGoogleClient.connect();
    }

    /**
     * @param connectionHint Gets called when connect() is called.
     *                       Description: When connected to the Google API services
     *                       will create location object and set the device
     *                       longitude and latitude.
     * @author Allen Space
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        Log.i(TAG, "Now connected to Google services.");

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleClient);

        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
            mLatitude = mLocation.getLatitude();
        }

        Log.i(TAG, "Latitude & Longitude: " + mLatitude + ", " + mLongitude);

        //Start service to fetch addresses.
        //Intent intent = new Intent(mContext, FetchLocationAddress.class);
        //mContext.startService(intent);

    }

    /**
     * @author Allen Space
     * Descritpion: Handle a connection suspend from services
     * */
    @Override
    public void onConnectionSuspended(int i) {

        Log.w(TAG, "Google connection Suspended.");

        final ProgressDialog ringProgressDialog = ProgressDialog.show(mContext,
                "We Have Lost connection", "Hold on, we will try connect!", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }

        }).start();
    }

    /**
     * @author Allen Space
     * Description: Throws a dialog if connect fails.
     * */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "Google Services connection has failed!");

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Google Service Lost");

        builder.create();

    }

    /**
     * @author Allen Space
     * @param pAddress String value of th address from user.
     * Description: With passing of an address string this will grab
     *               the lat and long points of the entered address.
     *
     * */
    public void fetchByAdress(String pAddress, Context pContext)
    {
        Log.i(TAG, "Getting by address of: " + pAddress);

        mContext = pContext.getApplicationContext();

        Intent intent = new Intent(mContext, FetchLocationAddress.class);
        intent.putExtra("address", pAddress);

        mContext.startService(intent);
    }
}


