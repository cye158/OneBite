package com.ironsquishy.biteclub;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;




/**
 * @author Edward Yao
 *         Description: Result activity, shows the restaurant result of the randomized search
 *         allows the user to change the distance of the search via modes of transportatiom
 *         can obtain additional information of the restaurant
 */
public class ResultActivity extends Activity {

    /**
     * Data Fields
     */
    private static TextView expandInfo, collapseInfo;
    private static LinearLayout mLinearLayout;
    private static ValueAnimator mExpandAnimator;
    private static ImageView car_button, bus_button, walk_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        expandInfo = (TextView) findViewById(R.id.showInfo);
        collapseInfo = (TextView) findViewById(R.id.hideInfo);

        mLinearLayout = (LinearLayout) findViewById(R.id.YelpInfoExpand);

        car_button = (ImageView) findViewById(R.id.car_button);
        bus_button = (ImageView) findViewById(R.id.bus_button);
        walk_button = (ImageView) findViewById(R.id.walk_button);


        mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        mLinearLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mLinearLayout.measure(widthSpec, heightSpec);

                        mExpandAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
                        return true;
                    }
                });

        expandInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility()==View.GONE) {
                    expand();
                }else{
                    collapse();
                }
            }
        });
        collapseInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                collapse();
            }
        });
    }

    /**
     * @Author Edward Yao
     * Description: To expand yelp information
     */
    private void expand() {
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);
        expandInfo.setText("Hide Info");
        mExpandAnimator.start();
    }

    /**
     * @Author Edward Yao
     * Description: To collapse yelp information
     */
    private void collapse() {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);
                expandInfo.setText("Show Info");
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    /**
     * @Author Edward Yao
     * Description: To calculate the length need to expand to show all yelp information
     */
    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}

