package apiHelpers.Untappd;

import java.util.List;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class FetchUntappdData {
    //TODO build java class to for fetching untappd data for project scope.

    private static UntappdData mData;

    private static String createdAt;
    private static String comment;
    private static String drink;
    private static String breweryName;
    private static String venueAddress;

    /**
     * @author Allen Space
     * */
    public FetchUntappdData()
    {
        //default.
    }

    public FetchUntappdData(UntappdData data)
    {
        mData = data;

    }
    /**
     * @author Allen Space
     *
     * */
    public String getShortDescription(int index)
    {
        String created_at = mData.response.checkins.items.get(index).created_at;

        String comment = mData.response.checkins.items.get(index).checkin_comment;

        String drink = mData.response.checkins.items.get(index).beer.beer_name;

        String breweryName = mData.response.checkins.items.get(index).brewery.brewery_name;

        String venueAddress = mData.response.checkins.items.get(index).venue.location.venue_address;

        final String str = "Sorry, I love to drink " + drink + "\n";

        return str;
    }

    public int getItemSize()
    {
        return mData.response.checkins.items.size();
    }
}
