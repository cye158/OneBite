package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ApiManagers.LocationHandler;
import ApiManagers.UntappdManager;
import apihelpers.Untappd.OneUntappd;


public class UntappdActivity extends Activity{


    private static Context mContext;
    private static UntappdManager mUntappdManager;
    private static String mFilledComments;

    private static TextView mResultTextView;
    private static TextView mComments;
    private static TextView mRsltStyle;
    private static TextView mTotalRatings;
    private static TextView mDescription;


    private static ImageView mImageView;

    private static ListView mUntappdListV;

    private static OneUntappd mOneUntappd;

    private static int resource = android.R.layout.simple_list_item_1;
    private static int textViewResourceID = android.R.id.text1;

    private static Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.untappd_activity);

        mIntent =  this.getIntent();

        mContext = this;

        mResultTextView = (TextView) findViewById(R.id.resultText);

        mRsltStyle = (TextView) findViewById(R.id.BeerStyle);

        mTotalRatings = (TextView) findViewById(R.id.BeerNumberOfReview);

        mDescription = (TextView) findViewById(R.id.BeerDescriptionText);

        mImageView = (ImageView) findViewById(R.id.YelpImage);

        mUntappdListV = (ListView) findViewById(R.id.untappdList);


        mUntappdManager = new UntappdManager(mContext);

        mOneUntappd = mUntappdManager.getOneTappd();

        displayResultDrink();

    }

    @Override
    protected void onStart() {
        super.onStart();

        displayResultDrink();
    }

    public void toUntappdList(View view)
    {
        Intent intent = new Intent(this, UntappdList.class);
        startActivity(intent);
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

                mRsltStyle.setText(mOneUntappd.getmBeerStyle());

                mTotalRatings.setText(String.valueOf(mOneUntappd.getmTotalReviews()));

                if (mOneUntappd.getmDescription() == "")
                {
                    mDescription.setText("No Description provided.");
                } else {
                    mDescription.setText(mOneUntappd.getmDescription());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                        resource, textViewResourceID, mOneUntappd.getmFilledComments());

                mUntappdListV.setAdapter(adapter);

            }

        }, 500);

    }

}
