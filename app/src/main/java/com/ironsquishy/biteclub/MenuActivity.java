package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.toolbox.NetworkImageView;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import ApiManagers.DatabaseManager;
import ApiManagers.NetworkRequestManager;
import Callbacks.ImageViewRunnable;
import apihelpers.SelectedBusiness;


/**
 * @author Allen Space
 * Description: Menu  activity with google maps fragment.
 * */
public class MenuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener  {

    /**Data Fields*/
    private static SelectedBusiness mSelectedBusiness;
    private static TextView mResultText;
    private static String mRandomStringName;
    private static SwipeRefreshLayout swipeRefreshLayout;

    private static TextView addToData;
    private static ImageView mYelpImage;

    private static DatabaseManager mDatabaseManager;

    private static Context mContext;

    private AlertDialog.Builder filterDialog;
    private String inputFilter = "\n Filtered: ";

    private ShowcaseView showcaseView;
    private int count = 0;
    private Target t1,t2,t3,t4,t5,t6;


    /**
     * @Author Allen Space
     * Description: To create the menu activity.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mContext = this;

        mResultText = (TextView) findViewById(R.id.resultText);

        mYelpImage = (ImageView) findViewById(R.id.YelpImage);

        addToData = (TextView) findViewById(R.id.checkToAddFav);

        mDatabaseManager = new DatabaseManager(this);

        swipeRefresh();

        t1 = new ViewTarget(R.id.YelpImage, this);
        t2 = new ViewTarget(R.id.resultText, this);
        t3 = new ViewTarget(R.id.fab, this);
        t4 = new ViewTarget(R.id.checkToAddFav, this);
        t5 = new ViewTarget(R.id.filter, this);
        t6 = new ViewTarget(R.id.Untappd, this);

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(t1)
                .setOnClickListener(this)
                .setContentTitle(R.string.Tutorial)
                .setContentText(R.string.Result_Page)
                .setStyle(R.style.TutorialShowcaseStyle)
                .build();
        showcaseView.setButtonText("NEXT");

    }

    /** Check for favorite.**/
    public void checkFavAdd(View view)
    {
        //Add to result in text view to data.
        mDatabaseManager.addToDatabase(mRandomStringName);

        //TODO There should be a check to see if it has already been added to favorites, then the toast message should say "already added"
        Toast.makeText(getApplicationContext(), "Added to favorites.",
                Toast.LENGTH_SHORT).show();

    }

    /** Called when the user clicks the Go button - Eric */
    public void toNavi(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }



    /** Called when the user clicks the Search button - Eric */
    /** Revised by Renz */
    public void toSearch(View view) {
        FilterOption dialog = new FilterOption();
        dialog.show(getFragmentManager(), "Filter Dialog Box");
    }

    /**
     * @Author Allen Space
     * Description: On start, creates process dialoge
     * */
    @Override
    protected void onStart() {
        super.onStart();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSelectedBusiness = new SelectedBusiness();

                mRandomStringName = mSelectedBusiness.getmRestName();

                mResultText.setText(mRandomStringName);

                ImageViewRunnable imageViewRunnable = new ImageViewRunnable() {
                    @Override
                    public void runWithImageView(Bitmap bitmap) {
                        if (bitmap == null) {

                            mYelpImage.setImageResource(R.drawable.placeholder_yelp);

                        } else {
                            mYelpImage.setImageBitmap(bitmap);
                        }
                    }
                };

                NetworkRequestManager.getInstance().getYelpSingleImage(imageViewRunnable, mSelectedBusiness.getRestImageURL(), mContext);
            }
        }, 1000);

    }

    /**
     * @author Allen Space
     * Description: ReShuffles the yelp data.
     *              And displays on screen.
     **/
    private void randomizeYelpResponse()
    {
        mSelectedBusiness = new SelectedBusiness();

        mSelectedBusiness.reShuffleBusinessList();

        mRandomStringName = mSelectedBusiness.getmRestName();

        mResultText.setText(mRandomStringName);

        ImageViewRunnable imageViewRunnable = new ImageViewRunnable() {
            @Override
            public void runWithImageView(Bitmap bitmap) {
                if(bitmap == null){

                    mYelpImage.setImageResource(R.drawable.placeholder_yelp);

                }else {
                    mYelpImage.setImageBitmap(bitmap);
                }
            }
        };

        NetworkRequestManager.getInstance().getYelpSingleImage(imageViewRunnable, mSelectedBusiness.getRestImageURL(), mContext);
    }


    /**
     * @Author Eric Chen
     * Description: To create pull down to refresh.
     * */
    private void swipeRefresh()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_menu);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public void onRefresh() {
        //Created to simulate loading.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do stuff here.
                randomizeYelpResponse();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }


    /**
     * @Author Edward Yao
     * Description: Creates click to goto next tutorial target
     * */
    @Override
    public void onClick(View v) {
        switch (count){
            case 0:
                showcaseView.setShowcase(t2, true);
                showcaseView.setContentTitle(getString(R.string.Tutorial));
                showcaseView.setContentText(getString(R.string.Restaurant_Result));
                break;
            case 1:
                showcaseView.setShowcase(t3, true);
                showcaseView.setContentTitle(getString(R.string.Tutorial));
                showcaseView.setContentText(getString(R.string.Navigation));
                break;
            case 2:
                showcaseView.setShowcase(t4, true);
                showcaseView.setContentTitle(getString(R.string.Tutorial));
                showcaseView.setContentText(getString(R.string.Add_To_Favorites));
                break;
            case 3:
                showcaseView.setShowcase(t5, true);
                showcaseView.setContentTitle(getString(R.string.Tutorial));
                showcaseView.setContentText(getString(R.string.Filter));
                break;
            case 4:
                showcaseView.setShowcase(t6, true);
                showcaseView.setContentTitle(getString(R.string.Tutorial));
                showcaseView.setContentText(getString(R.string.Untappd));
                showcaseView.setButtonText(getString(R.string.Finish));
                break;
            case 5:
                showcaseView.hide();
                Toast.makeText(getApplicationContext(), "Now You Know EVERYTHING.",
                        Toast.LENGTH_SHORT).show();

                break;
        }
        count++;

    }

}
