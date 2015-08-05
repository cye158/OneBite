package ApiManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Callbacks.GeneralCallback;
import apihelpers.YelpApiHandler.Restaurant;
import apihelpers.YelpApiHandler.YelpData;
import apihelpers.googleapis.LocationHelper;
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

    private static ArrayList<String> FiltersArray = null;

    //----Class Structure---!
    //----Constructors-------
    //----Getters------------
    //----Setters------------
    //----Override methods---
    //----Helpers, wrappers--


    private RestaurantManager()
    {
        //Private constructor.
    }

    public static RestaurantManager getInstance() {
        if (mRestaurantManager == null) {
            mRestaurantManager = new RestaurantManager();
        }

        return mRestaurantManager;
    }

    //Set the array list.
    public void setFilters(ArrayList<String> object)
    {
        FiltersArray =  object;
    }

    public Restaurant getRandRestCar()
    {
        Restaurant carRestuarant = new Restaurant();
        double originLat = LocationHandler.getmLatitude();
        double originLng = LocationHandler.getmLongitude();

        //Shuffle all because it is max radius
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        //keep reshuffling until one is found.

        for(int i =0; i < mYelpData.businesses.size(); i++) {

            if (doesFitFilter(mYelpData.businesses.get(i)))
            {

                double destLat = mYelpData.businesses.get(i).location.coordinate.latitude;
                double destLng = mYelpData.businesses.get(i).location.coordinate.longitude;

                LocationHelper somelocation = new LocationHelper(originLat, originLng, destLat, destLng);

                carRestuarant = new Restaurant(mYelpData.businesses.get(i));

                new MarkerMapFactory(carRestuarant);

                carRestuarant.setDistanceFrom((float) somelocation.getDistanceFromOrigin());

                //Set the restaurents filter.
                carRestuarant.setmCuisineStyle(mYelpData.businesses.get(i).categories.get(0).get(0));

                //insert restaurant image so front-end won't make a second call
                carRestuarant.setmRestImage(mYelpData.businesses.get(i).restImage);

                //insert restaurant ratings

                carRestuarant.setRatingImage(mYelpData.businesses.get(i).restRatings);


                //Returns a random restuarant name.
                return carRestuarant;
            }
        }


        showToast(mContext);

        return carRestuarant;

    }





    public Restaurant getRandRestBus()
    {
        final double BusRadius = 5632.7; //Bus radius in meters.

        Restaurant busRestuarant = new Restaurant();

        double originLat = LocationHandler.getmLatitude();
        double originLng = LocationHandler.getmLongitude();

        //Pre shuffle whole list again.
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        LocationHelper somelocation = new LocationHelper();


        //Loop to grab first Restaurant with bus distance.
        for(int i =0; i < mYelpData.businesses.size(); i++)
        {
            double destLat = mYelpData.businesses.get(i).location.coordinate.latitude;
            double destLng = mYelpData.businesses.get(i).location.coordinate.longitude;

            float distance = (float) somelocation.getDistanceFromOrigin(originLat, originLng, destLat, destLng);

            if(distance <= BusRadius && doesFitFilter(mYelpData.businesses.get(i)))
            {
                busRestuarant = new Restaurant(mYelpData.businesses.get(i));

                new MarkerMapFactory(busRestuarant);

                //Set distance from.
                busRestuarant.setDistanceFrom(distance);

                //insert image from here
                busRestuarant.setmRestImage(mYelpData.businesses.get(i).restImage);

                //Insert to rating image to restuarant object.
                busRestuarant.setRatingImage(mYelpData.businesses.get(i).restRatings);

                //Set cuisin style string.
                busRestuarant.setmCuisineStyle(mYelpData.businesses.get(i).categories.get(0).get(0));

                //Returns a random restuarant name.
                return busRestuarant;
            }
        }

        showToast(mContext);

        return busRestuarant;
    }

    public Restaurant getRandRestWalk()
    {
        final double WalkRadius = 2414.02; //Bus radius in meters.


        Restaurant walkRestuarant = new Restaurant();

        double originLat = LocationHandler.getmLatitude();
        double originLng = LocationHandler.getmLongitude();

        //Pre shuffle whole list again.
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        LocationHelper somelocation = new LocationHelper();

        //Loop to grab first Restaurant with bus distance.
        for(int i =0; i < mYelpData.businesses.size(); i++)
        {
            double destLat = mYelpData.businesses.get(i).location.coordinate.latitude;
            double destLng = mYelpData.businesses.get(i).location.coordinate.longitude;

            float distance = (float) somelocation.getDistanceFromOrigin(originLat, originLng, destLat, destLng);

            //Checks it is in Walking radius.
            if(distance <= WalkRadius && doesFitFilter(mYelpData.businesses.get(i)))
            {
                walkRestuarant = new Restaurant(mYelpData.businesses.get(i));

                new MarkerMapFactory(walkRestuarant);

                //Set distance from.
                walkRestuarant.setDistanceFrom(distance);

                //insert image from here
                walkRestuarant.setmRestImage(mYelpData.businesses.get(i).restImage);

                //Insert to rating image to restuarant object.
                walkRestuarant.setRatingImage(mYelpData.businesses.get(i).restRatings);

                //Set cuisin style string.
                walkRestuarant.setmCuisineStyle(mYelpData.businesses.get(i).categories.get(0).get(0));

                //Returns a random restuarant name.
                return walkRestuarant;
            }
        }

        showToast(mContext);

        return walkRestuarant;
    }


    public void populateYelpData(double pLatitude, double pLongitude, final Context pContext)
    {
        mContext = pContext.getApplicationContext();
        //Simplify the callback process.
        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {
                mYelpData = (YelpData) object;

                getAllRestuarantImages(pContext);
            }
        };

        NetworkRequestManager.getInstance().initContext(pContext);
        NetworkRequestManager.getInstance().populateYelpData(generalCallback,"8046.72", pContext);
    }

    //Get all restuarant images.
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

                }
            };

            NetworkRequestManager.getInstance().getYelpSingleImage(generalCallback, URL, pContext);
        }
    }

    //Help to find filter restuarant.
    private Boolean doesFitFilter(YelpData.Business pRestaurant)
    {

        if(FiltersArray==null) {
            return true; //That means all were check or none.
        }else {

            for(int i = 0; i < FiltersArray.size(); i++) {

                Log.i("categ",FiltersArray.get(i).toLowerCase() + " == " + pRestaurant.categories.get(0).get(1)
                        +"?");

                if(pRestaurant.categories.get(0).get(1).equals((FiltersArray.get(i)
                        .toLowerCase())))
                {
                    Log.i("categ", "I FOUND ONE!!!!");
                    return true;
                }
            }
            return false;
        }

    }

    private void showToast(Context pContex)
    {
        Toast.makeText(pContex, "No restaurant found!",
                Toast.LENGTH_SHORT).show();
    }


     /*
    public Restaurant getFilterRestCar(ArrayList filter)
    {
        //Shuffle all because it is max radius
        Collections.shuffle(mYelpData.businesses, new Random(System.nanoTime()));

        for (int i=0; i < mYelpData.businesses.size(); i++){

            for(int j=0; j <filter.size(); j++){

                Restaurant matchedRestaurant = new Restaurant(mYelpData.businesses.get(i));
                if(mYelpData.businesses.get(i).categories.get(0).get(1).equals((filter.get(j).toString().toLowerCase()))) {

                    Restaurant matchedRestaurant = new Restaurant(mYelpData.businesses.get(i));

                Log.i("YelpData", "Rest name: " + matchedRestaurant.getmRestName());
                    return matchedRestaurant;

                }


            }

        }

        Restaurant matchedRestaurant = new Restaurant(mYelpData.businesses.get(0));
        return matchedRestaurant; //if cannot find one restaurant's style matches the user's selection
    }*/

}
