package com.ironsquishy.biteclub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CYE on 7/16/15.
 * @author Renz
 * Description: FilterOption class that dislays a dialog box of food categories. The user can choose
 *             a category he/she would
 **/
public class FilterOption extends DialogFragment {
    SharedPreferences filter_pref;
    Editor editor;

    // Shared Preference file name
    private static final String PREF_NAME = "Pref";

    // Shared Preferences Key
    public static final String KEY_NAME = "name";
    public static final Boolean KEY_VALUE = false;


    /*List of the food category*/
    final ArrayList arrayFilter = new ArrayList();
    final String[] foodCuisine = { "NewAmerican", "Mexican", "Chinese", "Filipino", "Italian",
            "Japanese", "Korean", "Vietnamese", "Thai", "Vegetarian", "Creperies", "Cafe",
            "Desserts", "Seafood" };

    boolean[] itemsChecked = { false, false, false, false, false, false, false, false, false,
            false, false, false, false, false };


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //loads the preference saved
        //for(int i=0; i<itemsChecked.length; i++){
        //    itemsChecked[i] = loadFilter(i);
        //}

        /*Alert dialog declaration*/
        AlertDialog.Builder filterDialog = new AlertDialog.Builder(getActivity());
        filterDialog.setTitle("Food Category:");
        filterDialog.setMultiChoiceItems(foodCuisine, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {

            /*Checked items from the category are saved in an array*/
            @Override
            public void onClick(DialogInterface dialog, int index, boolean isChecked) {

                if (isChecked) {
                    arrayFilter.add(index);
                    //store checked filter
                    saveFilter(index, isChecked);
                } else {
                    //arrayFilter.remove(Integer.valueOf(index));
                    arrayFilter.remove(index);
                    //store unchecked filter
                    saveFilter(index, false);
                }
            }
        });

        filterDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            /*The array is then converted to a string with ',' to separate each item */
            String addToFilter = "";

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //commit and save the preference for next run
                //commitPref();

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

        //dialog cannot be cancelled if out of bounds of the dialog is clicked
        setCancelable(false);

        AlertDialog objDialog = filterDialog.create();
        return objDialog;
    }

    /*Stores pref into a file named filter_pref*/
    public void saveFilter(int  index, Boolean isChecked) {
        filter_pref = getActivity().getSharedPreferences("filter_pref", Context.MODE_PRIVATE);
        editor = filter_pref.edit();
        editor.putBoolean("filters" + index, isChecked);
    }

    /*Saves changes in made to filter_pref*/
    public void commitPref(){
        editor.commit();
    }

    /*Loads the pref by checking the boolean from the passed index marker*/
    public boolean loadFilter(int index) {
        filter_pref = getActivity().getSharedPreferences("filter_pref", Context.MODE_PRIVATE);
        return filter_pref.getBoolean("filters" + index, false);
    }

}
