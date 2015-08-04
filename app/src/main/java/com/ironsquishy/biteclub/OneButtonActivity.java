package com.ironsquishy.biteclub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    ImageView oneButtonPulse;
    ImageButton oneButton;
    Animation oneButtonPulseAnimation, oneButtonStopPulseAnimation;
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

        oneButton = (ImageButton)findViewById(R.id.one_button);
        oneButtonPulse = (ImageView)findViewById(R.id.one_button_pulse);

    }

    @Override
    protected void onStart() {
        super.onStart();

        oneButtonPulseAnimation = AnimationUtils.loadAnimation(this,R.anim.one_button_pulse_animation);
        oneButtonPulseAnimation.setAnimationListener(animationPulseListener);
        oneButtonStopPulseAnimation = AnimationUtils.loadAnimation(this,R.anim.one_button_pulse_animation);

        oneButtonPulse.startAnimation(oneButtonPulseAnimation);

        mContext = this;

        oneButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                        oneButton.setBackgroundResource(R.drawable.one_button_up);
                        oneButtonPing.start();
                        oneButtonPulse.startAnimation(oneButtonPulseAnimation);
                        progressDialog.show();
                        break;
                }

                return false;

            }
        });
        RestaurantManager.getInstance().populateYelpData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);

        UntappdManager untappdManager = new UntappdManager(mContext);

        untappdManager.populateUntappdData(LocationHandler.getmLatitude(), LocationHandler.getmLongitude(), mContext);
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