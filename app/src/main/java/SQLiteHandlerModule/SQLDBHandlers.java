package SQLiteHandlerModule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Allen Space
 * @brief Class that handles SQLite data for the purpose of using the database
 *              on the Android devices.
 */
public class SQLDBHandlers extends SQLiteOpenHelper {

    /** Private Data membrs*/
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="databaseDB.db";
    private static final String TABLE_RESTUARENT = "restuarent";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_RESTUARENT = "entryname";
    private static final String COlUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    private static EntryField mEntry;

    private static final String TAG = "DATABASE";

    /**
     * @author Allen Space
     * Description: Constructor for SQLiteHandler the follow are its params
     * @param context Used call, can use this keyword for passsing
     * @param name    Can be set for setting different database name, default database.
     * @param factory CursorFactory for SQLITEDATABASE class
     * @param version For SQL Database version default set to 1.
     *
     */
    public SQLDBHandlers(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    /**
     * @author Allen Space
     * Description: Override onCreate from android and creates new table
     *              with
     * @param db  SQLiteDatabase object passed in. For new query.
     * */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " +
                TABLE_RESTUARENT + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RESTUARENT + " TEXT, " +
                COlUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL " +
                ");";

        db.execSQL(query);

        Log.i(TAG, "Properly executed Oncreat().");
    }

    /**
     * @author Allen Space
     * Description: Use for upgrade table. deletes and calls onCreat() to create new table.
     * @param db SQLiteDabase object from java.
     * @param oldVersion Must need old verison of the database.
     * @param newVersion Needs also new verison of the database.
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTUARENT);
        onCreate(db);

        Log.i(TAG, "Properly executed OnUpgrade().");
    }

    /**
     * @author Allen Space
     * @param entry Object that is passed in for new row.
     * Description: Adds new row into the table.
     * */
    public void addEntry(EntryField entry)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTUARENT, entry.get_entryname());
        values.put(COlUMN_LATITUDE, entry.get_latitude());
        values.put(COLUMN_LONGITUDE, entry.get_longitude());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_RESTUARENT, null, values);
        db.close();

        Log.i(TAG, "Properly added entry to DB.");
    }

    public void addEntryExplicit(String pRestuarent, double pLatitude, double pLongitude)
    {
      EntryField entry = new EntryField(pRestuarent, pLatitude, pLongitude);

      addEntry(entry);
       
    }

    /**
     * @author Allen Space
     * Description: Finds entry string and deletes.
     * @param entryName String too find row to delete.
     * */
    public void deleteEntry(String entryName)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_RESTUARENT + " WHERE " + COLUMN_RESTUARENT + "=\"" + entryName + "\";");

        Log.i(TAG, "Properly deleted Field.");

    }

    /**
    * @author Allen Space
    * @param entry Entry object.
    * @return String.
    */
    public String getEntryByObject(EntryField entry)
    {
      String dbstring = "";
      SQLiteDatabase db = getWritableDatabase();

      String query = "SELECT * FROM " + TABLE_RESTUARENT + "WHERE 1";

      Cursor c = db.rawQuery(query, null);

      c.moveToFirst();

      return dbstring;
    }


    /**
     * @author Allen Space
     * Description: Will take all contents in the database and turn to string.
     * @return dbString Will return the database string.
     *
     * */
    public String databaseToString()
    {
        //New string sets nothing.
        //New sql object.
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        //Go back to beginning and start from there.
        String query = "SELECT * FROM " + TABLE_RESTUARENT + " WHERE 1";

        //Cursor points to a location in results.
        Cursor c = db.rawQuery(query, null);
        //Move to the first row.
        c.moveToFirst();

        //When beginning with first data string, continue to ends of database.
        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex("entryname")) != null)
            {
                dbString += c.getString(c.getColumnIndex("entryname"));
                dbString += "\n";

            }

            c.moveToNext();
        }
        db.close();

        Log.i(TAG,"Returning data, in string type");

        return dbString; //string return with all strings from database.
    }
}
