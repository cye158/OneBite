package com.ironsquishy.biteclub;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;


import Callbacks.UntappdResultRunnable;
import ApiManagers.NetworkRequestManager;
import ApiHelpers.SelectedBusiness;
import ApiManagers.UntappdFeedManager;


public class UntappdList extends ActionBarActivity {

    private static ListView untappdListView;
    private static UntappdFeedManager untappdData;
    private static SelectedBusiness mSelectedBusiness;

    private static SwipeRefreshLayout swipeRefreshLayout;

    private static String[] items;
    private static int resource = android.R.layout.simple_list_item_1;
    private static int textViewResourceID = android.R.id.text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untappd_list);

        mSelectedBusiness = new SelectedBusiness();



    }
   @Override
    protected void onStart() {
        super.onStart();

        refreshFeed();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(untappdListView);
    }

    /** Called when the user clicks the Navigate button - Eric */
    public void toNavigation(View view) {
        //TODO: Implement navigation mode button and remove toast package.
        Toast.makeText(getApplicationContext(), "This should start navigation.",
                Toast.LENGTH_LONG).show();
    }


    private void refreshFeed()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Getting Untappd feed.");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();
        final Context context = this;

        UntappdResultRunnable untappdResultRunnable = new UntappdResultRunnable() {
            @Override
            public void runWithRandomResult(UntappdFeedManager untappdFeedManager) {
                untappdListView = (ListView) findViewById(R.id.untappdList);

                items = new String[untappdFeedManager.getItemSize()];

                for(int i = 0; i < untappdFeedManager.getItemSize();i++)
                {
                    items[i] = untappdFeedManager.getLongDescription(i);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, resource, textViewResourceID,items);

                untappdListView.setAdapter(adapter);

                progressDialog.dismiss();
            }
        };

        NetworkRequestManager.getInstance().populateUntappdFeed(untappdResultRunnable, mSelectedBusiness.getRestLatitude(), mSelectedBusiness.getRestLongitdude(), context);

    }
}
