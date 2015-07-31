package ApiManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Callbacks.GeneralCallback;
import apihelpers.YelpApiHandler.Restaurant;
import apihelpers.YelpApiHandler.YelpData;
import apihelpers.googleapis.MarkerMapFactory;

/**
 * Created by Whomever and Darin.
 */
public class RestaurantManager {

    /**Data Fields**/
    private static YelpData mYelpData; //This will need to populated before hand!!!!!!!!!!!!!
    private static Bitmap mYelpImage; //
    private static Context mContext; //Volly need to pass in the context

    private Restaurant mRestaurant;    //Creating resulting restaurant.

    private static RestaurantManager mRestaurantManager = null;

    //----Class Structure---!
    //----Constructors-------
    //----Getters------------
    //----Setters------------
    //----Override methods---
    //----Helpers, wrappers--


    private RestaurantManager()
    {
        //Default constructor.
    }

    public static RestaurantManager getInstance() {
        if (mRestaurantManager == null) {
            mRestaurantManager = new RestaurantManager();
        }

        return mRestaurantManager;
    }

    public Restaurant getRandRestCar()
    {
        //Shuffle all because it is max radius
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        //Set origin location.
        Location origin = new Location("origin");
        origin.setLatitude(LocationHandler.getmLatitude());
        origin.setLongitude(LocationHandler.getmLongitude());

        //Set given Restuarant location.
        Location location = new Location("newLocation");
        location.setLatitude(mYelpData.businesses.get(0).location.coordinate.latitude);
        location.setLongitude(mYelpData.businesses.get(0).location.coordinate.longitude);

        Restaurant restaurant = new Restaurant(mYelpData.businesses.get(0));

        restaurant.setDistanceFrom(origin.distanceTo(location));

        List<List<String>> Matrix = null;

        Matrix = mYelpData.businesses.get(0).categories;

        restaurant.setmCuisineStyle(Matrix.get(0).get(0));

        new MarkerMapFactory(restaurant);

        //insert restaurant image so front-end won't make a second call
        restaurant.setmRestImage(mYelpData.businesses.get(0).restImage);

        //insert restaurant ratings

        restaurant.setRatingImage(mYelpData.businesses.get(0).restRatings);


        //Returns a random restuarant name.
        return restaurant;
    }

    public Restaurant getFilterRestCar(ArrayList filter)
    {
        //Shuffle all because it is max radius
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));



        for (int i=0; i < mYelpData.businesses.size(); i++){

            for(int j=0; j <filter.size(); j++){

//                Restaurant matchedRestaurant = new Restaurant(mYelpData.businesses.get(i));
//                Log.i("YelpData", "Rest name: " + matchedRestaurant.getmRestName());
//                Log.i("YelpData", "filter name: " + filter.get(j).toString().toLowerCase());
//                Log.i("YelpData", "Category name: " + mYelpData.businesses.get(i).categories.get(0).get(1));
                if(mYelpData.businesses.get(i).categories.get(0).get(1).equals((filter.get(j).toString().toLowerCase()))) {

                    Restaurant matchedRestaurant = new Restaurant(mYelpData.businesses.get(i));
//
//                    Log.i("YelpData", "Rest name: " + matchedRestaurant.getmRestName());
                    return matchedRestaurant;


                }


            }

        }

        Restaurant matchedRestaurant = new Restaurant(mYelpData.businesses.get(0));
        return matchedRestaurant; //if cannot find one restaurant's style matches the user's selection


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

                restaurant.setDistanceFrom(origin.distanceTo(location));

                new MarkerMapFactory(restaurant);

                List<List<String>> Matrix = null;

                Matrix = mYelpData.businesses.get(0).categories;

                restaurant.setmCuisineStyle(Matrix.get(0).get(0));

                //insert image from here
                restaurant.setmRestImage(mYelpData.businesses.get(i).restImage);

                //Insert image rating to restaurant
                restaurant.setRatingImage(mYelpData.businesses.get(i).restRatings);

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

                restaurant.setDistanceFrom(origin.distanceTo(restlocation));

                new MarkerMapFactory(restaurant);

                List<List<String>> Matrix = null;

                Matrix = mYelpData.businesses.get(0).categories;

                restaurant.setmCuisineStyle(Matrix.get(0).get(0));

                //insert image from here
                restaurant.setmRestImage(mYelpData.businesses.get(i).restImage);

                //Insert to rating image to restuarant object.
                restaurant.setRatingImage(mYelpData.businesses.get(i).restRatings);

                //Returns a random restuarant name.
                return restaurant;
            }
        }

        return null;
    }

    //One call for all pass in the LocationHandler Lat and Long.
    public void populateYelpData(double pLatitude, double pLongitude, final Context pContext)
    {
        //Simplify the callback process.
        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {
                mYelpData = (YelpData) object;

                getAllRestuarantImages(pContext);
            }
        };


        NetworkRequestManager.getInstance().populateYelpData(generalCallback,"8046.72", pContext);
    }

    private void getAllRestuarantImages(Context pContext)
    {
        final Context context = pContext;

        int count = 0;

        for (int i = 0; i < mYelpData.businesses.size();i++)
        {
            //Get restaurant image..
            getYelpSingleImage(mYelpData.businesses.get(i).image_url, context, count, true);

            getYelpSingleImage(mYelpData.businesses.get(i).rating_img_url_large, context, count, false);

            count++;

        }
    }

    //one call for all pass with the URL
    public void getYelpSingleImage(String URL, Context pContext, final int index, boolean flag){

        if (flag == true) {



            GeneralCallback generalCallback = new GeneralCallback() {
                @Override
                public void runWithResponse(Object object) {
                    mYelpImage = (Bitmap) object;

                    mYelpData.businesses.get(index).restImage = mYelpImage;

                    Log.i("YelpData", "Image count: " + index);

                }
            };

            URL = URL.replaceAll("ms.jpg" , "o.jpg"); //replace the image size


            NetworkRequestManager.getInstance().getYelpSingleImage(generalCallback, URL, pContext);

        }else
        {

            GeneralCallback generalCallback = new GeneralCallback() {
                @Override
                public void runWithResponse(Object object) {
                    mYelpImage = (Bitmap) object;

                    mYelpData.businesses.get(index).restRatings = mYelpImage;

                    Log.i("YelpData", "Image count: " + index);

                }
            };

            NetworkRequestManager.getInstance().getYelpSingleImage(generalCallback, URL, pContext);
        }
    }







}
