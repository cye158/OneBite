package com.ironsquishy.biteclub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import apihelpers.SelectedBusiness;
import ApiManagers.NetworkRequestManager;
import Callbacks.SelectedBusinessRunnable;


/**
 * @author Allen Space
 * Description: Menu  activity with google maps fragment.
 * */
public class MenuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**Data Fields*/
    private static SelectedBusiness mSelectedBusiness;
    private static TextView mResultText;
    private static String mRandomStringName;
    private static SwipeRefreshLayout swipeRefreshLayout;

    /**
     * @Author Allen Space
     * Description: To create the menu activity.
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mResultText = (TextView) findViewById(R.id.resultText);

        randomizeYelpResponse();

        swipeRefresh();
    }

    /** Called when the user clicks the Go button - Eric */
    public void toNavi(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Information button - Eric */
    public void toInfo(View view) {
        //Untappd List.
        Intent intent = new Intent(this, UntappdList.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Search button - Eric */
    /** Was revised - Renz (7/20/15)**/
    public void toFilter(View view) {
        FilterOption dialog = new FilterOption();
        dialog.show(getFragmentManager(), "Filter Dialog Box");
    }

    /**
     * @Author Allen Space
     * Description: On start, creates process dialoge
     * */
    @Override
    protected void onStar
        super.onStart();

    }

    private void randomizeYelpResponse()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Getting Restaurant Result.");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        final Context context = this;

        //When Volley is done responding this handles what else to execute
        SelectedBusinessRunnable selectedBusinessRunnable = new SelectedBusinessRunnable() {
            @Override
            public void runWithRandomResult(SelectedBusiness randomizer) {

                //Add more stuff to do as approporiate or create more flexibility.

                mResultText.setText(randomizer.getmRestName());//This just set edit text box.

                progressDialog.dismiss();
            }
        };

        //Like yelp async this is the volley doing the same thing we can expand parameters for ease
        //of use, like filters and radius.
        NetworkRequestManager.getInstance().populateYelpData(selectedBusinessRunnable, this);
    }


    /**
     * @Author Eric Chen
     * Description: To create pull down to refresh.
     * */
    private void swipeRefresh()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_menu);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public void onRefresh() {
        //Created to simulate loading.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do stuff here.
                randomizeYelpResponse();
                swipeRefreshLayout.setRefreshing(false);
            }
        },100);
    }

}
