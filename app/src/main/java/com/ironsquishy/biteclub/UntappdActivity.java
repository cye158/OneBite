package com.ironsquishy.biteclub;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ApiManagers.LocationHandler;
import ApiManagers.UntappdManager;
import apihelpers.Untappd.OneUntappd;
import apihelpers.Untappd.UntappdData;


public class UntappdActivity extends Activity{

    private static TextView mRestResult;
    private static EditText mPopDrink;
    private static TextView mRndDrink;
    private static Context mContext;
    private static UntappdManager mUntappdManager;
    private static List<String> mBeerList = null;
    private static TextView mResultTextView;
    private static TextView mComments;

    private static ImageView mImageView;

    private static OneUntappd mOneUntappd;

    private static Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.untappd_activity);

        mIntent =  this.getIntent();

        mContext = this;

        mResultTextView = (TextView) findViewById(R.id.resultText);

        mImageView = (ImageView) findViewById(R.id.YelpImage);

        mUntappdManager = new UntappdManager();

        mOneUntappd = mUntappdManager.getMostPopularDrink();

        displayResultDrink();

    }

    @Override
    protected void onStart() {
        super.onStart();

        displayResultDrink();
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

    private void displayResultDrink()
    {
        //Created to simulate loading.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mResultTextView.setText(mOneUntappd.getBeerName());

                mImageView.setImageBitmap(mOneUntappd.getBeerImage());

            }

        }, 500);

    }


    private void waitForUntappd() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mUntappdManager.populateUntappdData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
