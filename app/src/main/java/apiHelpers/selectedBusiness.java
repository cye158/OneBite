package ApiHelpers;

import android.content.Context;

import java.util.Random;

import ApiManagers.UntappdFeedManager;
import ApiHelpers.YelpApiHandler.SearchForBusinessesResponse;

/**
 * Created by Allen Space on 6/22/2015.
 */
public class SelectedBusiness {

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
    public SelectedBusiness() {

    }

    public SelectedBusiness(SearchForBusinessesResponse pBusinessResponse, Context pContext)
    {

        this.mBusinessResponse = pBusinessResponse;

        this.mRestName = mBusinessResponse.businesses.get(0).name;
        this.mRestAddress = mBusinessResponse.businesses.get(0).location.display_address.get(0);

        this.mRestLatitude = mBusinessResponse.businesses.get(0).location.coordinate.latitude;
        this.mRestLonigtude = mBusinessResponse.businesses.get(0).location.coordinate.longitude;

    }


    /**
     * @author Allen Space
     */
    public static void setmReturnString(String mReturnString) {
        SelectedBusiness.mReturnString = mReturnString;
    }

    /**
     * @author Allen Space
     */
    public static void setmFindString(String mFindString) {
        SelectedBusiness.mFindString = mFindString;
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
