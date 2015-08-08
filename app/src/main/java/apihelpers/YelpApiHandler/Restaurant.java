package apihelpers.YelpApiHandler;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen Space on 7/24/2015.
 */
public class Restaurant {


    /**Data Fields**/
    private static String mRestName;
    private static String mDescription;
    private static Bitmap mRestImage;//this handles the displayed yelp image, but won't be in the restaurant object
    private static Bitmap mRatingImage; //this handles the displayed yelp rating star image.
    private static double mLatitude;
    private static double mLongitude;
    private static double mRatings;
    private static List<List<String>> mCategogy;
    private static int mReviewCount;
    private static boolean mIsClosed;
    private static String mCuisineStyle;
    private static double mDistancefrom;

    private static String mPhoneNumber;


    private apihelpers.YelpApiHandler.YelpData.Business restaurant;


    public Restaurant()
    {
        //Default Constructor.
    }

    public Restaurant(String pRestName, String pDescription, double pLatitude, double pLongitude, double pRatings,Bitmap pRatingImage)
    {
        this.mRestName = pRestName;
        this.mDescription = pDescription;
        this.mLatitude = pLatitude;
        this.mLongitude = pLongitude;
        this.mRatings = pRatings;
        this.mRestImage = pRatingImage;
        this.mRatingImage = pRatingImage;
    }

    public Restaurant(Object object)
    {
        if(object instanceof  apihelpers.YelpApiHandler.YelpData.Business)
        {
            restaurant = (apihelpers.YelpApiHandler.YelpData.Business) object;

            this.mRestName = restaurant.name;
            this.mDescription = restaurant.snippet_text;
            this.mLatitude = restaurant.location.coordinate.latitude;
            this.mLongitude = restaurant.location.coordinate.longitude;
            this.mRatings = restaurant.rating;

            this.mCategogy = restaurant.categories;

            this.mIsClosed = restaurant.is_closed;

            this.mReviewCount = restaurant.review_count;

            this.mPhoneNumber = restaurant.phone;


        }else{

            Log.e("YelpData", "Failed to get instance!!!!!!!!!!!!!");
        }
    }
    public String getmRestName()
    {
        if(mRestName == null)
            return "No Restaurant found.";
        else
            return mRestName;
    }

    public void setmRestName(String mRestName) {
        this.mRestName = mRestName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setRatingImage(Bitmap bitmap)
    {
        mRatingImage = bitmap;
    }
    //Picture of Restuarant from yelp.
    public void setRestImage(Bitmap bitmap)
    {
        mRestImage = bitmap;
    }

    //Image of the restuarant ratings from Yelp
    public Bitmap getRatingImage()
    {

        if(mRatingImage != null)
        {
            Log.i("YelpData", "Returning rating image.");
            return this.mRatingImage;
        }
        else
            return null;
    }

    public Bitmap getmRestImage()
    {
        if(mRestImage != null ) {
            Log.i("YelpData", "Returning restuarant image.");
            return mRestImage;
        }
        else
            return null;
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
        return this.mRatings;
    }

    public void setmRatings(double mRatings) {
        this.mRatings = mRatings;
    }

    public List<List<String> >getCategory(List<List<String>> mCategogies){ return this.mCategogy; }

    public boolean getIsClosed()
    {
        return this.mIsClosed;
    }

    public int getReviewCount()
    {
        return this.mReviewCount;
    }

    public void setmCuisineStyle(String cuisineStyle)
    {
        this.mCuisineStyle = cuisineStyle;
    }

    public String getmCuisineStyle()
    {
        return this.mCuisineStyle;
    }

    public void setDistanceFrom(float distance)
    {
        this.mDistancefrom = distance;
    }

    public double getDistancefrom()
    {
        return this.mDistancefrom;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
