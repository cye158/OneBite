package com.ironsquishy.biteclub;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Renz on 7/2/2015.
 * @author Renz
 * Description: Prompts the user for an address location. The input is then pass to the
 *              location handler to get the lcoation. *Current is in the works*
 **/
public class InputLocationActivity extends DialogFragment implements View.OnClickListener {

    Button Enter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_location, null);
        Enter = (Button) view.findViewById(R.id.enterButton);
        Enter.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.enterButton){
            dismiss();
            startActivity(new Intent(this.getActivity(), TransportationActivity.class));
            Toast.makeText(getActivity(), "Location has been entered",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
