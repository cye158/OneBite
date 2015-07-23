package com.ironsquishy.biteclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ApiManagers.UntappdFeedManager;
import apihelpers.SelectedBusiness;

public class InfoActivity extends AppCompatActivity {

    private static TextView mExtYelpInfo;
    private static SelectedBusiness mSelectedBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mExtYelpInfo = (TextView) findViewById(R.id.YelpInfo);

        mSelectedBusiness = new SelectedBusiness();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mExtYelpInfo.setText(mSelectedBusiness.getLongDescriptionRest());

    }

    /** Called when the user clicks the Information button - Eric */
    public void toInfo(View view) {
        //Untappd List.
        Intent intent = new Intent(this, UntappdList.class);
        startActivity(intent);

    }

}

