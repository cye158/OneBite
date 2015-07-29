package apihelpers.YelpApiHandler;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * Created by Allen Space on 7/24/2015.
 */
public class Restaurant {


    /**Data Fields**/
    private String mRestName;
    private String mDescription;
    private Bitmap mRestImage;//this handles the displayed yelp image, but won't be in the restaurant object
    private Bitmap mRatingImage; //this handles the displayed yelp rating star image.
    private double mLatitude;
    private double mLongitude;
    private double mRatings;
    private List<List<String>> mCategogy;


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
            this.mRestImage = restaurant.businesses.image_url;
            this.mRatingImage = restaurant.businesses.rating_img_url_small;
            this.mCategogy = restaurant.categories;


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

    public void setmRestImage(Bitmap mRestImage)  { this.mRestImage = mRestImage; }
    //insert here so could be used in the restaurant manager class to insert

    public void getRatingImage(Bitmap mRatingImage){ this.mRatingImage = mRatingImage;}

    public Bitmap getmRestImage(){ return mRestImage; }

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

    public List<List<String> >getCategory(List<List<String>> mCategogies){ return this.mCategogy; }
}
