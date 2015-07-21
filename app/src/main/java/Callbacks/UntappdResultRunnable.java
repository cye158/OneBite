package Callbacks;

import ApiManagers.UntappdFeedManager;

/**
 * Created by Allen Space on 7/18/2015.
 */
abstract public class UntappdResultRunnable implements Runnable {
    @Override
    public void run() {

    }

    abstract public void runWithRandomResult(UntappdFeedManager untappdFeedManager);
}
