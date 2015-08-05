package apihelpers.googleapis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ironsquishy.biteclub.R;

import java.util.List;

import ApiManagers.DatabaseManager;
import ApiManagers.LocationHandler;
import apihelpers.SQLiteHandler.VisitedPlace;
import apihelpers.YelpApiHandler.Restaurant;


/**
 * Created by Allen Space on 7/14/2015.
 * Edited by Guan
 * Edited by Renz icons 7/29/15
 */
public class MarkerMapFactory {

    private static MarkerOptions mMarkers;
    private static GoogleMap mGoogleMap;
    private static DatabaseManager mDatabaseManager;
    private static Restaurant mRestaurant;
    private static Context context;

    /**
     * @author Allen Space
     *@param pGoogleMap GoogleMap java object.
     *Description: Main class constructor
     * */
    public MarkerMapFactory(GoogleMap pGoogleMap, Context pContext)
    {
        mGoogleMap =  pGoogleMap;
        mDatabaseManager =  DatabaseManager.getInstance(pContext);
        context = pContext;
    }

    public MarkerMapFactory(Restaurant pRestaurant)
    {
        mRestaurant = pRestaurant;
    }

    /**
     * @author Allen Space
     * Description: Call to place marker on clients location in google maps.
     * @return Marker object.
     * */
    public Marker createClientMarker()
    {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(LocationHandler.getmLatitude(), LocationHandler.getmLongitude()))
                .title("You're Here!");
                //.snippet(LocationHandler.streetAddress + ": " + LocationHandler.cityAddress);

        Bitmap userIcon = convertToBitmapMarkers(R.drawable.gmarker_user);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(userIcon));
        Marker  clientMarker = mGoogleMap.addMarker(markerOptions);

        return clientMarker;
    }


    /**
     * @author Allen Space Edited by Guan
     * @return Marker java object
     * Description: Factory method that will create the resulting marker from yelp response.
     * */
    public Marker createResultMarker()
    {

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(mRestaurant.getmLatitude(), mRestaurant.getmLongitude()))
                .title(mRestaurant.getmRestName());

        Bitmap burgerIcon = convertToBitmapMarkers(R.drawable.gmarker_burger);

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(burgerIcon));

        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();

        return marker;
    }

    /**
     * @author: Guan
     * Description: turn a resource image into bitmap and resized to google marker's size
     * @param id the resource ID of the image data
     * @return Bitmap object of resized image
     */
    private Bitmap convertToBitmapMarkers(int id) {
        Bitmap marker = BitmapFactory.decodeResource(context.getResources(), id);
        marker = Bitmap.createScaledBitmap(marker, 100, 132, true);

        return marker;
    }

    /**
     * @author: Guan
     * Description: populate google map with custom markers of user's favorite location
     */
    public void createHistoryMarkers()
    {
        List<VisitedPlace> restaurants;

        if (!mDatabaseManager.isDatabaseEmpty()) {
            restaurants = mDatabaseManager.getAllVisitedPlaces();

            Bitmap starIcon = convertToBitmapMarkers(R.drawable.gmarker_star);

            for (int i = 0; i < restaurants.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(restaurants.get(i).get_latitude(), restaurants.get(i).get_longitude()));
                markerOptions.title(restaurants.get(i).get_name());
                markerOptions.snippet("Visited " + restaurants.get(i).get_date());
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(starIcon));

                mGoogleMap.addMarker(markerOptions);
            }
        }
    }

    public void createUntappdMarkers(Context context)
    {   /*
        SelectedBusiness mResult = new SelectedBusiness();

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {
                UntappdManager untappdFeedData = (UntappdManager) object;

                for(int i = 0; i < untappdFeedData.getItemSize(); i++)
                {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(untappdFeedData.getSingleItemLatitude(i), untappdFeedData.getSingleItemLongitude(i)));
                    markerOptions.title(untappdFeedData.getBeerTitle(i));
                    markerOptions.snippet(untappdFeedData.getShortDescription(i));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                    // use this line for custom marker
                    //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.beer));

                    Marker marker = mGoogleMap.addMarker(markerOptions);
                }
            }
        };

        NetworkRequestManager.getInstance().populateUntappdFeed(generalCallback, mResult.getRestLatitude(), mResult.getRestLongitdude(), context);
        */
    }

}
