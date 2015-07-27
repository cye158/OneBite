package ApiManagers;

import android.content.Context;

import java.util.List;

import apihelpers.SQLiteHandler.DBHandler;
import apihelpers.SQLiteHandler.VisitedPlace;


/**
 * Created by Allen Space on 7/20/2015.
 */
public class DatabaseManager {

    /** Data Fields */
    private static DBHandler mDBHandler;


    //----Class Structure-------
    //----Constructor-----------
    //----Getters---------------
    //----Setters---------------
    //----Helpers---------------

    /**
     * @author Allen Space
     * */
    public DatabaseManager(Context pContext)
    {
        mDBHandler = new DBHandler(pContext, null, null, 1);

        //This might change to businessDataManager.

    }

    //TODO put Getter methods

    //TODO put Setter methods.


    /**
     * @author Allen Space, Edited by Guan
     * */
    public void addToDatabase(String pNewPlace, Double latitude, Double longitude)
    {
        //VisitedPlace visitedPlace = new VisitedPlace(pNewPlace, mSelectedBusiness.getRestLatitude(), mSelectedBusiness.getRestLongitdude());
        //mDBHandler.addVisitedPlace(visitedPlace);

        mDBHandler.addVisitedPlace(new VisitedPlace(pNewPlace, latitude, longitude));
    }

    /**
     * @author Guan
     * Description: Delete all content in the database
     */
    public void clearDatabase()
    {
        mDBHandler.deleteAllContentInTable();
    }

    /**
     * @author Guan
     * Description: get all contents in the database
     * @return List of VisitedPlace object, or null if no content
     */
    public List<VisitedPlace> getAllVisitedPlaces()
    {
       return mDBHandler.getContentFromTable();
    }

    /**
     * @author Guan
     * Description: check whether the database is empty
     * @return true if database contain no data, false otherwise
     */
    public boolean isDatabaseEmpty() {
        return mDBHandler.isTableEmpty();
    }

    /**
     * @author Guan
     * Description: remove the a record in the database
     * @param place A String of the name of the location to be remove
     */
    public void removeFromDatabase(String place) {
        //VisitedPlace visitedPlace = new VisitedPlace(place,
                //mSelectedBusiness.getRestLatitude(),
                //mSelectedBusiness.getRestLongitdude());

        //mDBHandler.deleteVisitedPlace(visitedPlace);
    }

    /**
     * @author Guan
     * Description: checking if the inputed location is in the database
     * @param place A String of the name of the location to be check
     * @return  true if is already in the database, false otherwise
     */
    public boolean checkIfInDatabase(String place, Double latitude, Double longitude) {
        //return mDBHandler.isExistInDatabase(place,
                //mSelectedBusiness.getRestLatitude(),
                //mSelectedBusiness.getRestLongitdude());
        return mDBHandler.isExistInDatabase(place, latitude, longitude);
    }
}


