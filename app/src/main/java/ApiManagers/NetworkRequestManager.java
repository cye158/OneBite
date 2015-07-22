package ApiManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.ironsquishy.biteclub.R;

import org.json.JSONObject;

import AuthKeys.UntappdAuthKeys;
import AuthKeys.YelpAuthKeys;
import Callbacks.ImageViewRunnable;
import Callbacks.SelectedBusinessRunnable;
import Callbacks.UntappdResultRunnable;
import apihelpers.SelectedBusiness;
import apihelpers.networkhelper.SingleRequest;
import apihelpers.Untappd.UntappdData;
import apihelpers.YelpApiHandler.SearchForBusinessesResponse;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;
import oauth.signpost.signature.QueryStringSigningStrategy;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class NetworkRequestManager {

    /**Data Fields*/
    private static NetworkRequestManager singleton = null;
    private static UntappdFeedManager mData;
    private static SelectedBusiness mSelectedBusiness;

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
    public void populateUntappdFeed(final UntappdResultRunnable pUntappdRunnable, double pLatitude, double pLongitude, Context pContext)
    {

        final String url = builtURL(pLatitude, pLongitude);


        JsonObjectRequest jsObjectReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i(TAG,"Returned response, populating Untappd data for OneBite.");

                        //Need for populating UntappdData.
                        UntappdData feed = new Gson().fromJson(response.toString(), UntappdData.class);
                        mData = new UntappdFeedManager(feed);

                        pUntappdRunnable.runWithRandomResult(mData);

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

    public void populateYelpData(final SelectedBusinessRunnable selectedBusinessRunnable,String pRadius, final Context pContext)
    {


        final String sendJsonRequestURL =buildYelpAuthenticationUrl(pRadius, LocationHandler.getmLatitude(), LocationHandler.getmLongitude());

        JsonObjectRequest jsObjectReq = new JsonObjectRequest
                (Request.Method.GET, sendJsonRequestURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        long seed = System.nanoTime();

                        Log.i(YELP, "Yelp Responded, populating data for OneBite.");

                        SearchForBusinessesResponse businessesResponse = new Gson().fromJson(response.toString(), SearchForBusinessesResponse.class);


                        selectedBusinessRunnable.runWithRandomResult(businessesResponse);

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
     * @param pLatitude Double latitude values.
     * @param pLongitude Double longitude values.
     * Description: Helper method for populateUntappdFeed, builds the url string.
     * */
    private String builtURL(double pLatitude, double pLongitude)
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

    /**
     * @author Allen Space
     * @param pLatitdude Double for location.
     * @param pLongitude Double for location.
     * Description: Builds and signs the URL request for Yelp.
     *              It uses latitude and longitude points instead
     *              of string address.
     * */
    private String buildYelpAuthenticationUrl(String pRadius , double pLatitdude, double pLongitude)
    {
        //Default values.
        String term = "restaurant";
        String radius = pRadius;

        //Convert doubles to strings.
        String latitude = String.valueOf(pLatitdude);
        String longitude = String.valueOf(pLongitude);

        //URL building.
        String signedQuery = "";
        String requestUrl = String.format(
                "http://api.yelp.com/v2/search?term=%s&offset=%d&radius_filter=%s&ll=%s,%s",
                Uri.encode(term), 0, radius, latitude + "", longitude + "");

        //OAuthentication Protocal with Sign Post Library.
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(YelpAuthKeys._CONSUMER_KEY, YelpAuthKeys._CONSUMER_SECRET);

        consumer.setMessageSigner(new HmacSha1MessageSigner());
        consumer.setTokenWithSecret(YelpAuthKeys._TOKEN, YelpAuthKeys._TOKEN_SECRET);
        consumer.setSendEmptyTokens(true);

        consumer.setSigningStrategy(new QueryStringSigningStrategy());

        //Sign the URL.
        try {
            Log.i(YELP, "Signing url request.");

            signedQuery = consumer.sign(requestUrl);

        } catch (OAuthMessageSignerException e) {

            Log.e(YELP, "OAuthMessageSignerException thrown");
            e.printStackTrace();

        } catch (OAuthExpectationFailedException e) {

            Log.e(YELP, "OAuthExpectationFailedException thrown.");
            e.printStackTrace();

        } catch (OAuthCommunicationException e) {

            Log.e(YELP, "OAuthCommunicationException thrown.");
            e.printStackTrace();
        }

        //Returned Signed and authenticated URL string.
        return signedQuery;
    }

    public static void getYelpSingleImage(final ImageViewRunnable imageViewRunnable, String URL, Context pContext)
    {

        final ImageView imageView = null;

        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

                imageViewRunnable.runWithImageView(imageView);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Could not load image.");
                imageView.setImageResource(R.drawable.pac_man_01);

                imageViewRunnable.runWithImageView(imageView);
            }
        });

        SingleRequest.getInstance(pContext.getApplicationContext()).addToRequestQueue(imageRequest);
    }

}
