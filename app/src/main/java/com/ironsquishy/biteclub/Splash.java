package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

//TODO Delete This if not using for a Splash Screen.
/**
 * Created by CYE on 6/26/15.
 */
public class Splash extends Activity {

    //Set a splashscreen
    private static int splashInterval = 2345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(Splash.this, NaviActivity.class);
                startActivity(i);


                //pause splashscreen
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub
            }
        }, splashInterval);
    }
}
