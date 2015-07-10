package YelpAsync;

import android.os.AsyncTask;
import android.util.Log;

import Callbacks.BusinessResponseRunnable;
import apiHelpers.LocationHandler;
import apiHelpers.YelpApiHandler.YelpApiHandler;
import apiHelpers.YelpApiHandler.YelpData.SearchForBusinessesResponse;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.Random;

/*
 * Created by darver on 7/2/15.
 * <p/>
 * Makes the query to the Yelp API as an asynchronous task.
 * After querying, the data returned from the API response will be converted into a java object from JSON.
 */
public class YelpAsync extends AsyncTask<String, Void, Object> {
    private BusinessResponseRunnable mCallback;
    public YelpAsync(BusinessResponseRunnable businessResponseRunnable){
            mCallback = businessResponseRunnable;
    }
    protected SearchForBusinessesResponse doInBackground(String... params) {
        YelpApiHandler yelpApi = YelpApiHandler.getYelpObj();
                long seed = System.nanoTime();



        //Accepts multiple Parameters
        String term = params[0];
        String radius = params[1];
        String location = params[2];


        String yelpData = yelpApi.oneBiteSearch(term, radius,location);

        /**
         * Creates new yelpAPI object and parses through the returned JSON string
         * and converts to java object associated with yelpAPI return (SearchForBusinessesResponse).
         **/

        SearchForBusinessesResponse businessesResponse = new Gson().fromJson(yelpData, SearchForBusinessesResponse.class);

        //Log output response.

        /**
         * Shuffles business list contained within businessResponse
         */
        Collections.shuffle(businessesResponse.businesses, new Random(seed));

        return businessesResponse;
    }


    protected void onPostExecute(Object businessObject) {
        mCallback.runWithBusinessResponse((SearchForBusinessesResponse)businessObject);
    }
}

