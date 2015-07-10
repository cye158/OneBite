package com.ironsquishy.biteclub;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Search button - Eric */
    public void toSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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
                mRandomizer = new Randomizer(businessResponse);
                mRandomStringName =  mRandomizer.getBusinessName(0);

                Log.i("YelpData", "At location: " + LocationHandler.streetAddress +"., " + LocationHandler.cityAddress);

                for(int i = 0; i < mRandomizer.getBusinessListSize(); i++)
                {
                    Log.i("YelpData", "Restuarant name: " + mRandomizer.getBusinessName(i));
                }

                mResultText.setText(mRandomStringName);
            }
        };

        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("Restaurant", "5234.5", LocationHandler.streetAddress + "., " + LocationHandler.cityAddress);

    }

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


}
<<<<<<< HEAD
||||||| merged common ancestors
=======
package com.ironsquishy.biteclub;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import apiHelpers.LocationHandler;


/**
 * @author Allen Space
 * Descption: Menu  activity with google maps fragment.
 * */
public class MenuActivity extends ActionBarActivity {
    /**Data Fields*/
    private static Randomizer mRandomizer;
    private static TextView mResultText;
    private static LocationHandler mLocation;


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

        //Instantiate locationHandler object
        mLocation = new LocationHandler(this);

        //Start the connection to location service.
        mLocation.startConnect();


        Log.i("onCreate", "Successful call.");
    }

    /**
     * @Author Allen Space
     * Description: On start, creates process dialoge
     * */
    @Override
    protected void onStart() {
        super.onStart();

        //Start process dialog
        final ProgressDialog ringProgressDialog = ProgressDialog.show(MenuActivity.this,
                "Whoa hold on Bro!!!", "First we have to find you!!", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Log.i("LOCATION", "Latitude and Longitude: " + mLocation.getmLatitude() + ", " + mLocation.getmLongitude());


                    //Add Google maps  to activity.
                    addFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }

        }).start();

        //Randomizer class instantiate
        mRandomizer = new Randomizer();

        //Just gets a random string from randomizer
        String pResultStr = ("Result: " + mRandomizer.getRandomString());

        //Set to text field.
        mResultText.setText(pResultStr);

    }

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
}
>>>>>>> 9cca971cb76a56959af29b9d02d5a8a8d070e2e0
||||||| merged common ancestors
=======

>>>>>>> afa55c48d98c5e32536783a6176cd6451bbec976
