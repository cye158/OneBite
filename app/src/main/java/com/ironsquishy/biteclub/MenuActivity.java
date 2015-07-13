package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Callbacks.BusinessResponseRunnable;
import apiHelpers.LocationHandler;
import apiHelpers.YelpApiHandler.YelpData.Randomizer;
import apiHelpers.YelpApiHandler.YelpData.SearchForBusinessesResponse;
import YelpAsync.YelpAsync;


/**
 * @author Allen Space
 * Description: Menu  activity with google maps fragment.
 * */
public class MenuActivity extends ActionBarActivity {
    /**Data Fields*/
    private static Randomizer mRandomizer;
    private static TextView mResultText;
    private static String mRandomStringName;

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

        //Text field from menu_activity.xml
        mResultText = (TextView) findViewById(R.id.resultText);

        Log.i("onCreate", "Successful call.");
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

    /** Called when the user clicks the Retry button - Eric */
    public void toRetry(View view) {
        onStart();
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

        //Set to text field.

        BusinessResponseRunnable businessResponseRunnable = new BusinessResponseRunnable() {
            @Override
            public void runWithBusinessResponse(SearchForBusinessesResponse businessResponse) {
                mRandomizer = new Randomizer(businessResponse, getApplicationContext());
                mRandomStringName =  mRandomizer.getBusinessName(0);

                Log.i("YelpData", "At location: " + LocationHandler.streetAddress +"., " + LocationHandler.cityAddress);

                for(int i = 0; i < mRandomizer.getBusinessListSize(); i++)
                {
                    Log.i("YelpData", "Restuarant name: " + mRandomizer.getBusinessName(i));
                }

                mResultText.setText(mRandomStringName);
            }
        };

        //Set and execute yelp async.
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("Restaurant", "5234.5", LocationHandler.streetAddress + "., " + LocationHandler.cityAddress);

    }
    
    //TODO Delete addFragment if not using.
    /**
     * @author Allen Space
     * Desciption: Private method that adds a fragment to the this activity.
     *             The MapFragment fragment layout must be on the xml layout.
     * */
    private void addFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
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


}
