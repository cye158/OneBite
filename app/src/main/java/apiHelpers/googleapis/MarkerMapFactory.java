package apiHelpers.googleapis;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

import apiHelpers.YelpApiHandler.YelpData.Randomizer;

/**
 * Created by Allen Space on 7/14/2015.
 */
public class MarkerMapFactory {

    private static MarkerOptions mMarkers;
    private static GoogleMap mGoogleMap;

    /**
     * @author Allen Space
     *@param pGoogleMap GoogleMap java object.
     *Description: Main class constructor
     * */
    public MarkerMapFactory(GoogleMap pGoogleMap)
    {
        this.mGoogleMap =  pGoogleMap;
    }

    /**
     * @author Allen Space
     * Description: Call to place marker on clients location in google maps.
     * @return Marker object.
     * */
    public Marker createClientMarker()
    {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(LocationHandler.getmLatitude(), LocationHandler.getmLongitude()))
                .title("You")
                .snippet(LocationHandler.streetAddress + ": " + LocationHandler.cityAddress);

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        Marker  clientMarker = mGoogleMap.addMarker(markerOptions);

        return clientMarker;
    }


    /**
     * @author Allen Space
     * @return Marker java object
     * Description: Factory method that will create the resulting marker from yelp response.
     * */
    public Marker createResultMarker()
    {
        Randomizer randomResult = new Randomizer();


        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(randomResult.getRestLatitude(), randomResult.getRestLongitdude()))
                .snippet(randomResult.getmRestAddress())
                .title(randomResult.getmRestName());

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

       Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();

        return marker;
    }

    public List<MarkerOptions> createHistoryMarkers()
    {
        List<MarkerOptions> markers;

        //TODO implement a list of markers from the Database.

        return null;
    }

    public List<MarkerOptions> createUntappdMarkers()
    {
        List<MarkerOptions> markers;

        //TODO implement markers for Untappd feed response.

        return null;
    }

}
