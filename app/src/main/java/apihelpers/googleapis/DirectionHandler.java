package apihelpers.googleapis;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import com.google.maps.android.PolyUtil;

import ApiManagers.LocationHandler;
import ApiManagers.NetworkRequestManager;
import Callbacks.GeneralCallback;
import apihelpers.YelpApiHandler.Restaurant;

/**
 * Created by Allen Space on 8/4/2015.
 */
public class DirectionHandler {

    /**Data Fields*/
    private static DirectionData mDirectData;

    private static List<LatLng> mListLatLng;

    private static Context mContext;

    private static String mStatus;


    public DirectionHandler()
    {
        //defualt constructor.
    }

    /**
     * @author Allen Space
     * Description: Needed before call to populate.
     * */
    public DirectionHandler(Context pContext)
    {
        mContext = pContext.getApplicationContext();
    }

    /**
     * @author Allen Space
     * Description: Build url with additonal lat, lng points perfereably the restaurant location.
     * */
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

    /**
     * @author Allen Space
     * Description: Populates the POJO for decoding poly line for directions on map.
     *
     * */
    public void populateJSONDirections(Restaurant pRestuarant)
    {

        final String url = directionURLBuilder(pRestuarant.getmLatitude(), pRestuarant.getmLongitude());

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                Log.i("LOCATION", "Got back the direction data.");

                //Imediately populate POJO
                mDirectData = (DirectionData) object;
                mStatus = mDirectData.status;

                //Imediately decode poly line points.
                mListLatLng = decodePolyPoints(mDirectData.routes.get(0).overview_polyline.points);

            }
        };

        NetworkRequestManager.getInstance().populateDirectionData(generalCallback, url, mContext);
    }

    /**
     * @author Allen Space
     * Description: Decodes the poly line points and transforms it into list of lat, lng points.
     * */
    private List<LatLng> decodePolyPoints(final String polyPoints)
    {
        List<LatLng> listOfLatLng = PolyUtil.decode(polyPoints);

        return listOfLatLng;
    }


    /**
     * @author Allen Space
     * Description: To publicly get List of lat, lng points.
     * */
    public List<LatLng> getListOfLngLat()
    {
        return mListLatLng;
    }
}
