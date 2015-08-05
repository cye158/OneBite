package ApiManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Callbacks.GeneralCallback;
import apihelpers.Untappd.BeerData;
import apihelpers.Untappd.OneUntappd;
import apihelpers.Untappd.UntappdApiHandler;
import apihelpers.Untappd.UntappdData;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdManager {

    private static UntappdData mData;

    private static List<UntappdData.Item> mItems;

    private static BeerData.Beer mBeerData;

    private static NetworkRequestManager mNetworkRequestManager;

    private static UntappdApiHandler mUntappdHandler;

    private static String mMostPopularBeer;
    private static int mMostPopularBeerBID;

    private static Bitmap mBitmap;

    private static OneUntappd mOneUntappd;

    private static Context mContext;

    private static Bitmap mBeerImage;

    private static String mSavedURL;


    public UntappdManager()
    {
        //default constructor.
    }

    /**
     * @author Allen Space
     */
    public UntappdManager(Context pContext) {

        mContext = pContext;

        mUntappdHandler = new UntappdApiHandler();
    }

    public UntappdManager(UntappdData data) {
        mData = data;

        mItems = mData.response.checkins.items;

        mUntappdHandler = new UntappdApiHandler();

    }

    /**
     * @author Allen Space
     */
    public String getShortDescription(int index) {

        String comment = mData.response.checkins.items.get(index).checkin_comment;

        final String str = "Comment: " + comment;

        return str;
    }

    /**
     * @author Allen Space.
     */
    public String getLongDescription(int index) {

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

    public String getBeerTitle(int pIndex) {
        String drink = mData.response.checkins.items.get(pIndex).beer.beer_name;
        String str = "Drink: " + drink;
        return str;
    }


    public int getItemSize() {
        return mData.response.checkins.items.size();
    }

    public double getSingleItemLatitude(int pIndex) {
        return mData.response.checkins.items.get(pIndex).venue.location.lat;
    }

    public double getSingleItemLongitude(int pIndex) {
        return mData.response.checkins.items.get(pIndex).venue.location.lng;
    }

    public List<UntappdData.Item> getListItems() {
        return mData.response.checkins.items;
    }

    public String getRandomDrink() {

        Collections.shuffle(mData.response.checkins.items, new Random(System.nanoTime()));

        return mData.response.checkins.items.get(0).beer.beer_name;
    }

    public List<String> getFilledComments() {
        List<String> filledComments = new ArrayList<String>();


        for (int i = 0; i < mBeerData.checkins.items.size(); i++) {

            if (mBeerData.checkins.items.get(i).checkin_comment != "")
            {
                filledComments.add(mBeerData.checkins.items.get(i).checkin_comment);
            }
        }

        return filledComments;
    }

    public String getPopularBeerStyle() {
        final String str;


        return null;
    }

    /**
     * @author Allen Space
     */
    public void populateUntappdData(double pLatitdude, double pLongitude, final Context pContext) {

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                mData = (UntappdData) object;

                mItems = mData.response.checkins.items;

                Log.i("UNTAPPD", "Manager retrieved data...");

                getAllBeerImages(pContext);


            }
        };

        NetworkRequestManager.getInstance().populateUntappdFeed(generalCallback, pLatitdude, pLongitude, pContext);
    }

    /**
     * @author Allen SPace
     * Description: Set up for UntappdActivity needs to be called before call to get a OneUntappd object.
     * */
    public void setMostPopularDrink() {
        List<String> mostBeers = new ArrayList<String>();
        List<String> allBeers = new ArrayList<String>();

        String finalResult;

        for (int i = 0; i < mItems.size(); i++) {
            allBeers.add(mItems.get(i).beer.beer_name);
        }

        //Check for equal occurances.
        if (mostBeers.size() < 1) {

            mostBeers = mode(allBeers);
            finalResult = mostBeers.get(0);
            mMostPopularBeer = finalResult;

            mBeerImage = findRsltImage(finalResult);

            mMostPopularBeerBID = findBeerBID();

            getBeerData(mMostPopularBeerBID, mContext);

        } else {

            Collections.shuffle(mostBeers, new Random(System.nanoTime()));

            finalResult = mostBeers.get(0);
            mMostPopularBeer = finalResult;

            mBeerImage = findRsltImage(finalResult);

            mMostPopularBeerBID = findBeerBID();

            getBeerData(mMostPopularBeerBID, mContext);


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


    /**
     * @author Allen Space
     * @param URL String for url.
     * @param pContext Context data memember.
     *
     * */
    private void getUntappdBeerImage(String URL, Context pContext)
    {

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {
                //do nothing images are put into LRU bitmap cache.
            }
        };

        NetworkRequestManager.getInstance().getUntappdSingleImage(generalCallback, URL, pContext);

    }

    /**
     * @author Allen space
     * Description: Helper to get all images.
     **/
    private void getAllBeerImages(final Context pContext)
    {
        for (int i = 0; i < mItems.size(); i++) {
            //Get all beer images..
            getUntappdBeerImage(mItems.get(i).beer.beer_label, pContext);

        }
    }


    private int findBeerBID() {

        for (int i = 0; i < mItems.size(); i++) {
            if (mMostPopularBeer == mItems.get(i).beer.beer_name)
                return mItems.get(i).beer.bid;
        }

        return 16630;
    }

    private static void getBeerData(final int BID, Context pContext)
    {

        final String url = mUntappdHandler.untappdURLForBeer(BID);

        //Saved for webView later.
        mSavedURL = url;

        GeneralCallback generalCallback = new GeneralCallback() {
            @Override
            public void runWithResponse(Object object) {

                Log.i("UNTAPPD", "Retrieved beer data.");
                mBeerData = (BeerData.Beer) object;

            }
        };

        Log.i("UNTAPPD", "Making beer request.");
        NetworkRequestManager.getInstance().populateBeerInfo(generalCallback, url, pContext);

    }

    private Bitmap findRsltImage(final String pBeerToLookFor)
    {

        for(int i = 0; i < mItems.size(); i++)
        {
            if(pBeerToLookFor == mItems.get(i).beer.beer_name){

                return NetworkRequestManager.getInstance().getBitmapOnLRU(mItems.get(i).beer.beer_label);
            }
        }

        return null;
    }

    public OneUntappd getOneTappd()
    {
        return new OneUntappd(mMostPopularBeer, mBeerImage, mBeerData, getFilledComments(), mSavedURL);
    }
}