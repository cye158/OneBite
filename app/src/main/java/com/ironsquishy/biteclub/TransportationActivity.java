package com.ironsquishy.biteclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import Callbacks.BusinessResponseRunnable;
import apiHelpers.LocationHandler;
import apiHelpers.YelpApiHandler.YelpData.SearchForBusinessesResponse;

/**
 * Created by Eric on 7/8/2015.
 */
public class TransportationActivity extends FragmentActivity {

    ViewPager viewPager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        viewPager = (ViewPager) findViewById(R.id.transportation_pager_id);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new TransportationPagerAdapter(fragmentManager));
        viewPager.setCurrentItem(1);

        Double dlatitude = LocationHandler.getmLatitude();
        Double dlongitude = LocationHandler.getmLongitude();

        String sLatitude = Double.toString(dlatitude);
        String sLongitude = Double.toString(dlongitude);
    }

    /** Called when the user clicks the Feed Me! button for bus */
    public void toMenuActivityBus(View view) {
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
        /*
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("500",null, "San Francisco");
        */
        startActivity(intent);
    }

    /** Called when the user clicks the Feed Me! button for car*/
    public void toMenuActivityCar(View view) {
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
        /*
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("500",null, "San Francisco");
        */
        startActivity(intent);
    }

    /** Called when the user clicks the Feed Me! button for walk*/
    public void toMenuActivityWalk(View view) {
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
        /*
        YelpAsync yelpAsync = new YelpAsync(businessResponseRunnable); //"500", "San Francisco");
        yelpAsync.execute("500",null, "San Francisco");
        */
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
