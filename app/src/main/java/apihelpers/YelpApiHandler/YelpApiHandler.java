package apihelpers.YelpApiHandler;


import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import AuthKeys.YelpAuthKeys;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;
import oauth.signpost.signature.QueryStringSigningStrategy;

/**
 * class that calls the YelpAPI
 */
public class YelpApiHandler {

    private static String YELP = "YelpData";

    private static final String CONSUMER_KEY = YelpAuthKeys._CONSUMER_KEY;
    private static final String CONSUMER_SECRET = YelpAuthKeys._CONSUMER_SECRET;
    private static final String TOKEN = YelpAuthKeys._TOKEN;
    private static final String TOKEN_SECRET = YelpAuthKeys._TOKEN_SECRET;

    public YelpApiHandler()
    {


    }

    /**
     * @author Allen Space
     * @param pLatitdude Double for location.
     * @param pLongitude Double for location.
     * Description: Builds and signs the URL request for Yelp.
     *              It uses latitude and longitude points instead
     *              of string address.
     * */
    public String buildYelpAuthenticationUrl(String pRadius , double pLatitdude, double pLongitude)
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
}