package apihelpers.YelpApiHandler;

import android.util.Log;

import java.util.List;

/**
 * Created by Allen Space on 7/24/2015.
 */
public class Restaurant {


    /**Data Fields**/
    private String mRestName;
    private String mDescription;
    private double mLatitude;
    private double mLongitude;
    private double mRatings;

    private YelpData.Business restaurant;


    public Restaurant()
    {
        //Defualt Constructor.
    }

    public Restaurant(String pRestName, String pDescription, double pLatitude, double pLongitude, double pRatings)
    {
        this.mRestName = pRestName;
        this.mDescription = pDescription;
        this.mLatitude = pLatitude;
        this.mLongitude = pLongitude;
        this.mRatings = pRatings;
    }

    public Restaurant(Object object)
    {
        if(object instanceof  YelpData.Business)
        {
            restaurant = (YelpData.Business) object;

            this.mRestName = restaurant.name;
            this.mDescription = restaurant.snippet_text;
            this.mLatitude = restaurant.location.coordinate.latitude;
            this.mLongitude = restaurant.location.coordinate.longitude;
            this.mRatings = restaurant.rating;
        }else{

            Log.e("YelpData", "Failed to get instance!!!!!!!!!!!!!");
        }
    }
    public String getmRestName() {
        return mRestName;
    }

    public void setmRestName(String mRestName) {
        this.mRestName = mRestName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmRatings() {
        return mRatings;
    }

    public void setmRatings(double mRatings) {
        this.mRatings = mRatings;
    }
}
