package com.ironsquishy.biteclub;

import android.app.DialogFragment;;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Renz on 7/2/2015.
 * @author Renz
 * Description: Prompts the user for an address location
 **/
public class InputLocationActivity extends DialogFragment implements View.OnClickListener {

    Button Enter;// LocSerSwitch;
    String address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_location, null);
        Enter = (Button) view.findViewById(R.id.enterButton);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.enterButton){
            // TODO for address input by the user.
            dismiss();
            Toast.makeText(getActivity(), "Your at: "+ address,
                    Toast.LENGTH_SHORT).show();
        }else{
            dismiss();
            Toast.makeText(getActivity(), "Please enter a starting location",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
