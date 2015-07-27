package com.ironsquishy.biteclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class InfoActivity extends AppCompatActivity {

    private static TextView mExtYelpInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mExtYelpInfo = (TextView) findViewById(R.id.YelpInfo);



    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    /** Called when the user clicks the Information button - Eric */
    public void toInfo(View view) {
        //Untappd List.
        Intent intent = new Intent(this, UntappdList.class);
        startActivity(intent);

    }

}

