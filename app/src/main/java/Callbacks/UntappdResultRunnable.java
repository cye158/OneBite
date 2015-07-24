package Callbacks;

import ApiManagers.UntappdManager;

/**
 * Created by Allen Space on 7/18/2015.
 */
abstract public class UntappdResultRunnable implements Runnable {
    @Override
    public void run() {

    }

    abstract public void runWithRandomResult(UntappdManager untappdManager);
}
