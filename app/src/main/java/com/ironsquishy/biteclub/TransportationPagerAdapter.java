package com.ironsquishy.biteclub;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Eric on 6/24/2015.
 */
public class TransportationPagerAdapter extends FragmentPagerAdapter {

    String[] transportation;
    String[] transportation_description;

    public TransportationPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        Resources resources = context.getResources();

        transportation = resources.getStringArray(R.array.transportation);
        transportation_description = resources.getStringArray(R.array.transportation_description);

    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putString(TransportationFragment.description_key, transportation_description[position]);
        bundle.putInt(TransportationFragment.image_id_key, get_device_image_id(position));
        TransportationFragment transportation_fragment = new TransportationFragment();
        transportation_fragment.setArguments(bundle);

        return transportation_fragment;
    }

    private int get_device_image_id(int position){
        int id = 0;
        switch (position)
        {
            case 0:
                id = R.mipmap.image_car;
                break;
            case 1:
                id = R.mipmap.image_bus;
                break;
            case 2:
                id = R.mipmap.image_walk;
                break;
            case 3:
                id = R.mipmap.image_uber;
                break;
        }
        return id;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return transportation[position];
    }

    @Override
    public int getCount() {
        return transportation.length;
    }
}
