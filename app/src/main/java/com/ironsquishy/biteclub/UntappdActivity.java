package com.ironsquishy.biteclub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ApiManagers.LocationHandler;
import ApiManagers.UntappdManager;
import apihelpers.SelectedBusiness;


public class UntappdActivity extends ActionBarActivity {

    private static TextView mRestResult;
    private static EditText mPopDrink;
    private static TextView mRndDrink;
    private static Context mContext;
    private static UntappdManager mUntappdManager;

    private static SelectedBusiness mSelectedBusiness;

    private static Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untappd);

        mIntent =  this.getIntent();

        mUntappdManager = new UntappdManager();

        mContext = this;

        mRestResult = (TextView) findViewById(R.id.ResturantRslt);

        //mPopDrink = (EditText) findViewById(R.id.PopDrinkResult);

        mRndDrink = (TextView) findViewById(R.id.RndDrinkRslt);

        mSelectedBusiness = new SelectedBusiness();

        mUntappdManager.populateUntappdData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);

        displayRestaurant();

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
