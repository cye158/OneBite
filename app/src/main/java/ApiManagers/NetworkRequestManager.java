package ApiManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import Callbacks.GeneralCallback;

import apihelpers.Untappd.UntappdApiHandler;
import apihelpers.YelpApiHandler.YelpApiHandler;
import apihelpers.YelpApiHandler.YelpData;
import apihelpers.networkhelper.SingleRequest;
import apihelpers.Untappd.UntappdData;


/**
 * Created by Allen Space on 7/12/2015.
 */
public class NetworkRequestManager {

    /**Data Fields*/
    private static NetworkRequestManager singleton = null;
    private static UntappdManager mData;


    //Log cat tags....
    private static final String TAG = "UNTAPPD";
    private static final String YELP = "YelpData";

    //------Constructors------
    //------Helper Methods----

    /**
     * @author Allen Space
     * Description: Private constructor for singleton design
     * */
    private NetworkRequestManager()
    {
        //singleton
    }

    /**
     *@author Allen Space
     * Description: Standard singleton getInstance.
     *
     * */
    public static NetworkRequestManager getInstance()
    {
        if(singleton == null)
        {
            singleton =  new NetworkRequestManager();
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
    public void populateUntappdFeed(final GeneralCallback generalCallback, double pLatitude, double pLongitude, Context pContext)
    {
        final String url = new UntappdApiHandler().untappdURL(pLatitude, pLongitude);


        JsonObjectRequest jsObjectReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i(TAG,"Returned response, populating Untappd data for OneBite.");

                        //Need for populating UntappdData.
                        UntappdData mData = new Gson().fromJson(response.toString(), UntappdData.class);

                        generalCallback.runWithResponse(mData);

                        Log.i(TAG, "Finished populated data exiting out.");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Handle error and prompt user.
                        Log.i("UNTAPPD", "Failed Response from untappd.");

                    }
                });


        //Adds the Untappd request on the stack.
        SingleRequest.getInstance(pContext.getApplicationContext()).addToRequestQueue(jsObjectReq);
    }


    /**
     * @author Allen Space
     *
     * */
    public void populateYelpData(final GeneralCallback generalCallback,String pRadius, final Context pContext)
    {
        final double latitude = LocationHandler.getmLatitude();
        final double longitude = LocationHandler.getmLongitude();

        final String sendJsonRequestURL = new YelpApiHandler().buildYelpAuthenticationUrl(pRadius, latitude, longitude);

        JsonObjectRequest jsObjectReq = new JsonObjectRequest
                (Request.Method.GET, sendJsonRequestURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        long seed = System.nanoTime();

                        Log.i(YELP, "Yelp Responded, populating data for OneBite.");

                        YelpData businessesResponse = new Gson().fromJson(response.toString(), YelpData.class);

                        generalCallback.runWithResponse(businessesResponse);

                        Log.i(YELP, "Finished Populating Data for OneBite.");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Handle error and prompt user.
                        Log.i(YELP, "Failed to retrieve response.");
                    }
                });

        // Adds Yelp request on the stack.
        SingleRequest.getInstance(pContext.getApplicationContext()).addToRequestQueue(jsObjectReq);
    }


    /**
     * @author Allen Space
     * @param generalCallback Callback object.
     * @param URL The url string for picture.
     * @param pContext needs the context it came from.
     *
     * Description: Gets a bitmap object of image from the internet.
     * */
    public static void getYelpSingleImage(final GeneralCallback generalCallback, String URL, Context pContext)
    {
        Log.i(YELP, "Trying to retrive the image url @ " + URL);

        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

                Log.i(YELP, "Responded with image");

                generalCallback.runWithResponse(bitmap);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(YELP, "Could not get image.");

                Bitmap bitmap = null;

                generalCallback.runWithResponse(bitmap);
            }
        });

        SingleRequest.getInstance(pContext.getApplicationContext()).addToRequestQueue(imageRequest);
    }

    public static void getUntappdSingleImage(final GeneralCallback generalCallback, String URL, Context pContext)
    {

        Log.i(TAG, "Trying to retrive the image url @ " + URL);

        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

                Log.i(TAG, "Responded with image");

                generalCallback.runWithResponse(bitmap);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(YELP, "Could not get image.");

                Bitmap bitmap = null;

                generalCallback.runWithResponse(bitmap);
            }
        });

        SingleRequest.getInstance(pContext.getApplicationContext()).addToRequestQueue(imageRequest);
    }

}
