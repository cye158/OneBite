package ApiManagers;

import android.content.Context;
import android.location.Location;

import java.util.Collections;
import java.util.Random;

import Callbacks.GeneralCallback;
import apihelpers.YelpApiHandler.Restaurant;
import apihelpers.YelpApiHandler.YelpData;

/**
 * Created by Whomever.
 */
public class RestaurantManager {

    /**Data Fields**/
    private static YelpData mYelpData; //This will need to populated before hand!!!!!!!!!!!!!

    private Restaurant mRestaurant;    //Creating resulting restaurant.

    //----Class Structure---!
    //----Constructors-------
    //----Getters------------
    //----Setters------------
    //----Override methods---
    //----Helpers, wrappers--


    public RestaurantManager()
    {
        //Default constructor.
    }

    public Restaurant getRandRestCar()
    {
        //Shuffle all because it is max radius
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        //Returns a random restuarant name.
        return new Restaurant(mYelpData.businesses.get(0));
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
                return new Restaurant(mYelpData.businesses.get(i));
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
                return new Restaurant(mYelpData.businesses.get(i));
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


}
