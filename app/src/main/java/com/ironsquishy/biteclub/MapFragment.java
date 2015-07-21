package com.ironsquishy.biteclub;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ApiManagers.LocationHandler;
import apihelpers.googleapis.MarkerMapFactory;

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

    private static Context mContext;

    private static MarkerMapFactory markerMapFactory;

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

        mContext = mapView.getContext();

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
     * Description: Moves the postion of the camera view to clients marker.
     * */
    private void moveCameraToClient(GoogleMap pGoogleMap)
    {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(LocationHandler.getmLatitude(), LocationHandler.getmLongitude()))
                .zoom(13)
                .build();

        pGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    /**
     * @author Allen Space
     * Description: On Map ready callback, override method is called to populate or move camera.
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.i(TAG, "Building Map.");

        populateGoogleMaps(googleMap, mContext);

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
     * @author Allen Space
     * */
    private void populateGoogleMaps(GoogleMap pGoogleMap, Context pContext)
    {
        markerMapFactory = new MarkerMapFactory(pGoogleMap);

        moveCameraToClient(pGoogleMap);

        Marker  restMark = markerMapFactory.createResultMarker();

        //Client Marker.
        Marker marker = markerMapFactory.createClientMarker();

        //Untappd marker generated.
        markerMapFactory.createUntappdMarkers(pContext);

        //addFakeMarkers(pGoogleMap);
    }

}