package com.ironsquishy.biteclub;

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
    private static String[] stringArray = {"Ice Cream", "Donut", "Burgers", "Pizza",
                                            "Sushi", "Lollipop", "Burrito", "Tacos"};
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
        int i = mRandom.nextInt(stringArray.length);

        return stringArray[i];
    }

}
