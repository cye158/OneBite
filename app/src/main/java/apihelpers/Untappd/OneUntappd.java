package apihelpers.Untappd;

import android.graphics.Bitmap;

/**
 * Created by Allen Space on 7/29/2015.
 */
public class OneUntappd {

    private String mBeerName;
    private Bitmap mBeerImage;

    public OneUntappd()
    {
        //default constructor.
    }

    public OneUntappd(String pBeerName, Bitmap pBeerImage)
    {
        this.mBeerName = pBeerName;
        this.mBeerImage = pBeerImage;
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
}
