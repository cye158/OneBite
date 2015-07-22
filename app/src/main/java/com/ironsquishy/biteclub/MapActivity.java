package com.ironsquishy.biteclub;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author Allen Space
 * Description: This was created to have a standalone activity of maps.
 *
 * */
public class MapActivity extends ActionBarActivity {

    /**
     * @author Allen Space
     * Description: on create goes to map fragment in layouts.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    /**
     * @author Allen Space
     * Descriptio: On start any changes.
     * */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /** Called when the user clicks the Navigate button - Eric*/
    public void toNavigation(View view) {
        //TODO: Implement navigation mode button and remove toast package.
        Toast.makeText(getApplicationContext(), "This should start navigation.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * @author Allen Space
     * Desciption: Private method that adds a fragment to the this activity.
     *             The MapFragment fragment layout must be on the xml layout.
     *
     * Only needed when making call to create.
     * */
    private void addFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
    }
}
