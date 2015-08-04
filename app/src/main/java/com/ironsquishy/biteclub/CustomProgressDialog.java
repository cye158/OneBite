package com.ironsquishy.biteclub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Edward on 7/28/2015.
 */

/**
 * @Author Edward Yao
 * Description: creates custom process dialog
 */
public class CustomProgressDialog extends ProgressDialog {

    private AnimationDrawable animation;
    MediaPlayer oneButtonPingFeedback;
    ImageView orangePacMan;
    TextView nowSearchingText;
    Animation animationToLeft;



    public static ProgressDialog initiateProgressDialog(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.y = 200;
        dialog.getWindow().setAttributes(params);
        return dialog;
    }


    public CustomProgressDialog(Context context) {

        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {

        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progress_dialog);

        orangePacMan = (ImageView) findViewById(R.id.imageanimation);
        nowSearchingText = (TextView) findViewById(R.id.textanimation);

        //sets animation to layout as a background image
        orangePacMan.setBackgroundResource(R.drawable.animation_pac_man_progress_dialog);
        orangePacMan.bringToFront();
        orangePacMan.setTranslationX(-5);

        animation = (AnimationDrawable) orangePacMan.getBackground();

        //creates the slide to left animation
        animationToLeft = new TranslateAnimation(1500, -1100, 0, 0);
        animationToLeft.setDuration(3450);
        animationToLeft.setRepeatMode(Animation.RESTART);
        animationToLeft.setRepeatCount(Animation.INFINITE);

        //sets string text to layout
        String textLeft = "SEARCH RESTAURANTS ψ(｀∇´)ψ";
        nowSearchingText.setText(textLeft);
        nowSearchingText.setTextColor(Color.parseColor("#FFFFFF"));
        nowSearchingText.setTypeface(null, Typeface.BOLD);
        nowSearchingText.setTextSize(18);
        nowSearchingText.setGravity(Gravity.CENTER);

        oneButtonPingFeedback = MediaPlayer.create(getContext(), R.raw.sonar_one_ping_feedback);

    }

    /**
     * starts custom progress dialog
     */
    @Override
    public void show() {
        super.show();

        //starts pac man animation
        animation.start();
        //starts text animation
        nowSearchingText.startAnimation(animationToLeft);

        /**
         * creates runtime timer
         */
        Thread timer=new Thread()
        {
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {

                    //playes sound
                    oneButtonPingFeedback.start();
                    //stops custom progress dialog
                    dismiss();


                }
            }
        };
        timer.start();

    }

    /**
     * stops custom progress dialog
     */
    @Override
    public void dismiss() {
        super.dismiss();
        animation.stop();

        Intent i = new Intent(getContext(), ResultActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(i);
    }


}
