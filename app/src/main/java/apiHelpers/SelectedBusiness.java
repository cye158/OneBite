package apihelpers;

import android.content.Context;

import java.util.Collections;
import java.util.Random;

import ApiManagers.UntappdFeedManager;
import apihelpers.YelpApiHandler.SearchForBusinessesResponse;

/**
 * Created by Allen Space on 6/22/2015.
 */
public class SelectedBusiness {

    /**
     * Data Fields
     */
    private static final String TAG = "YelpData";
    private static SearchForBusinessesResponse mBusinessResponse;

    /**Single Restuarant Data*/
    private static String mRestName;
    private static String mRestAddress;
    private static Double mRestLatitude;
    private static Double mRestLonigtude;

    private static String mDescription;

    private static String mRestMobileImage;

    private static float mRestRating;


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

    public SelectedBusiness(SearchForBusinessesResponse pBusinessResponse)
    {
        mBusinessResponse = pBusinessResponse;

        Collections.shuffle(mBusinessResponse.businesses, new Random(System.nanoTime()));

        mRestName = mBusinessResponse.businesses.get(0).name;

        mRestAddress = mBusinessResponse.businesses.get(0).location.display_address.get(0);

        mRestLatitude = mBusinessResponse.businesses.get(0).location.coordinate.latitude;
        mRestLonigtude = mBusinessResponse.businesses.get(0).location.coordinate.longitude;

        mDescription =  mBusinessResponse.businesses.get(0).snippet_text;

        mRestMobileImage =   mBusinessResponse.businesses.get(0).image_url;

        mRestRating =  mBusinessResponse.businesses.get(0).rating;


    }


    public int getBusinessListSize()
    {
        return mBusinessResponse.businesses.size();
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

    public String getRestImageURL()
    {
        return mRestMobileImage;
    }

    /***/
    public String getLongDescriptionRest()
    {
        String str = "";

        str = "Address: " + mRestAddress + "\n" + "\n"
               + "Rating: " + mRestRating + "\n" + "\n"
                + "Description: " + mDescription;

        return str;
    }


    public void reShuffleBusinessList()
    {
        Collections.shuffle(mBusinessResponse.businesses, new Random(System.nanoTime()));

        mRestName = mBusinessResponse.businesses.get(0).name;
        mRestAddress = mBusinessResponse.businesses.get(0).location.display_address.get(0);

        mRestLatitude = mBusinessResponse.businesses.get(0).location.coordinate.latitude;
        mRestLonigtude = mBusinessResponse.businesses.get(0).location.coordinate.longitude;
        mDescription =  mBusinessResponse.businesses.get(0).snippet_text;
        mRestMobileImage =   mBusinessResponse.businesses.get(0).image_url;

        mRestRating =  mBusinessResponse.businesses.get(0).rating;
    }

}
