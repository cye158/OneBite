package apiHelpers.YelpApiHandler.YelpData;

import android.util.Log;

import java.util.Random;

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
        mRandom = new Random();
    }

    public Randomizer(SearchForBusinessesResponse pBusinessResponse)
    {
        this.mRandom = new Random();

        this.mBusinessResponse = pBusinessResponse;
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

}
