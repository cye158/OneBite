package com.ironsquishy.biteclub;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import apiHelpers.LocationHandler;
import apiHelpers.YelpApiHandler.YelpData.Randomizer;

/**
 * Created by Allen Space on 6/24/2015.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    /**Data Fields*/
    private static final double LONGITUDE = -122.431297;
    private static final double LATITUDE = 37.773972;
    private MapView mapView;
    private static GoogleMap gMap;
    private static double mLongitude = LONGITUDE;
    private static double mLatitude = LATITUDE;
    private static LocationHandler mLocation;

    private static final String TAG = "LOCATION";


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

        mapView.getMapAsync(this);

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
    private void markClient(GoogleMap pGoogleMap)
    {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(LocationHandler.getmLatitude(), LocationHandler.getmLongitude()))
                .title("Your Position!")
                .snippet(LocationHandler.streetAddress + ": " + LocationHandler.cityAddress);

        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        pGoogleMap.addMarker(marker);
    }

    /**
     * @author Allen Space
     * Description: Moves the postion of the camera view to clients marker.
     * */
    private void moveCameraToClient(GoogleMap pGoogleMap)
    {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(LocationHandler.getmLatitude(), LocationHandler.getmLongitude()))
                .zoom(12)
                .build();

        pGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * @author Allen Space
     * @param pNewLat Double value passed for latitude.
     * @param pNewLong Double value passed or longitude.
     * Description: Use to add adition makers based on latitude and longitude values.
     */
    public void addRestMark(GoogleMap pGoogleMap, String pName, String pAddress, Double pNewLat, Double pNewLong)
    {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng( pNewLat, pNewLong))
                .snippet(pAddress)
                .title(pName);

        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        pGoogleMap.addMarker(marker);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.i(TAG, "Building Map.");

        Randomizer randomizer = new Randomizer();

        addRestMark(googleMap, randomizer.mRestName, randomizer.mRestAddress, randomizer.mRestLatitude, randomizer.mRestLonigtude);

        markClient(googleMap);
        moveCameraToClient(googleMap);
        addFakeMarkers(googleMap);
    }

    /**
     * @author Guan
     * @param latLng LatLng Object that contains latitude and longitude.
     * @param  name Name of the location
     * Description: Adds an orange marker to the map with giving coordinates
     */
    private void addLocationToMap(GoogleMap googleMap, LatLng latLng, String name) {
        MarkerOptions marker = new MarkerOptions()
                .position(latLng)
                .title(name)
                .snippet("You been to here!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                );

        googleMap.addMarker(marker);

    }

    /**
     * @author Guan
     * Description: Add fake markers to map for Demo purpuse
     *              remove this when connected to history database
     */
    private void addFakeMarkers(GoogleMap googleMap) {
        addLocationToMap(googleMap,new LatLng(37.774772, -122.41993), "Zuni Cafe");
        addLocationToMap(googleMap,new LatLng(37.726821, -122.475523), "McDonald's");
        addLocationToMap(googleMap, new LatLng(37.723984, -122.48512), "The Village Market And Pizza");
        addLocationToMap(googleMap, new LatLng(37.717657, -122.473893), "Hall of Flame Burger");
        addLocationToMap(googleMap, new LatLng(37.723541, -122.45455), "Pho Ha Tien");
        addLocationToMap(googleMap, new LatLng(37.672926, -122.466196), "Round Table Pizza");
    }
}