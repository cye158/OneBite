package com.ironsquishy.biteclub;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import apiHelpers.UntappdFeedManager;


public class UntappdList extends ActionBarActivity {

    private static ListView untappdListView;
    private static UntappdFeedManager untappdData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untappd_list);

        untappdData = new UntappdFeedManager();

        untappdListView = (ListView) findViewById(R.id.untappdList);

        String[] items = new String[untappdData.getItemSize()];

        for(int i = 0; i < untappdData.getItemSize();i++)
        {
            items[i] = untappdData.getShortDescription(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,items);

        untappdListView.setAdapter(adapter);

    }

}
