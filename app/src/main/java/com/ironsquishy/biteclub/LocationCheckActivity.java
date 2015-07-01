package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Guan on 6/27/2015.
 */

public class LocationCheckActivity extends Activity {

    static final int LOCATION_SETTINGS_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SETTINGS_REQUEST) {
            // user press back button from location settings
            // check if location services are now enabled

            boolean location_on = checkLocationStatus();

            if (location_on) {
                startActivity(new Intent(LocationCheckActivity.this, TransportationActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean location_on = checkLocationStatus();

        if (!location_on) {

            // yes button
            Button yesButton;
            setContentView(R.layout.activity_location_check);

            yesButton = (Button) findViewById(R.id.yes_button);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                }
            });

            // no button
            Button noButton;
            noButton = (Button) findViewById(R.id.no_button);

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(
                            LocationCheckActivity.this,
                            CurrentLocationActivity.class));
                }
            });
        } else {
            startActivity(new Intent(LocationCheckActivity.this, TransportationActivity.class));
            finish();
        }
    }

    /**
     * Check if location if on or off
     *
     * @return TRUE if location is on, FALSE if location is off
     */
    public boolean checkLocationStatus() {
        LocationManager locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean location_on = false;

        try {
            location_on = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {}

        return location_on;
    }
}