package apihelpers.Untappd;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Allen Space on 7/29/2015.
 */
public class OneUntappd {

    private static String mBeerName;
    private static Bitmap mBeerImage;
    private static String mBeerStyle;
    private static double mRatings;
    private static int mTotalReviews;
    private static String mDescription;
    private static String mReviews;
    private static List<String> mFilledComments;

    public OneUntappd()
    {
        //default constructor.
    }


    public OneUntappd(String pBeerName, Bitmap pBeerImage, BeerData.Beer pBeer, List<String> pFilledComments)
    {
        this.mBeerName = pBeerName;
        this.mBeerImage = pBeerImage;

        this.mBeerStyle = pBeer.beer_style;
        this.mTotalReviews = pBeer.rating_count;
        this.mDescription = pBeer.beer_description;

        mFilledComments = pFilledComments;

    }

    public String getBeerName()
    {
        return this.mBeerName;
    }

    public Bitmap getBeerImage()
    {
        return this.mBeerImage;
    }

    public void setBeerName(String pBeerName)
    {
        this.mBeerName = pBeerName;
    }

    public void setBeerImage(Bitmap pBeerImage)
    {
        this.mBeerImage = pBeerImage;
    }

    public String getmReviews() {
        return mReviews;
    }

    public void setmReviews(String mReviews) {
        this.mReviews = mReviews;
    }

    public String getmDescription() {
        return this.mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmTotalReviews() {
        return this.mTotalReviews;
    }

    public void setmTotalReviews(int mTotalReviews) {
        this.mTotalReviews = mTotalReviews;
    }

    public double getmRatings() {
        return mRatings;
    }

    public void setmRatings(double mRatings) {
        this.mRatings = mRatings;
    }

    public String getmBeerStyle() {
        return mBeerStyle;
    }

    public void setmBeerStyle(String mBeerStyle) {
        mBeerStyle = mBeerStyle;
    }

    public List<String> getmFilledComments()
    {
        return mFilledComments;
    }
}
