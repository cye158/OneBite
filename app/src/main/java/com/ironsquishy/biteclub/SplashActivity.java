package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Eric on 25-Jun-15.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /**
         * Thread class to use sleep, new intent to main activity.
         */
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    Intent i = new Intent("com.ironsquishy.biteclub.MAINACTIVITY");
                    startActivity(i);
                }
            }
        };
        timerThread.start();
    }

    /**
     * This is to prevent the user to press the back button and go back to the splash screen.
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}