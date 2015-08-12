package com.ironsquishy.biteclub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import ApiManagers.LocationHandler;
import ApiManagers.RestaurantManager;
import ApiManagers.UntappdManager;

/**
 * Created by Edward Yao on 7/28/2015.
 */

/**
 * @author Edward Yao
 * Description: One Button activity
 *              Starts search for restaurant
 * */
public class OneButtonActivity extends Activity {

    ImageView oneButtonPulse, oneButtonText;
    ImageButton oneButton;
    Animation oneButtonPulseAnimation, oneButtonStopPulseAnimation, oneButtonTextAnimation;
    MediaPlayer oneButtonPing;
    ProgressDialog progressDialog;

    private Context mContext;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button);

        mContext = this;

        progressDialog = CustomProgressDialog.initiateProgressDialog(this);

        oneButtonPing = MediaPlayer.create(this, R.raw.sonar_three_ping);
        oneButtonText = (ImageView) findViewById(R.id.one_button_text);
        oneButton = (ImageButton)findViewById(R.id.one_button);
        oneButtonPulse = (ImageView)findViewById(R.id.one_button_pulse);

        oneButtonTextAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        oneButtonText.startAnimation(oneButtonTextAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();

        oneButtonPulseAnimation = AnimationUtils.loadAnimation(this,R.anim.one_button_pulse_animation);
        oneButtonPulseAnimation.setAnimationListener(animationPulseListener);
        oneButtonStopPulseAnimation = AnimationUtils.loadAnimation(this,R.anim.one_button_pulse_animation);

        oneButtonPulse.startAnimation(oneButtonPulseAnimation);

        mContext = this;

        final Rect r = new Rect();

        oneButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //get the views rect relative to its parent
                v.getHitRect(r);
                // offset the touch coordinates with the values from r
                // to obtain meaningful coordinates
                final float x = event.getX() + r.left;
                final float y = event.getY() + r.top;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Start
                        oneButton.setBackgroundResource(R.drawable.one_button_down);
                        oneButtonPulse.setAnimation(oneButtonPulseAnimation);
                        oneButtonPulse.clearAnimation();
                        if (oneButtonPing.isPlaying()) {
                            oneButtonPing.reset();
                            oneButtonPing.release();
                            oneButtonPing = MediaPlayer.create(getApplicationContext(), R.raw.sonar_two_ping);
                        }
                        break;


                    case MotionEvent.ACTION_UP:
                        // End

                        //if the touch coordinates are not in the View of rerct
                        if (!r.contains((int) x, (int) y)) {
                            oneButton.setBackgroundResource(R.drawable.one_button_up);
                            oneButtonPing.start();
                            oneButtonPulse.startAnimation(oneButtonPulseAnimation);
                            oneButtonPing.reset();
                            oneButtonPing.release();
                            oneButtonPing = MediaPlayer.create(getApplicationContext(), R.raw.sonar_one_ping);
                            oneButtonPing.start();
                            break;
                        }

                        oneButtonPing.reset();
                        oneButtonPing.release();
                        oneButtonPing = MediaPlayer.create(getApplicationContext(), R.raw.sonar_two_ping);
                        oneButton.setBackgroundResource(R.drawable.one_button_up);
                        oneButtonPing.start();

                        //Dont Move this code!!
                        RestaurantManager.getInstance().populateYelpData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);
                        UntappdManager untappdManager = new UntappdManager(mContext);
                        untappdManager.populateUntappdData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);
                        //end..

                        oneButtonPulse.startAnimation(oneButtonPulseAnimation);
                        progressDialog.show();

                        break;
                }

                return false;

            }
        });

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        oneButtonPulse.clearAnimation();
        oneButtonPing.stop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        oneButtonPing.reset();
        oneButtonPing.release();
        oneButtonPing = MediaPlayer.create(getApplicationContext(), R.raw.sonar_two_ping);

    }

    AnimationListener animationPulseListener = new AnimationListener(){

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            oneButtonPulse.startAnimation(oneButtonPulseAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
            oneButtonPulse.clearAnimation();

        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }};

    /**Copied & modified from google marker class to be used in this class - Renz 7/30/15
     *
     * @author: Guan
     * Description: turn a non .bmp image into bitmap and resize to prevent blurriness
     * @param id the resource ID of the image data
     * @return Bitmap object of resized image
     **/
    private Bitmap toBitmap(int id) {
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), id);
        //icon = Bitmap.createScaledBitmap(icon, 240, 240, true);
        return icon;
    }

}