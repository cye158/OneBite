package ApiManagers;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import Callbacks.GeneralCallback;
import Callbacks.UntappdResultRunnable;
import apihelpers.Untappd.UntappdData;

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
        Log.i("UNTAPPD", "Number of Beers: " + mData.response.checkins.items.size());
        Collections.shuffle(mData.response.checkins.items, new Random(System.nanoTime()));

        return mData.response.checkins.items.get(0).beer.beer_name;
    }

    /**
     * @author Allen Space
     * */
    public void populateUntappdData(double pLatitdude, double pLongitude, Context pContext)
    {
        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                mData = (UntappdData) object;

                Log.i("UNTAPPD", "Manager retrieved data...");

            }
        };

        NetworkRequestManager.getInstance().populateUntappdFeed(generalCallback, pLatitdude, pLongitude, pContext);
    }

}

