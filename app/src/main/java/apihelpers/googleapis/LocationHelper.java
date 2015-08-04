package apihelpers.googleapis;

import android.location.Location;

/**
 * Created by Allen Space on 7/31/2015.
 */
public class LocationHelper {

    /**Data Fields*/
    private Location mOrigin;
    private Location mDest;

    private double mOriginLatidude;
    private double mOriginLongitude;


    private double mDestLatitude;
    private double mDestLongitude;

    public LocationHelper()
    {
        //defualt constructor.
    }

    public LocationHelper(double pOriginLat, double pOriginLng, double pDestLat, double pDestLng)
    {
        this.mOriginLatidude = pOriginLat;
        this.mOriginLongitude = pOriginLng;

        this.mDestLatitude = pDestLat;
        this.mDestLongitude = pDestLng;

    }

    public LocationHelper(Location pOrigin, Location pDest)
    {
        this.mOrigin = pOrigin;
        this.mDest = pDest;
    }

    //Overloaded
    public double getDistanceFromOrigin()
    {
        //Set origin location.
        Location origin = new Location("origin");
        origin.setLatitude(mOriginLatidude);
        origin.setLongitude(mOriginLongitude);

        //Set given Restuarant location.
        Location location = new Location("Dest");
        location.setLatitude(mDestLatitude);
        location.setLongitude(mDestLongitude);

        return origin.distanceTo(location);
    }

    //Overloaded
    public double getDistanceFromOrigin(double pOriginLat, double pOriginLng, double pDestLat,
                                        double pDestLng)
    {
        //Set origin location.
        Location origin = new Location("origin");
        origin.setLatitude(pOriginLat);
        origin.setLongitude(pOriginLng);

        //Set given Restuarant location.
        Location location = new Location("Dest");
        location.setLatitude(pDestLat);
        location.setLongitude(pDestLng);

        return origin.distanceTo(location);
    }

    //Overloaded
    public double getDistanceFromOrigin(Location pOrigin, Location pDest)
    {
        return pOrigin.distanceTo(pDest);
    }

}
