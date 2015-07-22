package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ApiManagers.DatabaseManager;
import apihelpers.SelectedBusiness;


/**
 * @author Allen Space
 * Description: Menu  activity with google maps fragment.
 * */
public class MenuActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**Data Fields*/
    private static SelectedBusiness mSelectedBusiness;
    private static TextView mResultText;
    private static String mRandomStringName;
    private static SwipeRefreshLayout swipeRefreshLayout;

    private static RadioButton addToData;
    private static DatabaseManager mDatabaseManager;

    private AlertDialog.Builder filterDialog;
    private String inputFilter = "\n Filtered: ";

    /**
     * @Author Allen Space
     * Description: To create the menu activity.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mResultText = (TextView) findViewById(R.id.resultText);


        addToData = (RadioButton) findViewById(R.id.checkToAddFav);

        mDatabaseManager = new DatabaseManager(this);

        swipeRefresh();
    }

    /** Check for favorite.**/
    public void checkFavAdd(View view)
    {
        if(addToData.isChecked()) {
            //Add to result in text view to data.
            mDatabaseManager.addToDatabase(mRandomStringName);

            Toast.makeText(getApplicationContext(), "Added to favorites.",
                    Toast.LENGTH_SHORT).show();

        }
    }

    /** Called when the user clicks the Go button - Eric */
    public void toNavi(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Information button - Eric */
    public void toInfo(View view) {
        //Untappd List.
        Intent intent = new Intent(this, UntappdList.class);
        startActivity(intent);

    }

    /** Called when the user clicks the Search button - Eric */
    /* Being revised by Renz*/
    public void toSearch(View view) {
        filterOption();
        AlertDialog alert = filterDialog.create();
        alert.show();

        //Intent intent = new Intent(this, SearchActivity.class);
        //startActivity(intent);
    }

    /**
     * @Author Allen Space
     * Description: On start, creates process dialoge
     * */
    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSelectedBusiness = new SelectedBusiness();

                mRandomStringName = mSelectedBusiness.getmRestName();

                mResultText.setText(mRandomStringName);
            }
        }, 1000);

    }


    /**
     * @author Renz
     * Desciption: Private method that pops an dialog box to let user check the filter
     *             to be used for next randomzed result.
     * */
    private void filterOption(){
        FilterOption dialog = new FilterOption();
        dialog.show(getFragmentManager(), "Filter Dialog Box");
    }

    /**
     * @author Allen Space
     * Description: ReShuffles the yelp data.
     *              And displays on screen.
     **/
    private void randomizeYelpResponse()
    {
        mSelectedBusiness = new SelectedBusiness();

        mSelectedBusiness.reShuffleBusinessList();

        mRandomStringName = mSelectedBusiness.getmRestName();

        mResultText.setText(mRandomStringName);
    }


    /**
     * @Author Eric Chen
     * Description: To create pull down to refresh.
     * */
    private void swipeRefresh()
    {
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
                addToData.setChecked(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

}
