package ApiManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import Callbacks.GeneralCallback;
import apihelpers.YelpApiHandler.Restaurant;
import apihelpers.YelpApiHandler.YelpData;
import apihelpers.googleapis.MarkerMapFactory;

/**
 * Created by Whomever.
 */
public class RestaurantManager {

    /**Data Fields**/
    private static YelpData mYelpData; //This will need to populated before hand!!!!!!!!!!!!!
    private static Bitmap mYelpImage; //
    private static Context mContext;

    private Restaurant mRestaurant;    //Creating resulting restaurant.

    //----Class Structure---!
    //----Constructors-------
    //----Getters------------
    //----Setters------------
    //----Override methods---
    //----Helpers, wrappers--

    public RestaurantManager()
    {
        //Default Constructor
    }

    public RestaurantManager(Context pContext)
    {
        mContext = pContext;
    }

    public Restaurant getRandRestCar()
    {

        //Shuffle all because it is max radius
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        Restaurant restaurant = new Restaurant(mYelpData.businesses.get(0));

        new MarkerMapFactory(restaurant);

        //insert restaurant image so front-end won't make a second call
        restaurant.setmRestImage(mYelpImage);

        //Returns a random restuarant name.
        return restaurant;
    }

    public Restaurant getRandRestBus()
    {
        final double BusRadius = 5632.7; //Bus radius in meters.

        Location origin = new Location("origin");
        origin.setLatitude(LocationHandler.getmLatitude());
        origin.setLongitude(LocationHandler.getmLongitude());

        //Pre shuffle whole list again.
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        //Loop to grab first Restaurant with bus distance.
        for(int i =0; i < mYelpData.businesses.size(); i++)
        {
            Location location = new Location("newLocation");
            location.setLatitude(mYelpData.businesses.get(i).location.coordinate.latitude);
            location.setLongitude(mYelpData.businesses.get(i).location.coordinate.longitude);

            if(origin.distanceTo(location) <= BusRadius)
            {
                Restaurant restaurant = new Restaurant(mYelpData.businesses.get(i));

                new MarkerMapFactory(restaurant);

                //insert image from here
                restaurant.setmRestImage(mYelpImage);

                //Returns a random restuarant name.
                return restaurant;
            }
        }

        return null;
    }

    public Restaurant getRandRestWalk()
    {
        final double WalkRadius = 2414.02; //Bus radius in meters.

        Location origin = new Location("origin");
        origin.setLatitude(LocationHandler.getmLatitude());
        origin.setLongitude(LocationHandler.getmLongitude());

        //Pre shuffle whole list again.
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        //Loop to grab first Restaurant with bus distance.
        for(int i =0; i < mYelpData.businesses.size(); i++)
        {
            //Generating to calculate distance.
            Location restlocation = new Location("RestLocation");
            restlocation.setLatitude(mYelpData.businesses.get(i).location.coordinate.latitude);
            restlocation.setLongitude(mYelpData.businesses.get(i).location.coordinate.longitude);

            //Checks it is in Walking radius.
            if(origin.distanceTo(restlocation) <= WalkRadius)
            {
                Restaurant restaurant = new Restaurant(mYelpData.businesses.get(i));

                new MarkerMapFactory(restaurant);

                //insert image from here
                restaurant.setmRestImage(mYelpImage);

                //Returns a random restuarant name.
                return restaurant;
            }
        }

        return null;
    }

    //One call for all pass in the LocationHandler Lat and Long.
    public void populateYelpData(double pLatitude, double pLongitude, Context pContext)
    {
        //Simplify the callback process.
        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {
                mYelpData = (YelpData) object;
            }
        };

        NetworkRequestManager.getInstance().populateYelpData(generalCallback,"8046.72", pContext);
    }

    //one call for all pass with the URL
    public void getYelpSingleImage(String URL, Context pContext){

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {
                mYelpImage = (Bitmap) object;
            }
        };

        NetworkRequestManager.getInstance().getYelpSingleImage(generalCallback, "", pContext);
    }

    private List<YelpData.Business> getRestuarantBasedOnFilter(String[] filterString)
    {
        //For  or while to check which Restaurants fit the filter options.

        //Conditon statement.

        //once all are collected return list.

        return null;
    }

}
