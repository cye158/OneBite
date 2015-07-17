package apiHelpers.YelpApiHandler.YelpData;

import android.content.Context;
import android.util.Log;

import java.util.Random;

import apiHelpers.Untappd.UntappdHandler;
import apiHelpers.UntappdFeedManager;

/**
 * Created by Allen Space on 6/22/2015.
 */
public class Randomizer {

    /**
     * Data Fields
     */
    private static Random mRandom;
    private static String mReturnString;
    private static String mFindString;
    private static final String TAG = "YelpData";
    private static SearchForBusinessesResponse mBusinessResponse;
    private static UntappdFeedManager untappdFeedManager;

    /**Single Restuarant Data*/
    public static String mRestName;
    public static String mRestAddress;
    public static Double mRestLatitude;
    public static Double mRestLonigtude;


    //================================
    //***Constructors*****
    //================================
    //***Setters**********
    //================================
    //***Getters***********
    //================================
    //***Helpers***********
    //================================

    /**
     * @author Allen Space
     */
    public Randomizer() {

    }

    public Randomizer(SearchForBusinessesResponse pBusinessResponse, Context pContext)
    {
        untappdFeedManager = new UntappdFeedManager();

        this.mBusinessResponse = pBusinessResponse;

        this.mRestName = mBusinessResponse.businesses.get(0).name;
        this.mRestAddress = mBusinessResponse.businesses.get(0).location.display_address.get(0);

        this.mRestLatitude = mBusinessResponse.businesses.get(0).location.coordinate.latitude;
        this.mRestLonigtude = mBusinessResponse.businesses.get(0).location.coordinate.longitude;

        untappdFeedManager.populateUntappdData(mRestLatitude, mRestLonigtude, pContext);

    }


    /**
     * @author Allen Space
     */
    public static void setmReturnString(String mReturnString) {
        Randomizer.mReturnString = mReturnString;
    }

    /**
     * @author Allen Space
     */
    public static void setmFindString(String mFindString) {
        Randomizer.mFindString = mFindString;
    }

    /**
     * @return A random string.
     * @author Allen Space
     */
    public String getRandomString() {

        return "Business name was null!";

    }

    public int getBusinessListSize()
    {
        return mBusinessResponse.businesses.size();
    }

    /**
     * @author Allen Space
     *Description: get Business name by index.
     * */
    public String getBusinessName(int pIndex)
    {
        return this.mBusinessResponse.businesses.get(pIndex).name;
    }
    /**
     * @author Allen Space
     * */
    public String getmRestAddress()
    {
        return this.mRestAddress;
    }
    /**
     * @author Allen Space
     *
     * */
    public double getRestLatitude()
    {
        return this.mRestLatitude;
    }

    /**
     * @author Allen Space
     * */
    public double getRestLongitdude()
    {
        return this.mRestLonigtude;
    }

    /**
     * @author Allen Space
     * */
    public String getmRestName()
    {
        return this.mRestName;
    }

}
