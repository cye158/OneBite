package Callbacks;

import apihelpers.YelpApiHandler.YelpData;

/**
 * Created by darver on 7/7/15.
 */
abstract public class BusinessResponseRunnable implements Runnable{
    @Override
    public void run() {

    }

    abstract public void runWithBusinessResponse(YelpData businessResponse);

}

