package com.ironsquishy.biteclub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import ApiManagers.LocationHandler;
import ApiManagers.NetworkRequestManager;
import ApiManagers.RestaurantManager;
import Callbacks.GeneralCallback;
import apihelpers.SelectedBusiness;
import apihelpers.YelpApiHandler.Restaurant;
import apihelpers.YelpApiHandler.YelpData;

/**
 * Created by Eric on 7/8/2015.
 */
public class TransportationActivity extends FragmentActivity {

    ViewPager viewPager=null;
    private static RestaurantManager mRestaurantManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        viewPager = (ViewPager) findViewById(R.id.transportation_pager_id);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new TransportationPagerAdapter(fragmentManager));
        viewPager.setCurrentItem(1);

        //Call to Populate yelp data !!!!
        mRestaurantManager = new RestaurantManager();
        mRestaurantManager.populateYelpData(LocationHandler.getmLatitude(),LocationHandler.getmLongitude(), this);


    }

    /** Called when the user clicks the Feed Me! button for bus */
    public void toMenuActivityBus(View view) {

        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

    /** Called when the user clicks the Feed Me! button for car*/
    public void toMenuActivityCar(View view) {

        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

    /** Called when the user clicks the Feed Me! button for walk*/
    public void toMenuActivityWalk(View view) {

        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }
}

class TransportationPagerAdapter extends FragmentPagerAdapter{

    public TransportationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        switch(i) {
            case 0:
                return (new BusFragment());
            case 1:
                return (new CarFragment());
            case 2:
                return (new WalkFragment());
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "Bus";
            case 1:
                return "Drive";
            case 2:
                return "Walk";
        }
        return null;
    }
}
