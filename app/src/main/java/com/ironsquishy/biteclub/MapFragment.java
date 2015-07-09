package com.ironsquishy.biteclub;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ironsquishy.biteclub.R;

import apiHelpers.LocationHandler;

/**
 * Created by Allen Space on 6/24/2015.
 */
public class MapFragment extends Fragment {

    /**Data Fields*/
    private static final double LONGITUDE = -122.431297;
    private static final double LATITUDE = 37.773972;
    private MapView mapView;
    private static GoogleMap gMap;
    private static double mLongitude = LONGITUDE;
    private static double mLatitude = LATITUDE;

    /**
     * @author Allen Space
     * Description: When fragment is called this will be called.
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gMap = mapView.getMap();

        //Get client location.
        useClientLocation();

        //set operation.
        if(isCurrentLocation()) {

            markClient();

            moveCameraToClient();
        }

        return view;
    }

    /**
     * @author Allen Space
     * */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * @author Allen Space
     * */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onResume();
    }

    /**
     * @author Allen Space
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * @author Allen Space
     * */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * @author Allen Space
     * Description: Set the latitude and longitude variables to current device location.
     * */
    private void useClientLocation()
    {
        mLatitude = LocationHandler.getmLatitude();
        mLongitude = LocationHandler.getmLongitude();
    }

    /**
     * @author Allen Space
     * Description: Sets a marker on markers postion making Rose color.
     * */
    private void markClient()
    {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(mLatitude, mLongitude))
                .title("Your Position!");

        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        gMap.addMarker(marker);
    }

    /**
     * @author Allen Space
     * Description: Moves the postion of the camera view to clients marker.
     * */
    private void moveCameraToClient()
    {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mLatitude, mLongitude))
                .zoom(12)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * @author Allen Space
     * @param pNewLat Double value passed for latitude.
     * @param pNewLong Double value passed or longitude.
     * Description: Use to add adition makers based on latitude and longitude values.
     */
    public void addRestMark(Double pNewLat, Double pNewLong)
    {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng( pNewLat, pNewLat))
                .title("Mark");

        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        gMap.addMarker(marker);
    }

    /**
     * @author Allen Space
     * Description: Checks for change in Latitude and Longitude.
     * */
    private boolean isCurrentLocation()
    {
        if(mLatitude != LATITUDE && mLongitude != LONGITUDE)
        {
            return true;
        }else{
            return false;
        }
    }
}
