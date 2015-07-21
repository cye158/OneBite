package ApiManagers;

import android.content.Context;

import java.util.List;

import ApiHelpers.SQLiteHandler.DBHandler;
import ApiHelpers.SQLiteHandler.VisitedPlace;
import ApiHelpers.SelectedBusiness;

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

    /**
     * @author Guan
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
    public List<VisitedPlace> getAllVistedPlaces()
    {
       return mDBHandler.getContentFromTable();
    }
}
