package apihelpers.Untappd;

import AuthKeys.UntappdAuthKeys;

/**
 * Created by Allen Space on 7/23/2015.
 */
public class UntappdApiHandler {

    public UntappdApiHandler()
    {
        //Default Constructor
    }

    /**
     * @author Allen Space
     * @param pLatitude Double latitude values.
     * @param pLongitude Double longitude values.
     * Description: Helper method for populateUntappdFeed, builds the url string.
     * */
    public String untappdURL(double pLatitude, double pLongitude)
    {
        String url = UntappdAuthKeys.API_HOST
                + UntappdAuthKeys.ENDPOINT
                + UntappdAuthKeys.LATITUDE
                + String.valueOf(pLatitude) + "&"
                + UntappdAuthKeys.LONGITUDE
                + String.valueOf(pLongitude) + "&"
                + UntappdAuthKeys.CLIENT_ID
                + UntappdAuthKeys.ClientID + "&"
                + UntappdAuthKeys.CLIENT_SECRET
                + UntappdAuthKeys.ClientSecret;

        return url;
    }


}
