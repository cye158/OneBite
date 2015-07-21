package apihelpers.googleapis;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ApiManagers.*;
import Callbacks.UntappdResultRunnable;
import apiHelpers.SQLiteHandler.DBHandler;
import apiHelpers.SQLiteHandler.VisitedPlace;
import apiHelpers.SelectedBusiness;
import apihelpers.SelectedBusiness;


/**
 * Created by Allen Space on 7/14/2015.
 */
public class MarkerMapFactory {

    private static MarkerOptions mMarkers;
    private static GoogleMap mGoogleMap;
    private static DatabaseManager mDatabaseManager;

    /**
     * @author Allen Space
     *@param pGoogleMap GoogleMap java object.
     *Description: Main class constructor
     * */
    public MarkerMapFactory(GoogleMap pGoogleMap)
    {
        this.mGoogleMap =  pGoogleMap;
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
                .title("You")
                .snippet(LocationHandler.streetAddress + ": " + LocationHandler.cityAddress);

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        Marker  clientMarker = mGoogleMap.addMarker(markerOptions);

        return clientMarker;
    }


    /**
     * @author Allen Space
     * @return Marker java object
     * Description: Factory method that will create the resulting marker from yelp response.
     * */
    public Marker createResultMarker()
    {
        SelectedBusiness randomResult = new SelectedBusiness();


        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(randomResult.getRestLatitude(), randomResult.getRestLongitdude()))
                .snippet(randomResult.getmRestAddress())
                .title(randomResult.getmRestName());

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();

        return marker;
    }

    /**
     * @author: Guan
     * @return A list of Marker objects
     */
    public List<Marker> createHistoryMarkers()
    {
        List<VisitedPlace> restaurants;

        if (!mDatabaseManager.isDatabaseEmpty()) {
            restaurants = mDatabaseManager.getAllVistedPlaces();

            for (int i = 0; i < restaurants.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(restaurants.get(i).get_latitude(), restaurants.get(i).get_longitude()));
                markerOptions.title(restaurants.get(i).get_name());
                markerOptions.snippet("Visited " + restaurants.get(i).get_date());
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                mGoogleMap.addMarker(markerOptions);
            }
        }

        return null;
    }

    public void createUntappdMarkers(Context context)
    {
        SelectedBusiness mResult = new SelectedBusiness();

        UntappdResultRunnable untappdResultRunnable = new UntappdResultRunnable() {
            @Override
            public void runWithRandomResult(UntappdFeedManager untappdFeedData) {
                //Adds all markers from Untapppd Data
                for(int i = 0; i < untappdFeedData.getItemSize(); i++)
                {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(untappdFeedData.getSingleItemLatitude(i), untappdFeedData.getSingleItemLongitude(i)));
                    markerOptions.title(untappdFeedData.getBeerTitle(i));
                    markerOptions.snippet(untappdFeedData.getShortDescription(i));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                    Marker marker = mGoogleMap.addMarker(markerOptions);
                }
            }
        };

        NetworkRequestManager.getInstance().populateUntappdFeed(untappdResultRunnable, mResult.getRestLatitude(), mResult.getRestLongitdude(), context);

    }

}
