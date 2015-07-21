package AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import Callbacks.BusinessResponseRunnable;
import apiHelpers.YelpApiHandler.YelpApiHandler;
import apiHelpers.YelpApiHandler.SearchForBusinessesResponse;

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
    private static ProgressDialog progressDialog;
    public YelpAsync(BusinessResponseRunnable businessResponseRunnable, Context pContext){

        progressDialog = new ProgressDialog(pContext);
            mCallback = businessResponseRunnable;
    }
    /** Remove this when Ed is able to add the progress dialog.
    @Override
    protected void onPreExecute() {

        progressDialog.setTitle("Patience!!!");
        progressDialog.setMessage("Back-end needs to catch up.");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    **/

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

        //progressDialog.dismiss();
        mCallback.runWithBusinessResponse((SearchForBusinessesResponse)businessObject);

    }
}

