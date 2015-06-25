package com.example.eric.viewtabswipe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Eric on 6/24/2015.
 */
public class DeviceFragment extends Fragment {

    public static final String image_id_key = "imagekey";
    public static final String description_key = "descriptionkey";
    public static final String button_key = "buttonkey";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_device, container, false);

        Bundle bundle = getArguments();

        if (bundle != null)
        {
            int image_id = bundle.getInt(image_id_key);
            String description = bundle.getString(description_key);

            setValues(view, image_id, description);
        }
        view.setBackgroundColor(Color.YELLOW);

        return view;
    }

    private void setValues(View view, int image_id, String description){
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_transportation);
        imageView.setImageResource(image_id);

        TextView textView = (TextView) view.findViewById(R.id.text_view_transportation_description);
        textView.setText(description);
    }
}
