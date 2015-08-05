package apihelpers.googleapis;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import com.google.*;
import com.google.maps.android.PolyUtil;

import ApiManagers.LocationHandler;
import ApiManagers.NetworkRequestManager;
import Callbacks.GeneralCallback;
import apihelpers.YelpApiHandler.Restaurant;

/**
 * Created by Allen Space on 8/4/2015.
 */
public class DirectionHandler {

    private static DirectionData mDirectData;

    private static List<LatLng> mListLatLng;

    private static Context mContext;

    private static String mStatus;


    public DirectionHandler()
    {
        //defualt constructor.
    }

    public DirectionHandler(Context pContext)
    {
        mContext = pContext.getApplicationContext();
    }

    public String directionURLBuilder(double pLat, double pLng)
    {
        String latitude = String.valueOf(LocationHandler.getmLatitude());
        String longitude = String.valueOf(LocationHandler.getmLongitude());

        String dLatitude = String.valueOf(pLat);
        String dLongitude = String.valueOf(pLng);

        String hardURL = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitude
                            + "%20" + longitude +"&destination=" + dLatitude + "%20" + dLongitude;

        return hardURL;
    }

    public void populateJSONDirections(Restaurant pRestuarant)
    {

        final String url = directionURLBuilder(pRestuarant.getmLatitude(), pRestuarant.getmLongitude());

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                Log.i("LOCATION", "Got back the direction data.");
                mDirectData = (DirectionData) object;

                mListLatLng = decodePolyPoints(mDirectData.routes.get(0).overview_polyline.points);
                mStatus = mDirectData.status;
            }
        };

        NetworkRequestManager.getInstance().populateDirectionData(generalCallback, url, mContext);
    }

    private List<LatLng> decodePolyPoints(final String polyPoints)
    {
        List<LatLng> listOfLatLng = PolyUtil.decode(polyPoints);

        return listOfLatLng;
    }

    public List<LatLng> getListOfLngLat()
    {
        return mListLatLng;
    }
}
