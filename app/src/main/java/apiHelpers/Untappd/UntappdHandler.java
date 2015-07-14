package apiHelpers.Untappd;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdHandler {

    /**Data Fields*/
    private static UntappdHandler singleton = null;

    private static final String API_HOST = "https://api.untappd.com/v4/";
    private static final String ENDPOINT = "thepub/local/method_name?";
    private static final String LATITUDE = "lat=";
    private static final String LONGITUDE = "lng=";
    private static final String CLIENT_ID = "client_id=";
    private static final String CLIENT_SECRET = "client_secret=";

    private static final String ClientID = "CB265DA9B4D1F8397DC8AD8982F826EC5D74BBFE";
    private static final String ClientSecret = "F3E60D4923F984850A0D9E695FE5F8583F5DBFA4";

    private static final String TAG = "UNTAPPD";

    //------Constructors------
    //------Helper Methods----

    /**
     * @author Allen Space
     * Description: Private constructor for singleton design
     * */
    private UntappdHandler()
    {
        //singletpon
    }

    /**
     *@author Allen Space
     * Description: Standard singleton getInstance.
     *
     * */
    public static UntappdHandler getInstance()
    {
        if(singleton == null)
        {
            singleton =  new UntappdHandler();
        }

        return singleton;
    }

    /**
     * @author Allen Space
     * @param pLatitude Double latitude values.
     * @param pLongitude Double longitude values.
     * @param pContext Context java object.
     * Description: Call to populate Untappd Data POJO from the url call.
     * */
    public void populateUntappdFeed(double pLatitude, double pLongitude, Context pContext)
    {

        final String url = builtURL(pLatitude, pLongitude);

        JsonObjectRequest jsObjectReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Delete log outputs when there is a UI.
                        Log.i(TAG,"Returned response!");

                        //Need for populating UntappdData.
                        UntappdData feed = new Gson().fromJson(response.toString(), UntappdData.class);

                        for(int i =0; i < feed.response.checkins.items.size(); i++) {
                            Log.i(TAG,"-------------------------------------------");
                            Log.i(TAG, "comment id: " + feed.response.checkins.items.get(i).checkin_id);
                            Log.i(TAG, "comment: " + feed.response.checkins.items.get(i).checkin_comment);
                            Log.i(TAG, "created: " + feed.response.checkins.items.get(i).created_at);
                            Log.i(TAG, "beer name: " + feed.response.checkins.items.get(i).beer.beer_name);
                            Log.i(TAG, "beer style: " + feed.response.checkins.items.get(i).beer.beer_style);
                            Log.i(TAG, "brewery name: " + feed.response.checkins.items.get(i).brewery.brewery_name);
                            Log.i(TAG, "venue address: " + feed.response.checkins.items.get(i).venue.location.venue_address);
                            Log.i(TAG, "venue city: " + feed.response.checkins.items.get(i).venue.location.venue_city);
                            Log.i(TAG, "venue state: " + feed.response.checkins.items.get(i).venue.location.venue_state);

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Handle error and prompt user.
                        Log.i("UNTAPPD", "Failed Response from untappd.");
                    }
                });


        // Access the RequestQueue through your singleton class.
        SingleRequest.getInstance(pContext).addToRequestQueue(jsObjectReq);
    }

    /**
     * @author Allen Space
     * @param pLatitude Double latitude values.
     * @param pLongitude Double longitude values.
     * Description: Helper method for populateUntappdFeed, builds the url string.
     * */
    private String builtURL(double pLatitude, double pLongitude)
    {
        String url =  API_HOST + ENDPOINT + LATITUDE + String.valueOf(pLatitude) + "&" + LONGITUDE
                + String.valueOf(pLongitude) + "&" + CLIENT_ID
                      + ClientID + "&" + CLIENT_SECRET + ClientSecret;

        return url;
    }

}
