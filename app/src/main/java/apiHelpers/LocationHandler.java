package apiHelpers;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * Created by Allen Space on 6/22/2015.
 */
public class LocationHandler implements ConnectionCallbacks, OnConnectionFailedListener {

    /**
     * Data Fields
     */
    private static GoogleApiClient mGoogleClient;
    private static Location mLocation;
    private static double mLongitude = 0.0;
    private static double mLatitude = 0.0;
    private static com.ironsquishy.biteclub.MapFragment mapFragment;

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
     * @param context Needed for opening google client connection.
     *                Description: Preferable used onCreat().
     * @author Allen Space
     */
    public LocationHandler(Context context) {
        mGoogleClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Log.i("LOCATIONHANDLER", "Called LocationHandler constructor.");
    }

    /**
     * @author Allen Space
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
     *                  <p/>
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
    public void startConnect() {
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
        Log.i("GOOGLE_CONNECT", "Call to onConnected.");

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleClient);

        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
            mLatitude = mLocation.getLatitude();
        }

        Log.i("LOCATION", "Longitude: " + mLongitude);
        Log.i("LOCATION", "Latitude: " + mLatitude);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void DisplayMap() {

        Log.i("DisplayMap()", "Method call success");
    }

}
