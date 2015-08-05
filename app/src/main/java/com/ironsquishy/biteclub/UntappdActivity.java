package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import ApiManagers.UntappdManager;
import apihelpers.Untappd.OneUntappd;


public class UntappdActivity extends Activity{

    /**Data Fields*/
    private static Context mContext;
    private static UntappdManager mUntappdManager;
    private static OneUntappd mOneUntappd;

    //Text view objects
    private static TextView mResultTextView;
    private static TextView mComments;
    private static TextView mRsltStyle;
    private static TextView mTotalRatings;
    private static TextView mDescription;
    private static TextView mRatings;

    //Image view objects
    private static ImageView mImageView;

    //List view objects
    private static ListView mUntappdListV;
    private static int resource = android.R.layout.simple_list_item_1;
    private static int textViewResourceID = android.R.id.text1;






    private static Intent mIntent;

    private static LinearLayout untappdUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.untappd_activity);

        //Init all objects in the during creation of the activity.

        mIntent = this.getIntent();

        mContext = this;

        mResultTextView = (TextView) findViewById(R.id.resultText);

        mRsltStyle = (TextView) findViewById(R.id.BeerStyle);

        mRatings = (TextView) findViewById(R.id.BeerRating);

        mTotalRatings = (TextView) findViewById(R.id.BeerNumberOfReview);

        mDescription = (TextView) findViewById(R.id.BeerDescriptionText);

        mImageView = (ImageView) findViewById(R.id.UntappdImage);

        mUntappdListV = (ListView) findViewById(R.id.untappdList);


        mUntappdManager = new UntappdManager(mContext);

        mOneUntappd = mUntappdManager.getOneTappd();

        displayResultDrink();

        untappdUrl = (LinearLayout) this.findViewById(R.id.UntappdUrlHeaderImage);

        untappdUrl.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.untappd.com/beer/top_rated"));
                startActivity(intent);
            }
        });

    }


    /**
     * @author Allen Space
     * Descrition: A helper method for populating data on the view.
     * */
    private void displayResultDrink()
    {
        //Try to land objects.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mResultTextView.setText(mOneUntappd.getBeerName());

                mImageView.setImageBitmap(mOneUntappd.getBeerImage());

                mRsltStyle.setText(mOneUntappd.getmBeerStyle());

                mTotalRatings.setText(String.valueOf(mOneUntappd.getmTotalReviews()));

                //mRatings.setText(String.valueOf(mOneUntappd.getmRatings()));

                //Check if Description string is empty.
                if (mOneUntappd.getmDescription() == "")
                {
                    mDescription.setText("No Description provided.");
                } else {
                    mDescription.setText(mOneUntappd.getmDescription());
                }

                //For ListView under Reviews text.
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                        resource, textViewResourceID, mOneUntappd.getmFilledComments());

                mUntappdListV.setAdapter(adapter);

            }

        }, 250);

    }

}
