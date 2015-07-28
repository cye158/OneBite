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

    /*The array is then converted to a string with ',' to separate each item */
    String addToFilter = "";

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
        for(int i=0; i<itemsChecked.length; i++){
            itemsChecked[i] = loadFilter(i);
            if(itemsChecked[i]==true){
                if(!addToFilter.equals(""))
                    addToFilter +=  "," + foodCuisine[i];
                else
                    addToFilter += foodCuisine[i];
            }
        }

        /*Alert dialog declaration*/
        AlertDialog.Builder filterDialog = new AlertDialog.Builder(getActivity());
        filterDialog.setTitle("Food Category:");
        filterDialog.setMultiChoiceItems(foodCuisine, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {

            /*Checked items from the category are saved in an array*/
            @Override
            public void onClick(DialogInterface dialog, int index, boolean isChecked) {

                if (isChecked) {

                    arrayFilter.add(index);

                    /*store checked filter*/
                    saveFilter(index, isChecked);

                } else {

                    arrayFilter.remove(Integer.valueOf(index));

                    /*store unchecked filter*/
                    saveFilter(index, false);
                }
            }
        });

        filterDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < arrayFilter.size(); i++) {
                    if (!addToFilter.equals(""))
                        addToFilter += "," + foodCuisine[(Integer) arrayFilter.get(i)];
                    else
                        addToFilter += foodCuisine[(Integer) arrayFilter.get(i)];
                }
                Toast.makeText(getActivity(), "Filters entered", Toast.LENGTH_SHORT).show();

                /*
                To be used for yelp category_filter
                */
                //addToFilter.toLowerCase();
            }
        });

        filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /*Does nothing as filter was cancelled*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Filters cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(getActivity(), addToFilter, Toast.LENGTH_SHORT).show();

        /*Dialog cannot be cancelled if out of bounds of the dialog is clicked*/
        setCancelable(false);

        AlertDialog objDialog = filterDialog.create();
        return objDialog;
    }

    /*
    Shared preference method to store the pref into a file named filter_pref and is committed
    for every change made to the filter.
    */
    public void saveFilter(int  index, Boolean isChecked) {
        SharedPreferences filter_pref;
        Editor editor;
        filter_pref = getActivity().getSharedPreferences("filter_pref", Context.MODE_PRIVATE);
        editor = filter_pref.edit();
        editor.putBoolean("filters" + index, isChecked);
        editor.commit();
    }

    /*
    Loads the boolean from filter_pref by suing the index and returns it. Returns false as a
    default if file is empty.
    */
    public boolean loadFilter(int index) {
        SharedPreferences filter_pref;
        filter_pref = getActivity().getSharedPreferences("filter_pref", Context.MODE_PRIVATE);
        return filter_pref.getBoolean("filters" + index, false);
    }

}
