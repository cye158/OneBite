package apihelpers.Untappd;

import android.graphics.Bitmap;

/**
 * Created by Allen Space on 7/29/2015.
 */
public class OneUntappd {

    private String mBeerName;
    private Bitmap mBeerImage;
    private String mBeerStyle;
    private double mRatings;
    private int mTotalReviews;
    private String mDescription;
    private String mReviews;

    public OneUntappd()
    {
        //default constructor.
    }


    public OneUntappd(String pBeerName, Bitmap pBeerImage, BeerData.Beer pBeer)
    {
        this.mBeerName = pBeerName;
        this.mBeerImage = pBeerImage;

        this.mBeerStyle = pBeer.beer_style;
        this.mTotalReviews = pBeer.rating_count;

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
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmTotalReviews() {
        return mTotalReviews;
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
        this.mBeerStyle = mBeerStyle;
    }
}
