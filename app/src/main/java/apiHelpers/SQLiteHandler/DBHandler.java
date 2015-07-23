package apihelpers.SQLiteHandler;

/**
 * Created by Guan, Allen on 7/12/2015.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "history.db";
    public static final String TABLE_NAME = "visitedPlace";
    public static final String COLUME_ID = "_id";
    public static final String COLUME_NAME = "name";
    public static final String COLUME_LATITUDE = "latitude";
    public static final String COLUME_LONGITUDE = "longitude";
    public static final String COLUME_DATE = "date";

    private static final String TAG = "DATABASE";

    /**
     * Description: constructor for DBHandler Objects.
     * @param context context of the class using it, using keyword this.
     * @param name name of the database, use null as default.
     * @param factory CursorFactory, can be leave it as null.
     * @param version version number of the database.
     */
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        Log.i(TAG, "DBHandler Object created");
    }

    /**
     * Description: Overrided method from SQLiteOpenHelp class
     *              to create a new table in the database.
     * @param db SQLiteDatabase Object.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + "("
                + COLUME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUME_NAME + " TEXT, "
                + COLUME_LATITUDE + " REAL, "
                + COLUME_LONGITUDE + " REAL, "
                + COLUME_DATE + " DATE"
                + ");";

        db.execSQL(query);

        Log.i(TAG, "Database has been created, onCreate()");
    }

    /**
     * Description: A method responsible for upgrading the database.
     *              It will delete the old table and create a new one.
     * @param db SQLiteDatabase Object.
     * @param oldVersion int value of the older of the version database.
     * @param newVersion int value of the newer version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);

        Log.i(TAG, "Database has been upgraded, onUpgrade()");
    }

    /**
     * Description: adds a new row to the table.
     * @param place A VisitedPlace Object of new place to add to the table.
     */
    public void addVisitedPlace(VisitedPlace place) {
        if (!isExistInDatabase(place.get_name(), place.get_latitude(), place.get_longitude())) {
            ContentValues newPlace = new ContentValues();
            newPlace.put(COLUME_NAME, place.get_name());
            newPlace.put(COLUME_LATITUDE, place.get_latitude());
            newPlace.put(COLUME_LONGITUDE, place.get_longitude());
            newPlace.put(COLUME_DATE, place.get_date());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_NAME, null, newPlace);
            db.close();

            Log.i(TAG, "'" + place.get_name() + "'" + " has been added to the Database, addVisitedPlace()");
        } else {
            Log.i(TAG, "'" + place.get_name() + "'" + " with the same latitude and longitude already in the database");
        }

    }

    /**
     * Description: remove the row corresponding to the inputed location name.
     * @param place VisitedPlace object of location.
     */
    public void deleteVisitedPlace(VisitedPlace place) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME +
                " WHERE " + COLUME_NAME + " = '" + place.get_name() + "'" +
                " AND " + COLUME_LATITUDE + " = " + place.get_latitude() +
                " AND " + COLUME_LONGITUDE + " = " + place.get_longitude());

        db.close();

        Log.i(TAG, "'" + place.get_name() + "' (" + place.get_latitude() +
                ", " + place.get_longitude() + ") " +
                " has been deleted in the Database");
    }

    /**
     * Description: check whether a location is exist in the database.
     * @param place the name of the location.
     * @return true if already exist in the database, false if not in the database.
     */
    public boolean isExistInDatabase(String place, double latitude, double longitude) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUME_NAME + " = '" + place + "'" +
                " AND " + COLUME_LATITUDE + " = " + latitude +
                " AND " + COLUME_LONGITUDE + " = " + longitude;

        Cursor cursor = db.rawQuery(query, null);
        boolean existent = false;

        if (cursor.getCount() <= 0) {
            Log.i(TAG, "'" + place + "' (" + latitude + ", " + longitude + ") not found in Database");
        } else {
            Log.i(TAG, "'" + place + "' (" + latitude + ", " + longitude + ") founded in Database");
            existent = true;
        }

        cursor.close();
        db.close();
        return existent;
    }

    /**
     * Description: delete all rows in the table.
     */
    public void deleteAllContentInTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null,null);
        db.close();

        Log.i(TAG, "All content has been deleted, deleteAllContentInTable()");
    }

    /**
     * Description: check whether the table in the database has any content.
     * @return false if table is NOT empty, true if it is.
     */
    public boolean isTableEmpty() {
        if (getNumberOfRow() > 0) {
            Log.i(TAG, "Table is not empty, isTableEmpty()");
            return false;
        } else {
            Log.i(TAG, "Table is empty, isTableEmpty()");
            return true;
        }
    }

    /**
     * Description: count the number of row in the database
     * @return rowCount A int value of number of row present in the database
     */
    public int getNumberOfRow() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int rowCount = 0;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            rowCount = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        Log.i(TAG, "Table has " + rowCount + " row(s)");

        return rowCount;
    }

    /**
     * Description: extract all content in the database
     * @return List of VisitedPlace objects
     */
    public List<VisitedPlace> getContentFromTable() {
        if (isTableEmpty()) {
            Log.i(TAG, "Database is empty, no content to get!");
            return null;
        } else {
            List<VisitedPlace> restaurants = new ArrayList<VisitedPlace>();
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    VisitedPlace place = new VisitedPlace();
                    place.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUME_ID))));
                    place.set_name(cursor.getString(cursor.getColumnIndex(COLUME_NAME)));
                    place.set_latitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUME_LATITUDE))));
                    place.set_longitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUME_LONGITUDE))));
                    place.set_date(cursor.getString(cursor.getColumnIndex(COLUME_DATE)));
                    restaurants.add(place);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            Log.i(TAG, "restaurants array length: " + restaurants.size());

            return restaurants;
        }
    }

    /**
     * Description: Display all content in the database in logcat
     */
    public void displayContentInLog() {
        if (isTableEmpty()) {
            Log.i(TAG, "No Content to display");
        } else {
            List<VisitedPlace> restaurants = getContentFromTable();
            Log.i(TAG, "--------------Database Content Begin--------------");

            for (int i = 0; i < restaurants.size(); i++) {
                Log.i(TAG, restaurants.get(i).get_id() + "    "
                        + restaurants.get(i).get_name() + "    "
                        + restaurants.get(i).get_latitude() + "    "
                        + restaurants.get(i).get_longitude() + "    "
                        + restaurants.get(i).get_date());
            }

            Log.i(TAG, "--------------Database Content End----------------");
        }
    }
}
//End of Class
