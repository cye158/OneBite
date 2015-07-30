package com.ironsquishy.biteclub;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ApiManagers.LocationHandler;

/**
 * Created by Renz on 7/2/2015.
 * @author Renz
 * Description: Prompts the user for an address location. The input is then pass to the
 *              location handler to get the lcoation. <CURRENTLY IN THE WORKS>
 **/
public class InputLocationActivity extends DialogFragment implements View.OnClickListener {

    Button Enter;

    private static EditText mAddress;
    private static Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_location, null);
        Enter = (Button) view.findViewById(R.id.enterButton);
        getActivity().getActionBar().hide();
        mContext = view.getContext();

        mAddress = (EditText)view.findViewById(R.id.input_address);

        Enter.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.enterButton){

            final String AddressSTR = mAddress.getText().toString();

            Log.i("apihelpers", "Sending to fetch address." + AddressSTR);
            LocationHandler.getInstance().fetchByAdress(AddressSTR, mContext);

            dismiss();
            startActivity(new Intent(this.getActivity(), OneButtonActivity.class));
            Toast.makeText(getActivity(), "Location has been entered",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
