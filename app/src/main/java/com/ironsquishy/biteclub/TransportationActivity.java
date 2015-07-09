package com.ironsquishy.biteclub;

import java.util.Locale;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import AsyncTasks.YelpAsync;
import Callbacks.BusinessResponseRunnable;
import apiHelpers.YelpApiHandler.YelpData.SearchForBusinessesResponse;


public class TransportationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    //SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    //initialize pager adapter
    TransportationPagerAdapter device_pager_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        device_pager_adapter = new TransportationPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setAdapter(device_pager_adapter);
        mViewPager.setCurrentItem(1);
        BusinessResponseRunnable businessResponseRunnable = new BusinessResponseRunnable() {
            @Override
            public void runWithBusinessResponse(SearchForBusinessesResponse businessResponse) {

                //do stuff with businessResponse here!!!

            }
        };
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("restaurant", "500", "San Francisco");


    }

    /** Called when the user clicks the Feed Me! button */
    public void toResults(View view) {
        Intent intent = new Intent(this, MenuActivity.class);


//        if (check if location services is on(receive from location check)){
//        pass current location and radius defined by button to yelpAsync.
//        }
//        else if{
//        grab results from CurrentLocationActivity. Continue to pass defined radius to yelpAsync
//        }
        BusinessResponseRunnable businessResponseRunnable = new BusinessResponseRunnable() {
            @Override
            public void runWithBusinessResponse(SearchForBusinessesResponse businessResponse) {

            }
        };
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("500",null, "San Francisco");


        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
