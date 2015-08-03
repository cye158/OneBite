package com.ironsquishy.biteclub;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ApiManagers.UntappdManager;


public class UntappdList extends ActionBarActivity {

    private static ListView untappdListView;
    private static UntappdManager mUntappdManager;
    private static List<String> mComments;
    private static String[] mFilledComments;
    private static ListView mUntappdListV;


    private static SwipeRefreshLayout swipeRefreshLayout;

    private static String[] items;
    private static int resource = android.R.layout.simple_list_item_1;
    private static int textViewResourceID = android.R.id.text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untappd_list);

        mUntappdListV = (ListView) findViewById(R.id.untappdList);

        mUntappdManager = new UntappdManager(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                resource, textViewResourceID, mUntappdManager.getFilledComments());

        mUntappdListV.setAdapter(adapter);


    }

}

