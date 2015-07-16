package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Callbacks.BusinessResponseRunnable;
import apiHelpers.Untappd.FetchUntappdData;
import apiHelpers.Untappd.UntappdData;
import apiHelpers.googleapis.LocationHandler;
import apiHelpers.YelpApiHandler.YelpData.Randomizer;
import apiHelpers.YelpApiHandler.YelpData.SearchForBusinessesResponse;
import YelpAsync.YelpAsync;


/**
 * @author Allen Space
 * Description: Menu  activity with google maps fragment.
 * */
public class MenuActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    /**Data Fields*/
    private static Randomizer mRandomizer;
    private static TextView mResultText;
    private static String mRandomStringName;
    private static SwipeRefreshLayout swipeRefreshLayout;

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
        randomizeYelpResposne();
        SystemClock.sleep(500);

        swipeRefresh();
    }

    /** Called when the user clicks the Go button - Eric */
    public void toNavi(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Information button - Eric */
    public void toInfo(View view) {


        Intent intent = new Intent(this, InfoActivity.class);
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



    }
    

    /**
     * @author Renz
     * Desciption: Private method that pops an dialog box to let user check the filter
     *             to be used for next randomzed result.
     * */
    private void filterOption(){

        //Variables and list of the filter option
        final ArrayList arrayFilter = new ArrayList();
        filterDialog = new AlertDialog.Builder(this);
        final String[] strFilters = { "American", "Asian", "Chinese", "Filipino", "Italian", "Japanese",
                "Korean", "Vietnamese", "Thai", "Vegetarian" };

        //Process and filters are saved in array.
        String filterTitle = "\nFilters added:\n";
        filterDialog.setTitle("Filter Catergories");
        filterDialog.setMultiChoiceItems(strFilters, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    arrayFilter.add(which);
                } else if (arrayFilter.contains(which)) {
                    arrayFilter.remove(Integer.valueOf(which));
                }
            }
        });

        filterDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < arrayFilter.size(); i++) {
                    if(i == arrayFilter.size())
                        inputFilter += strFilters[(Integer) arrayFilter.get(i)];
                    else
                        inputFilter += strFilters[(Integer) arrayFilter.get(i)] + ", ";
                }
                Toast.makeText(getApplicationContext(), inputFilter, Toast.LENGTH_LONG).show();
            }
        });

        filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Filters have been cancelled", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void randomizeYelpResposne()
    {
        BusinessResponseRunnable businessResponseRunnable = new BusinessResponseRunnable() {
            @Override
            public void runWithBusinessResponse(SearchForBusinessesResponse businessResponse) {
                mRandomizer = new Randomizer(businessResponse, getApplicationContext());
                mRandomStringName =  mRandomizer.getBusinessName(0);

                Log.i("YelpData", "At location: " + LocationHandler.streetAddress + "., " + LocationHandler.cityAddress);

                for(int i = 0; i < mRandomizer.getBusinessListSize(); i++)
                {
                    Log.i("YelpData", "Restuarant name: " + mRandomizer.getBusinessName(i));
                }
                mResultText.setText(mRandomStringName);

            }
        };

        //Set and execute yelp async.
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable, this);
        yelpAsync.execute("Restaurant", "7000.0", LocationHandler.streetAddress + "., " + LocationHandler.cityAddress);
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
                randomizeYelpResposne();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

}
