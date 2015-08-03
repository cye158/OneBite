package ApiManagers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.beust.jcommander.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

import Callbacks.GeneralCallback;
import Callbacks.UntappdResultRunnable;
import apihelpers.Untappd.OneUntappd;
import apihelpers.Untappd.UntappdData;
import apihelpers.YelpApiHandler.YelpData;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdManager {

    private static UntappdData mData;

    private static List<UntappdData.Item> mItems;

    private static NetworkRequestManager mNetworkRequestManager;

    private static String createdAt;
    private static String comment;
    private static String drink;
    private static String breweryName;
    private static String venueAddress;

    private static Bitmap mBitmap;

    private static OneUntappd mOneUntappd;

    /**
     * @author Allen Space
     * */
    public UntappdManager()
    {
        //default.
    }

    public UntappdManager(UntappdData data)
    {
        mData = data;

        mItems = mData.response.checkins.items;

    }
    /**
     * @author Allen Space
     *
     * */
    public String getShortDescription(int index)
    {

        String comment = mData.response.checkins.items.get(index).checkin_comment;;

        final String str = "Comment: " + comment;

        return str;
    }

    /**
     * @author Allen Space.
     * */
    public String getLongDescription(int index)
    {
        String created_at = mData.response.checkins.items.get(index).created_at;

        String comment = mData.response.checkins.items.get(index).checkin_comment;

        String drink = mData.response.checkins.items.get(index).beer.beer_name;

        String breweryName = mData.response.checkins.items.get(index).brewery.brewery_name;

        String venueAddress = mData.response.checkins.items.get(index).venue.location.venue_address;

        String str = "Drink: " + drink + "\n"
                     + "Brewery: " + breweryName + "\n"
                     + "Comment: " + comment + "\n"
                     + "Venue Address: " + venueAddress + "\n";

        return str;
    }

    public String getBeerTitle(int pIndex)
    {
        String drink = mData.response.checkins.items.get(pIndex).beer.beer_name;
        String str ="Drink: " + drink;
        return str;
    }


    public int getItemSize()
    {
        return mData.response.checkins.items.size();
    }

    public double getSingleItemLatitude(int pIndex)
    {
        return mData.response.checkins.items.get(pIndex).venue.location.lat;
    }

    public double getSingleItemLongitude(int pIndex)
    {
        return mData.response.checkins.items.get(pIndex).venue.location.lng;
    }

    public List<UntappdData.Item> getListItems()
    {
        return mData.response.checkins.items;
    }

    public String getRandomDrink()
    {

        Collections.shuffle(mData.response.checkins.items, new Random(System.nanoTime()));

        return mData.response.checkins.items.get(0).beer.beer_name;
    }

    public List<String> getFilledComments()
    {
        List<String> filledComments = new ArrayList<String>();

        int count = 0;

        for(int i = 0; i < mData.response.checkins.items.size();i++)
        {
          if(mData.response.checkins.items.get(i).checkin_comment != "")
          {
              filledComments.add(mData.response.checkins.items.get(i).checkin_comment);
          }
        }

        return filledComments;
    }

    public String getPopularBeerStyle()
    {
        final String str;


        return null;
    }

    /**
     * @author Allen Space
     * */
    public void populateUntappdData(double pLatitdude, double pLongitude, final Context pContext)
    {

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                mData = (UntappdData) object;

                mItems = mData.response.checkins.items;

                getAllBeerImages(pContext);
                Log.i("UNTAPPD", "Manager retrieved data...");

            }
        };

        NetworkRequestManager.getInstance().populateUntappdFeed(generalCallback, pLatitdude, pLongitude, pContext);
    }

    public OneUntappd getMostPopularDrink()
    {
        List<String> mostBeers = new ArrayList<String>();
        List<String> allBeers = new ArrayList<String>();

        String finalResult;
        Bitmap finalResultImage;

        for(int i = 0; i < mItems.size(); i++)
        {
            allBeers.add(mItems.get(i).beer.beer_name);
        }

        mostBeers = mode(allBeers);

        if(mostBeers.size() < 1){

            finalResult = mostBeers.get(0);

            finalResultImage = getBeerImageFromMostBeer(finalResult);

             return new OneUntappd(finalResult,finalResultImage);
        }else{

            Collections.shuffle(mostBeers, new Random(System.nanoTime()));

            finalResult = mostBeers.get(0);

            finalResultImage = getBeerImageFromMostBeer(finalResult);

         return new OneUntappd(finalResult, finalResultImage);
        }
    }

    //Thank you RosettaCode
    public static <T> List<T> mode(List<? extends T> coll) {
        Map<T, Integer> seen = new HashMap<T, Integer>();
        int max = 0;
        List<T> maxElems = new ArrayList<T>();
        for (T value : coll) {
            if (seen.containsKey(value))
                seen.put(value, seen.get(value) + 1);
            else
                seen.put(value, 1);
            if (seen.get(value) > max) {
                max = seen.get(value);
                maxElems.clear();
                maxElems.add(value);
            } else if (seen.get(value) == max) {
                maxElems.add(value);
            }
        }
        return maxElems;
    }


    private void getUntappdBeerImage(String URL, Context pContext, final int count)
    {

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                mBitmap = (Bitmap) object;

                mItems.get(count).beerImage = mBitmap;
            }
        };

        NetworkRequestManager.getInstance().getUntappdSingleImage(generalCallback, URL, pContext);

    }

    private void getAllBeerImages(final Context pContext)
    {
        int count = 0;

        for (int i = 0; i < mItems.size();i++)
        {
            //Get restaurant image..
            getUntappdBeerImage(mItems.get(i).beer.beer_label, pContext, count);

            count++;

        }
    }

    private Bitmap getBeerImageFromMostBeer(String finalResult)
    {
        Bitmap bitmap = null;


        for(int i = 0; i < mItems.size();i++)
        {
            if(mItems.get(i).beer.beer_name == finalResult && mItems.get(i).beer.beer_label != "")
            {
                return mItems.get(i).beerImage;
            }
        }


        return null;
    }
}

