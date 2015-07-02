package com.ironsquishy.biteclub;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import apiHelpers.LocationHandler;


public class MainActivity extends ActionBarActivity{

    static final int LOCATION_SETTINGS_REQUEST = 1;

    private static LocationHandler mLocation;
    private static double mLongitude;
    private static double mLatitude;

    private String address;
    private AlertDialog.Builder LocAlertBuilder;
    private AlertDialog.Builder AddAlertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLocation = new LocationHandler(this);
        LocAlertBuilder = new AlertDialog.Builder(this);
        AddAlertBuilder = new AlertDialog.Builder(this);
        final EditText txtInput = new EditText(this);

        if (LocServStatus()!=true) {
            //test alert dialog
            LocAlertBuilder.setTitle("Location Services is disabled");
            LocAlertBuilder.setMessage("Do you want to enable it?");

            //supposed to go location service settings instead home
            LocAlertBuilder.setPositiveButton("Sugar~ Yes please~", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                    finish();
                }
            });

            //just go coninue to app.
            LocAlertBuilder.setNegativeButton("Let it go~", new DialogInterface.OnClickListener() {

                //pops up window to enter address manually.
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AddAlertBuilder.setTitle("Enter your starting location");
                    AddAlertBuilder.setView(txtInput);
                    AddAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            address = txtInput.getText().toString();
                            Toast.makeText(getApplicationContext(), "Address has been entered.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AddAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "No address has been entered.", Toast.LENGTH_LONG).show();
                        }
                    });

                    AlertDialog addressInput = AddAlertBuilder.create();

                    addressInput.show();
                }
            });

            AlertDialog alert = LocAlertBuilder.create();

            alert.show();
            //end test dialog
        }

        Log.i("OnCreate" , "Successful call.");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mLocation.startConnect();



        /* just another alert with button

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Dialog box");

        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

        */

        Log.i("OnStart", "Successful");
    }

    public boolean LocServStatus() {
        LocationManager locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationSet = false;

        try {
            isLocationSet = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {}

        return isLocationSet;
    }
}

