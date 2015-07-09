package apiHelpers.YelpApiHandler;


import android.os.StrictMode;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import APIKeys.AuthKeys;

/**
 * class that calls the YelpAPI
 */
public class YelpApiHandler {

    private static final String API_HOST = "api.yelp.com";
    private static final String DEFAULT_TERM = "restaurant";
    private static final String DEFAULT_LOCATION = "San Francisco, CA";
    private static final int SEARCH_LIMIT = 3;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String BUSINESS_PATH = "/v2/business";

    /*
     * Update OAuth credentials below from the Yelp Developers API site:
     * http://www.yelp.com/developers/getting_started/api_access
     */


    private static final String CONSUMER_KEY = AuthKeys._CONSUMER_KEY;
    private static final String CONSUMER_SECRET = AuthKeys._CONSUMER_SECRET;
    private static final String TOKEN = AuthKeys._TOKEN;
    private static final String TOKEN_SECRET = AuthKeys._TOKEN_SECRET;

    OAuthService service;
    Token accessToken;

    /**
     * Setup the Yelp API OAuth credentials.
     *
     * @param consumerKey Consumer key
     * @param consumerSecret Consumer secret
     * @param token Token
     * @param tokenSecret Token secret
     */
    public YelpApiHandler(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.service =
                new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
                        .apiSecret(consumerSecret).build();
        this.accessToken = new Token(token, tokenSecret);
    }

    /**
     * Creates and sends a request to the Search API by term and location.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
     * for more info.
     *
     * @param term <tt>String</tt> of the search term to be queried
     * @param location <tt>String</tt> of the location
     * @return <tt>String</tt> JSON Response
     */
    public String searchForBusinessesByLocation(String term, String location) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        return sendRequestAndGetResponse(request);
    }

    public String oneBiteSearch(String term, String radius, String location){
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("radius", radius);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));

        return sendRequestAndGetResponse(request);

    }


    /**
     * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
     *
     * @param path API endpoint to be queried
     * @return <tt>OAuthRequest</tt>
     */
    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
        return request;
    }

    /**
     * Sends an {@link OAuthRequest} and returns the {@link Response} body.
     *
     * @param request {@link OAuthRequest} corresponding to the API request
     * @return <tt>String</tt> body of API response
     */
    private String sendRequestAndGetResponse(OAuthRequest request) {
        System.out.println("Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

    private static YelpApiHandler _staticYelpObj;
    private static Object _lockObj = new Object();



    /**
     * Created by darver on 7/2/15.
     */
    public static YelpApiHandler getYelpObj() {
        if (_staticYelpObj == null) {
            synchronized (_lockObj) {
                if (_staticYelpObj == null)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    _staticYelpObj = new YelpApiHandler(AuthKeys._CONSUMER_KEY, AuthKeys._CONSUMER_SECRET, AuthKeys._TOKEN, AuthKeys._TOKEN_SECRET);
                }
            }
        }

        return _staticYelpObj;
    }
}