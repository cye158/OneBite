package ApiManagers;

import android.content.Context;

import java.util.List;

import Callbacks.UntappdResultRunnable;
import ApiHelpers.Untappd.UntappdData;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdFeedManager {

    private static UntappdData mData;

    private static NetworkRequestManager mNetworkRequestManager;

    private static String createdAt;
    private static String comment;
    private static String drink;
    private static String breweryName;
    private static String venueAddress;

    /**
     * @author Allen Space
     * */
    public UntappdFeedManager()
    {
        //default.
    }

    public UntappdFeedManager(UntappdData data)
    {
        mData = data;

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

    public void populateUntappdData(final UntappdResultRunnable pUntappdrun,double pLatitdude, double pLongitdude, Context pContext)
    {
        this.mNetworkRequestManager.getInstance().populateUntappdFeed(pUntappdrun, pLatitdude, pLongitdude, pContext);
    }

}
