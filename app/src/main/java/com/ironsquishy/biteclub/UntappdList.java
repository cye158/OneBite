package com.ironsquishy.biteclub;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(untappdListView);
    }

    /** Called when the user clicks the Navigate button - Eric */
    public void toNavigation(View view) {
        //TODO: Implement navigation mode button and remove toast package.
        Toast.makeText(getApplicationContext(), "This should start navigation.",
                Toast.LENGTH_LONG).show();
    }

}
