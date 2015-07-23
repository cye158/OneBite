package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CYE on 7/16
 * /15.
 * @author Renz
 * Description: FilterOption class that dislays a dialog box of food categories. The user can choose
 *             a category he/she would
 **/
public class FilterOption extends DialogFragment {

    final int FILTER_LIST_SIZE = 14;
    SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /*List of the food category*/
        final ArrayList arrayFilter = new ArrayList();
        final String[] foodCuisine = { "NewAmerican", "Mexican", "Chinese", "Filipino", "Italian",
                "Japanese", "Korean", "Vietnamese", "Thai", "Vegetarian", "Creperies", "Cafe",
                "Desserts", "Seafood" };

        //load pref

        /*Alert dialog declaration*/
        AlertDialog.Builder filterDialog = new AlertDialog.Builder(getActivity());
        filterDialog.setTitle("Food Category:");
        filterDialog.setMultiChoiceItems(foodCuisine, null, new DialogInterface.OnMultiChoiceClickListener() {

            /*Checked items from the category are saved in an array*/
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    arrayFilter.add(which);
                    saveFilter(,isChecked);
                } else if (arrayFilter.contains(which)) {
                    arrayFilter.remove(Integer.valueOf(which));
                }
            }
        });

        filterDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            //commit pref

            /*The array is then converted to a string with ',' to separate each item */
            String addToFilter = "";
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < arrayFilter.size(); i++) {
                    if(i != 0)
                        addToFilter +=  "," + foodCuisine[(Integer) arrayFilter.get(i)];
                    else
                        addToFilter += foodCuisine[(Integer) arrayFilter.get(i)];
                }
                Toast.makeText(getActivity(), addToFilter, Toast.LENGTH_SHORT).show();

                /*to be used for yelp category_filter*/
                //addToFilter.toLowerCase();
            }
        });

        filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /*Does nothing as filter was cancelled*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Filters have been cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        setCancelable(false);
        AlertDialog objDialog = filterDialog.create();
        return objDialog;
    }

    public void saveFilter(int index, Boolean isChecked) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("check" + index, isChecked);
        editor.commit();
    }

    public void loadFilter(int index) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.getBoolean("check" + index, false);
    }
}
