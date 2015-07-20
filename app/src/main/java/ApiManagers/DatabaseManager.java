package ApiManagers;

import android.content.Context;

import java.util.List;

import apiHelpers.SQLiteHandler.DBHandler;
import apiHelpers.SQLiteHandler.VisitedPlace;
import apiHelpers.SelectedBusiness;

/**
 * Created by Allen Space on 7/20/2015.
 */
public class DatabaseManager {

    /** Data Fields */
    private static DBHandler mDBHandler;
    private static SelectedBusiness mSelectedBusiness;

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
        mSelectedBusiness = new SelectedBusiness();
    }

    //TODO put Getter methods

    //TODO put Setter methods.


    /**
     * @author Allen Space
     * */
    public void addToDatabase(String pNewPlace)
    {
        VisitedPlace visitedPlace = new VisitedPlace(pNewPlace, mSelectedBusiness.getRestLatitude(), mSelectedBusiness.getRestLongitdude());
        mDBHandler.addVisitedPlace(visitedPlace);
    }


    public void clearDatabase()
    {
        //TODO find how to delete the database properly.
    }

    public List<VisitedPlace> getAllVistedPlaces()
    {


        return null;
    }


}
