package com.ironsquishy.biteclub;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ApiManagers.DatabaseManager;
import ApiManagers.RestaurantManager;
import ApiManagers.UntappdManager;
import apihelpers.YelpApiHandler.Restaurant;


/**
 * @author Allen Space
 *         Description: Menu  activity with google maps fragment.
 */
public class ResultActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * Data Fields
     */
    private static TextView mResultText;
    private static String mRandomStringName;
    private static SwipeRefreshLayout swipeRefreshLayout;
    private static RestaurantManager mRestaurantManager;
    private static Restaurant mRestaurant;

    private static UntappdManager mUntappdManager;

    private final static int WALK = 0;
    private final static int BUS = 1;
    private final static int CAR = 2;
    private static int defaultTransportation = 2;
    private int transportModePreference = defaultTransportation;

    private static TextView addToData;
    private static ImageView mYelpImage, mYelpRating;

    private static DatabaseManager mDatabaseManager;

    private static Context mContext;

    private static TextView mExtYelpInfo, mMoreYelpInfo;

    private static TextView expandInfo, collapseInfo;
    private static LinearLayout mLinearLayout;
    private static ValueAnimator mExpandAnimator;
    private static ImageView car_button, bus_button, walk_button;

    /**
     * @Author Allen Space
     * Description: To create the menu activity.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mContext = this;

        mResultText = (TextView) findViewById(R.id.resultText);

        mYelpImage = (ImageView) findViewById(R.id.YelpImage);

        mYelpRating = (ImageView) findViewById(R.id.YelpRating);

        addToData = (TextView) findViewById(R.id.checkToAddFav);

        mDatabaseManager = DatabaseManager.getInstance(this);

        mExtYelpInfo = (TextView) findViewById(R.id.YelpInfo);

        mMoreYelpInfo = (TextView) findViewById(R.id.MoreYelpInfo);

        mRestaurantManager = RestaurantManager.getInstance();

        mUntappdManager = new UntappdManager(this);

        car_button = (ImageView) findViewById(R.id.car_button);
        bus_button = (ImageView) findViewById(R.id.bus_button);
        walk_button = (ImageView) findViewById(R.id.walk_button);

        //loads the mode of transportation from last session;
        transportModePreference = loadTransportation(defaultTransportation);
        randomizeYelpResponse(transportModePreference);

        swipeRefresh();

        expandInfo = (TextView) findViewById(R.id.showInfo);
        collapseInfo = (TextView) findViewById(R.id.hideInfo);

        mLinearLayout = (LinearLayout) findViewById(R.id.YelpInfoExpand);

        /**
         * @Author Darin modified by Eric
         * Description: Transportation modes
         */

        car_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                randomizeYelpResponse(CAR);
                //saves the mode of transportation chosen.
                saveTransportation(CAR);
            }
        });

        //This one Buses
        bus_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                randomizeYelpResponse(BUS);

                //saves the mode of transportation chosen.
                saveTransportation(BUS);
            }
        });

        //This one walks
        walk_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                randomizeYelpResponse(WALK);

                //saves the mode of transportation chosen.
                saveTransportation(WALK);
            }
        });

        mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        mLinearLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mLinearLayout.measure(widthSpec, heightSpec);

                        mExpandAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
                        return true;
                    }
                });

        expandInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    expand();
                } else {
                    collapse();
                }
            }
        });
        collapseInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                collapse();
            }
        });

        mUntappdManager.setMostPopularDrink();

    }

    /** Check for favorite. - Guan Editted by Eric and Darin**/
    public void checkFavAdd(View view) {
        if (mDatabaseManager.checkIfInDatabase(mRestaurant.getmRestName(),
                mRestaurant.getmLatitude(),
                mRestaurant.getmLongitude())) {
            Toast.makeText(getApplicationContext(), "Already added to favorites!",
                    Toast.LENGTH_SHORT).show();
        } else {
            //Add to result in text view to data.
            mDatabaseManager.addToDatabase(mRestaurant.getmRestName(),
                    mRestaurant.getmLatitude(),
                    mRestaurant.getmLongitude());
            Toast.makeText(getApplicationContext(), "Added to favorites!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Called when the user clicks the navigation button - Eric
     */
    public void toNavi(View view) {
        Intent intent = new Intent(this, MapActivity.class);

        //This is important for Untappd activity, needs population data.

        startActivity(intent);
    }

    /**
     * Called when the user clicks the untappdFeed FAB - Eric
     */
    public void toInfo(View view) {
        Intent i = new Intent(this, UntappdActivity.class);
        i.putExtra("restname", mRandomStringName);
        startActivity(i);
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
     * Description: On start, creates process dialog
     */
    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * @author Allen Space optimized by Eric
     * Description: ReShuffles the yelp data.
     * And displays on screen.
     */
    private void randomizeYelpResponse(int tranState) {

        switch (tranState) {
            case WALK:
                Toast.makeText(getApplicationContext(), "A restaurant within walking distance is shown",
                        Toast.LENGTH_SHORT).show();
                highlightSelection(WALK);

                //Get a random restuarant based on walking distance
                mRestaurant = mRestaurantManager.getRandRestWalk();
                break;

            case BUS:
                Toast.makeText(getApplicationContext(), "A restaurant within bus distance is shown",
                        Toast.LENGTH_SHORT).show();
                highlightSelection(BUS);

                //Get a random restuarant based on bus distance
                mRestaurant = mRestaurantManager.getRandRestBus();
                break;

            case CAR:
                Toast.makeText(getApplicationContext(), "A restaurant within driving distance is shown",
                        Toast.LENGTH_SHORT).show();
                highlightSelection(CAR);

                //Get a random restuarant based on driving distance
                mRestaurant = mRestaurantManager.getRandRestCar();
                break;
        }
        setYelpInfo();
    }

    /**
     * @Author Eric Chen
     * Description: To create pull down to refresh.
     */
    private void swipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        Toast.makeText(getApplicationContext(), "Swipe down for another choice!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        //Created to simulate loading.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transportModePreference = loadTransportation(defaultTransportation);
                randomizeYelpResponse(transportModePreference);
                swipeRefreshLayout.setRefreshing(false);
            }

        }, 1000);


    }

    /**
     * @Author Edward Yao
     * Description: To expand yelp information
     */
    private void expand() {
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);
        expandInfo.setText("Hide Info");
        mExpandAnimator.start();
    }

    /**
     * @Author Edward Yao
     * Description: To collapse yelp information
     */
    private void collapse() {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);
                expandInfo.setText("More Info");
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    /**
     * @Author Edward Yao
     * Description: To calculate the length need to expand to show all yelp information
     */
    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /**
     * @Author Eric Chen
     * Description: Gets yelp information from Restaurant class
     */
    private void setYelpInfo() {
        //Set the Text name.
        mResultText.setText(mRestaurant.getmRestName());

        mYelpImage.setImageBitmap(mRestaurant.getmRestImage());

        mYelpRating.setImageBitmap(mRestaurant.getRatingImage());

        //Set the Descripiton and ratings
        //TODO: ADD MORE YELP INFO STRINGS

        /*String closedStatus;
        if (mRestaurant.getIsClosed()){
            closedStatus = "CLOSED";
        }
        else
            closedStatus = "OPEN";
            */
        double distanceMiles;
        double distanceMeters;

        distanceMeters = mRestaurant.getDistancefrom();

        distanceMiles = distanceMeters * 0.000621371;

        String distanceFrom = String.format("%.2f", distanceMiles);

        mExtYelpInfo.setText("Cuisine Style: " + mRestaurant.getmCuisineStyle() + "\n" +
                                "Number of Reviews: " + mRestaurant.getReviewCount() + "\n" +
                                "Distance: " + distanceFrom + " miles away");
        mMoreYelpInfo.setText("Phone Number: " + formatPhoneNum(mRestaurant.getPhoneNumber()) + "\n\n" +
                "Description: " + mRestaurant.getmDescription() + "... ");
    }


    /** Shared preference for transportation by Renz - 7/29/15 **/
    /*
        Save method to store the transportation mode preference into a file named
        "transport_preference". The file will be used by loadTransportation method.
    */
    public void saveTransportation(int defaultTransportation) {
        SharedPreferences transport_pref;
        SharedPreferences.Editor editor;
        transport_pref = getApplicationContext().getSharedPreferences("transport_preference", Context.MODE_PRIVATE);
        editor = transport_pref.edit();
        editor.putInt("transportation_mode", defaultTransportation);
        editor.commit();
    }

    /*
        Load method that loads the mode of transportation from "transport_preference" file. Then
        passes the integer that corresponds to the saved transportation mode as the new
        defaultTransportation. If the file is empty then it returns the same mode(default value).
    */
    public Integer loadTransportation(int defaultTransportation) {
        SharedPreferences transport_pref;
        transport_pref = getApplicationContext().getSharedPreferences("transport_preference", Context.MODE_PRIVATE);
        return transport_pref.getInt("transportation_mode", defaultTransportation);
    }

    /**
     * @Author Eric Chen
     * Description: This highlights the transportation mode.
     *
     * Edited the icons and rescaling by Renz 7/30/15
     **/
    private void highlightSelection(int transportation) {
        switch (transportation){
            case CAR:
                car_button.setImageBitmap(toBitmap(R.drawable.trans_car_select));
                bus_button.setImageBitmap(toBitmap(R.drawable.trans_bus));
                walk_button.setImageBitmap(toBitmap(R.drawable.trans_walk));
                break;
            case BUS:
                car_button.setImageBitmap(toBitmap(R.drawable.trans_car));
                bus_button.setImageBitmap(toBitmap(R.drawable.trans_bus_select));
                walk_button.setImageBitmap(toBitmap(R.drawable.trans_walk));
                break;
            case WALK:
                car_button.setImageBitmap(toBitmap(R.drawable.trans_car));
                bus_button.setImageBitmap(toBitmap(R.drawable.trans_bus));
                walk_button.setImageBitmap(toBitmap(R.drawable.trans_walk_select));
                break;
            default:
                car_button.setImageBitmap(toBitmap(R.drawable.trans_car));
                bus_button.setImageBitmap(toBitmap(R.drawable.trans_bus));
                walk_button.setImageBitmap(toBitmap(R.drawable.trans_walk));
                break;
        }
    }

    /**
     *
     * Copied & modified from google marker class to be used in this class - Renz 7/30/15
     *
     * @author: Guan
     * Description: turn a non .bmp image into bitmap and resize to prevent blurriness
     * @param id the resource ID of the image data
     * @return Bitmap object of resized image
     *
     */
    private Bitmap toBitmap(int id) {
        Bitmap marker = BitmapFactory.decodeResource(this.getResources(), id);
        marker = Bitmap.createScaledBitmap(marker, 220, 220, true);
        return marker;
    }

    /**
     * @author: Guan
     * Description: Format US phone number (ONLY)
     * @param phoneNum A string value of phone number
     * @return A string of formated phone number
     */
    private String formatPhoneNum(String phoneNum) {
        if (phoneNum != null) {
            return "(" + phoneNum.substring(0, 3) + ")-" +
                    phoneNum.substring(3, 6) + "-" +
                    phoneNum.substring(6, 10);
        }

        return " Not Available";
    }
}


