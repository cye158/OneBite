package com.ironsquishy.biteclub;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ApiManagers.LocationHandler;
import ApiManagers.UntappdManager;


public class UntappdFragment extends Fragment {

    private static TextView mResultTextView;
    private static UntappdManager mUntappdManager;
    private static Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.untappd_activity, container, false);

        /*
        mContext = getContext();

        mUntappdManager.populateUntappdData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);

        mUntappdManager = new UntappdManager();


        mResultTextView = (TextView) findViewById(R.id.resultText);

        mResultTextView.setText(mUntappdManager.getMostPopularDrink());
        */

        return null;
    }
}
