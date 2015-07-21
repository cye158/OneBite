package com.ironsquishy.biteclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ApiManagers.UntappdFeedManager;
import ApiHelpers.SelectedBusiness;

public class InfoActivity extends AppCompatActivity {

    private static UntappdFeedManager untappdData;
    private static SelectedBusiness SelectedBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView untappdFeed = (TextView) findViewById(R.id.UntappdText);

        untappdData = new UntappdFeedManager();

        for(int i = 0; i < untappdData.getItemSize(); i++)
        {
            untappdFeed.append(untappdData.getShortDescription(i));
        }
    }

}
