package apihelpers.googleapis;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import com.google.*;
import com.google.maps.android.PolyUtil;

import ApiManagers.NetworkRequestManager;
import Callbacks.GeneralCallback;

/**
 * Created by Allen Space on 8/4/2015.
 */
public class DirectionHandler {

    private static DirectionData mDirectData;

    private static List<LatLng> mListLatLng;

    private static Context mContext;


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
        String hardURL = "https://maps.googleapis.com/maps/api/directions/json?origin=SanFracisco&destination=DalyCity";

        return hardURL;
    }

    public void populateJSONDirections(double pLatitude, double pLongitude, Context pContext)
    {
        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                Log.i("LOCATION", "Got back the direction data.");
                mDirectData = (DirectionData) object;

                mListLatLng = decodePolyPoints(mDirectData.routes.get(0).overview_polyline.points);

                for(int i = 0; i < mListLatLng.size(); i++)
                {
                    Log.i("LOCATION", "Point : " + mListLatLng.get(i));
                }
            }
        };

        NetworkRequestManager.getInstance().populateDirectionData(generalCallback, directionURLBuilder(0.0, 0.0), pContext);
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
