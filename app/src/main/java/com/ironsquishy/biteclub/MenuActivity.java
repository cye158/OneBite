package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ApiManagers.DatabaseManager;
import ApiManagers.RestaurantManager;
import apihelpers.YelpApiHandler.Restaurant;


/**
 * @author Allen Space
 *         Description: Menu  activity with google maps fragment.
 */
public class MenuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * Data Fields
     */
    private static TextView mResultText;
    private static String mRandomStringName;
    private static SwipeRefreshLayout swipeRefreshLayout;
    private static RestaurantManager mRestaurantManager;
    private static Restaurant mRestaurant;

    private static TextView addToData;
    private static ImageView mYelpImage;

    private static DatabaseManager mDatabaseManager;

    private static Context mContext;

    private AlertDialog.Builder filterDialog;
    private String inputFilter = "\n Filtered: ";

    private static TextView mExtYelpInfo;

    /**
     * @Author Allen Space
     * Description: To create the menu activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mContext = this;

        mResultText = (TextView) findViewById(R.id.resultText);

        mYelpImage = (ImageView) findViewById(R.id.YelpImage);

        addToData = (TextView) findViewById(R.id.checkToAddFav);

        mDatabaseManager = new DatabaseManager(this);

        mExtYelpInfo = (TextView) findViewById(R.id.YelpInfo);

        mRestaurantManager = new RestaurantManager();

        randomizeYelpResponse();

        swipeRefresh();
    }

    /**
     * Check for favorite.*
     */
    public void checkFavAdd(View view) {
        //Add to result in text view to data.
        mDatabaseManager.addToDatabase(mRestaurant.getmRestName(),
                mRestaurant.getmLatitude(),
                mRestaurant.getmLongitude());

        //TODO There should be a check to see if it has already been added to favorites, then the toast message should say "already added"
        Toast.makeText(getApplicationContext(), "Added to favorites.",
                Toast.LENGTH_SHORT).show();

    }

    /**
     * Called when the user clicks the navigation floating action button - Eric
     */
    public void toNavi(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicks the untappdFeed button - Eric
     */
    public void toInfo(View view) {
        Intent intent = new Intent(this, UntappdActivity.class);
        intent.putExtra("restname", mRestaurant.getmRestName());
        startActivity(intent);
    }

    /** Called when the user clicks the Filter button - Eric */
    /**
     * Revised by Renz
     */
    public void toFilter(View view) {
        FilterOption dialog = new FilterOption();
        dialog.show(getFragmentManager(), "Filter Dialog Box");
    }

    /**
     * @Author Allen Space
     * Description: On start, creates process dialoge
     */
    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * @author Allen Space
     * Description: ReShuffles the yelp data.
     * And displays on screen.
     */
    private void randomizeYelpResponse() {

        //Get a random restuarant.
        mRestaurant = mRestaurantManager.getRandRestCar();

        //Set the Text name.
        mResultText.setText(mRestaurant.getmRestName());

        //Set the Descripiton and ratings

        mExtYelpInfo.setText(mRestaurant.getmDescription() + "\n" + mRestaurant.getmRatings());

    }


    /**
     * @Author Eric Chen
     * Description: To create pull down to refresh.
     */
    private void swipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_menu);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        //Created to simulate loading.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do stuff here.
                randomizeYelpResponse();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }



}