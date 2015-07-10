package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import apiHelpers.LocationHandler;


public class SplashActivity extends Activity {

    TextView txt;
    ImageView orangePacMan, orangeWhole, orangeBite, oneBite, bigOrangePacMan;
    Animation animationSlideInRight, animationSlideOutLeft, animationFadeIn, animationSlideInLeft;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        orangePacMan = (ImageView)findViewById(R.id.pacMan);
        bigOrangePacMan = (ImageView)findViewById(R.id.bigPacMan);
        orangeWhole = (ImageView) findViewById(R.id.orangewhole);
        orangeBite = (ImageView) findViewById(R.id.orangebite);
        oneBite = (ImageView) findViewById(R.id.onebite);
        txt = (TextView) findViewById(R.id.textView);

        animationSlideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        animationSlideOutLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        animationSlideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        animationSlideInRight.setDuration(2500);
        animationSlideOutLeft.setDuration(1500);
        animationSlideInLeft.setDuration(1500);

        animationFadeIn.setDuration(0);

        animationSlideInRight.setAnimationListener(animationSlideInRightListener);
        animationSlideOutLeft.setAnimationListener(animationSlideOutLeftListener);
        animationSlideInLeft.setAnimationListener(animationSlideInLeftListener);

        orangeWhole.bringToFront();
        orangeWhole.startAnimation(animationFadeIn);
        orangeWhole.bringToFront();
        orangeBite.startAnimation(animationFadeIn);

        orangeWhole.setVisibility(View.VISIBLE);

        orangePacMan.bringToFront();
        orangePacMan.startAnimation(animationSlideInRight);
        orangePacMan.post(new Runnable() {

            @Override
            public void run() {
                ((AnimationDrawable) orangePacMan.getBackground()).start();

            }

        });

        orangePacMan.setVisibility(View.VISIBLE);
        orangeBite.setVisibility(View.VISIBLE);

    }

    AnimationListener animationSlideInRightListener = new AnimationListener(){
        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            orangeWhole.setVisibility(View.INVISIBLE);
            orangePacMan.startAnimation(animationSlideOutLeft);
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }};
    AnimationListener animationSlideOutLeftListener = new AnimationListener(){

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            orangePacMan.setVisibility(View.INVISIBLE);
            orangeBite.bringToFront();
            oneBite.startAnimation(animationFadeIn);
            oneBite.setVisibility(View.VISIBLE);
            txt.startAnimation(animationFadeIn);
            txt.setVisibility(View.VISIBLE);
            bigOrangePacMan.setVisibility(View.VISIBLE);
            bigOrangePacMan.startAnimation(animationSlideInLeft);
            bigOrangePacMan.post(new Runnable() {

                @Override
                public void run() {
                    ((AnimationDrawable) bigOrangePacMan.getBackground()).start();

                }
            });

        }
        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }};
    AnimationListener animationSlideInLeftListener = new AnimationListener(){
        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            Thread timer=new Thread()
            {
                public void run() {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    finally
                    {
                        //Get started LocationHandler and start connection.
                        LocationHandler.getInstance().setGoogleApiConnection(getBaseContext());
                        //Connect to google services.
                        LocationHandler.startConnect();
                        Intent i = new Intent(getBaseContext(), LocationCheckActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            };
            timer.start();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }};
}