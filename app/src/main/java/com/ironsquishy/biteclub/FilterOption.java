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

    /*List of the food cuisine style*/
    ArrayList<String> addFilter = new ArrayList();
    final String[] foodCuisine = { "NewAmerican", "Mexican", "Chinese", "Filipino", "Italian",
            "Japanese", "Korean", "Vietnamese", "Thai", "Vegetarian", "Creperies", "Cafe",
            "Desserts", "Seafood" };

    /*List of boolean of items checked by user*/
    boolean[] itemsChecked = { false, false, false, false, false, false, false, false, false,
            false, false, false, false, false };

    /*Dialog*/
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        /*Loads the boolean array*/
        for(int i=0; i<itemsChecked.length; i++){
            itemsChecked[i] = loadFilter(i);
            if(itemsChecked[i]==true){
                addFilter.add(foodCuisine[i]);
            }
        }

        /*Alert dialog declaration*/
        final AlertDialog.Builder filterDialog = new AlertDialog.Builder(getActivity());
        filterDialog.setTitle("Food Category:");
        filterDialog.setMultiChoiceItems(foodCuisine, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
            /*Checked items from the category are saved in an array*/
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) { //adds to addFilter
                    addFilter.add(foodCuisine[which]);
                    itemsChecked[which] = true;
                } else { //removes from addFilter
                    addFilter.remove(foodCuisine[which]);
                    itemsChecked[which] = false;
                }
            }
        });

        /*OK Button*/
        filterDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*Saves the boolean array changes*/
                for (int index = 0; index < itemsChecked.length; index++) {
                    saveFilter(index, itemsChecked[index]);
                }
                Toast.makeText(getActivity(), "Filters entered", Toast.LENGTH_SHORT).show();

                /*Pass the filter list chosen by user to Restaurant Manager*/
                //RestaurantManager.getInstance().setFilters(addFilter);
            }
        });

        /*Cancel Button*/
        filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*Does nothing other than show toast that its cancelled*/
                Toast.makeText(getActivity(), "Filters cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        setCancelable(false);
        AlertDialog objDialog = filterDialog.create();
        return objDialog;
    }

    /*  Shared preference method to store the pref into a file named filter_pref and is committed
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

    /*  Loads the boolean from filter_pref by using the index and returns it. Returns false as a
        default if file is empty.
    */
    public boolean loadFilter(int index) {
        SharedPreferences filter_pref;
        filter_pref = getActivity().getSharedPreferences("filter_pref", Context.MODE_PRIVATE);
        return filter_pref.getBoolean("filters" + index, false);
    }
}
