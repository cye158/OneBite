package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CYE on 7/20/15.
 * @author Renz
 * Desciption: Private method that pops an dialog box to let user check the filter
 *             to be used for next randomzed result.
 *
 **/
public class FilterOption extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Variables and list of the filter option
        final ArrayList arrayFilter = new ArrayList();
        final String[] strFilters = {"American", "Asian", "Chinese", "Filipino", "Italian", "Japanese",
                "Korean", "Vietnamese", "Thai", "Vegetarian"};

        //Process and filters are saved in array.
        AlertDialog.Builder filterDialog = new AlertDialog.Builder(getActivity());
        filterDialog.setTitle("Food Category:");
        filterDialog.setMultiChoiceItems(strFilters, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    arrayFilter.add(which);
                } else if (arrayFilter.contains(which)) {
                    arrayFilter.remove(Integer.valueOf(which));
                }
            }
        });

        filterDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            String addFilter = "";

            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < arrayFilter.size(); i++) {
                    if (i == arrayFilter.size())
                        addFilter += strFilters[(Integer) arrayFilter.get(i)];
                    else
                        addFilter += strFilters[(Integer) arrayFilter.get(i)] + ", ";
                }
                Toast.makeText(getActivity(), addFilter, Toast.LENGTH_SHORT).show();
            }
        });

        filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Filters have been cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog obj = filterDialog.create();
        return obj;
    }
}
