package com.ironsquishy.biteclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Guan on 6/27/2015.
 * Edited by Renz on 7/2/2015.
 *
 * @author Renz
 * Description: Prompts the user for an address location
 **/
public class InputLocationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);

        Button nextButton;
        nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InputLocationActivity.this, TransportationActivity.class));
                Toast.makeText(getApplicationContext(), "Address has been entered~",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
