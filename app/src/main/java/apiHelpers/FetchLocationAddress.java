package apiHelpers;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ironsquishy.biteclub.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apiHelpers.LocationHandler;

/**
 * Created by Allen Space on 7/2/2015.
 */
public class FetchLocationAddress extends IntentService{

    //For log cat.
    private static final String TAG = "apiHelpers";

    /**
     * @author Allen Space
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchLocationAddress(String name) {
        super("FetchLocationAddress");
    }

    /**
     * @author Allen Space
     * Description: This is the constructor that is called when intent to thrown.
     * */
    public FetchLocationAddress()
    {
        super("FetchLocationAddress");
        //defualt constructor.
    }

    /**
     * @author Allen Space
     * @param intent Intent object.
     *
     * Description: When the service intent is thrown this method is called and will
     *               excute the following code. In this context to open connection, to
     *               Google API and reverse geocoding.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "Service started.");

        Geocoder geocoder = new Geocoder(this, Locale.US);

        //Use for Geo coder return.
        List<Address> addresses = null;

        Log.i(TAG, "Beginning Bridge to Google. ");
            //TODO Increase error handling integrity.
            try {
                //Grab Device address through longitude and latitude.
                addresses = geocoder.getFromLocation(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), 1);

            } catch (IOException e) {
                String errorMessage = "Service unavailable";

                Log.e(TAG, errorMessage, e);
            }

            //Needs condition to handle either getting device address or other ways

            //Set the first Address list to a address object..
            Address address = addresses.get(0);

            //Array list to store strings of the address object.
            ArrayList<String> addressFragments = new ArrayList<String>();

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
                //TODO Delete Log for address line when not needed anymroe.
                Log.i(TAG, "Address Line: " + addressFragments.get(i));
            }

            //Set for Street and City strings.
            LocationHandler.streetAddress = addressFragments.get(0);
            LocationHandler.cityAddress = addressFragments.get(1);


        Log.i(TAG, "Done extracting info soon will destroy self. ");

    }

    /**
     * @author Allen Space
     * Description: Specify on Log cat when the Service is destoryed.
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "FetchAddress intentService destroyed.");

    }


}
