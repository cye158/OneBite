package Callbacks;

import apihelpers.YelpApiHandler.YelpData;

/**
 * Created by Allen Space on 7/12/2015.
 */
abstract public class SelectedBusinessRunnable implements Runnable {

    @Override
    public void run() {

    }

    abstract public void runWithRandomResult(YelpData businessesResponse);
}
