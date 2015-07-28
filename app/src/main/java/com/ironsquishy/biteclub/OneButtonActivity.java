package com.ironsquishy.biteclub;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

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
    Button oneButton;
    Animation oneButtonPulseAnimation, oneButtonStopPulseAnimation;
    MediaPlayer oneButtonPing;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button);

        oneButtonPing = MediaPlayer.create(this, R.raw.sonar_three_ping);

        oneButton = (Button)findViewById(R.id.one_button);
        oneButtonPulse = (ImageView)findViewById(R.id.one_button_pulse);

        oneButtonPulseAnimation = AnimationUtils.loadAnimation(this,R.anim.one_button_pulse_animation);
        oneButtonPulseAnimation.setAnimationListener(animationPulseListener);
        oneButtonStopPulseAnimation = AnimationUtils.loadAnimation(this,R.anim.one_button_pulse_animation);

        oneButtonPulse.startAnimation(oneButtonPulseAnimation);

        oneButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Start
                        oneButton.setBackground(getResources().getDrawable(R.drawable.one_button_pressed));
                        oneButton.setTextSize(22);
                        oneButtonPulse.setAnimation(oneButtonStopPulseAnimation);
                        oneButtonPulse.clearAnimation();;

                        break;
                    case MotionEvent.ACTION_UP:
                        // End
                        oneButton.setBackground(getResources().getDrawable(R.drawable.one_button));
                        oneButton.setTextSize(25);
                        oneButtonPulse.startAnimation(oneButtonPulseAnimation);
                        oneButtonPing.start();
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

    AnimationListener animationStopPulseListener = new AnimationListener(){

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            oneButtonPulse.clearAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
            oneButtonPulse.clearAnimation();

        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
            oneButtonPulse.clearAnimation();

        }};


}