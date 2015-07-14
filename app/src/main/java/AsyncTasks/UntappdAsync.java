package AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import apiHelpers.Untappd.UntappdData;
import Callbacks.UntappdResponseRunnable;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdAsync extends AsyncTask<String, Void, Object> {
    private UntappdResponseRunnable mCallback;

    public UntappdAsync(UntappdResponseRunnable untappdResponseRunnable)
    {
        this.mCallback = untappdResponseRunnable;
    }



    @Override
    protected UntappdData doInBackground(String... params) {

        String untappdJson = null;

        UntappdData untappdData = new Gson().fromJson(untappdJson, UntappdData.class);

        return untappdData;
    }

    @Override
    protected void onPostExecute(Object pUntappdObj) {

        mCallback.runUntappdResponse((UntappdData) pUntappdObj);
    }
}
