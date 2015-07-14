package Callbacks;

import apiHelpers.Untappd.UntappdData;

/**
 * Created by core6_000 on 7/12/2015.
 */
abstract public class UntappdResponseRunnable implements Runnable {

    @Override
    public void run() {

    }

    abstract public void runUntappdResponse(UntappdData pUntappdResponse);
}
