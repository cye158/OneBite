package com.ironsquishy.biteclub;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ApiManagers.LocationHandler;
import ApiManagers.UntappdManager;
import apihelpers.Untappd.UntappdData;


public class UntappdActivity extends Activity{

    private static TextView mRestResult;
    private static EditText mPopDrink;
    private static TextView mRndDrink;
    private static Context mContext;
    private static UntappdManager mUntappdManager;
    private static List<UntappdData.Beer> mBeerList = null;

    private static Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untappd);

        mIntent =  this.getIntent();

        mContext = this;

        mUntappdManager = new UntappdManager();

        mRestResult = (TextView) findViewById(R.id.ResturantRslt);

        mRndDrink = (TextView) findViewById(R.id.RndDrinkRslt);

        mUntappdManager.populateUntappdData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);

        //mPopDrink = (EditText) findViewById(R.id.PopDrinkResult);

        //displayRestaurant();

        //handleUntappdManger();
    }

    private void displayRestaurant()
    {
        final String restaurantRslt = mIntent.getStringExtra("restname");

        mRestResult.setText(restaurantRslt);
    }

    private void displayPopularDrink()
    {
        String popularSTR = "I will work on Algorithm!";

        mPopDrink.setText(popularSTR);
    }

    /** When user clicks "What poeple are saying!"*/
    public void onCommentClick(View view)
    {
        Intent intent = new Intent(this, UntappdList.class);
        startActivity(intent);
    }

    /**When user clicks on Randomize Drink*/
    public void onRandomDrink(View view)
    {
       mRndDrink.setText(mUntappdManager.getRandomDrink());
    }


}
