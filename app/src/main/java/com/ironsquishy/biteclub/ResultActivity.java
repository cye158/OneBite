package com.ironsquishy.biteclub;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ApiManagers.DatabaseManager;
import ApiManagers.LocationHandler;
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

    private static TextView addToData;
    private static ImageView mYelpImage, mYelpRating;

    private static DatabaseManager mDatabaseManager;

    private static Context mContext;

    private AlertDialog.Builder filterDialog;
    private String inputFilter = "\n Filtered: ";

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
    protected void onCreate(Bundle savedInstanceState) {
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

        mUntappdManager = new UntappdManager();

        randomizeYelpResponse(CAR);

        swipeRefresh();

        expandInfo = (TextView) findViewById(R.id.showInfo);
        collapseInfo = (TextView) findViewById(R.id.hideInfo);

        mLinearLayout = (LinearLayout) findViewById(R.id.YelpInfoExpand);

        car_button = (ImageView) findViewById(R.id.car_button);
        bus_button = (ImageView) findViewById(R.id.bus_button);
        walk_button = (ImageView) findViewById(R.id.walk_button);


        /**
         * @Author Darin modified by Eric
         * Description: Transportation modes
         */

        car_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                randomizeYelpResponse(CAR);
                Toast.makeText(getApplicationContext(), "Driving distance restaurants shown",
                        Toast.LENGTH_SHORT).show();
                car_button.setImageResource(R.drawable.car_icon001_selected);
                bus_button.setImageResource(R.drawable.bus_icon000);
                walk_button.setImageResource(R.drawable.walk_icon000);
            }
        });

        //This one Buses
        bus_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                randomizeYelpResponse(BUS);
                Toast.makeText(getApplicationContext(), "Bus distance restaurants shown",
                        Toast.LENGTH_SHORT).show();
                car_button.setImageResource(R.drawable.car_icon001);
                bus_button.setImageResource(R.drawable.bus_icon000_selected);
                walk_button.setImageResource(R.drawable.walk_icon000);
            }
        });

        //This one walks
        walk_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                randomizeYelpResponse(WALK);
                Toast.makeText(getApplicationContext(), "Walking distance restaurants shown",
                        Toast.LENGTH_SHORT).show();
                car_button.setImageResource(R.drawable.car_icon001);
                bus_button.setImageResource(R.drawable.bus_icon000);
                walk_button.setImageResource(R.drawable.walk_icon000_selected);
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
                //Get a random restuarant based on walking distance
                mRestaurant = mRestaurantManager.getRandRestWalk();
                break;
            case BUS:
                //Get a random restuarant based on bus distance
                mRestaurant = mRestaurantManager.getRandRestBus();
                break;
            case CAR:
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
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        //Created to simulate loading.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                randomizeYelpResponse(CAR);
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

        mYelpRating.setImageBitmap(mRestaurant.getmRatingImage());

        //Set the Descripiton and ratings
        //TODO: ADD MORE YELP INFO STRINGS

        String closedStatus;
        if (mRestaurant.getIsClosed()){
            closedStatus = "CLOSED";
        }
        else
            closedStatus = "OPEN";

        mExtYelpInfo.setText("Number of Reviews: " + mRestaurant.getReviewCount() + "\n" +
                "The Restaurant is currently: " + closedStatus + "\n" +
                "Distance: ");
        mMoreYelpInfo.setText("Description: " + mRestaurant.getmDescription());
    }
}

