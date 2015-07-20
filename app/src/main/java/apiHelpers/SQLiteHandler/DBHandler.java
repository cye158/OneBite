package apiHelpers.SQLiteHandler;

/**
 * Created by Guan, Allen on 7/12/2015.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

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

            //TODO add time stamp in database
            //String date = Calendar.getInstance().getTime().toString();

            ContentValues newPlace = new ContentValues();
            newPlace.put(COLUME_NAME, place.get_name());
            newPlace.put(COLUME_LATITUDE, place.get_latitude());
            newPlace.put(COLUME_LONGITUDE, place.get_longitude());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_NAME, null, newPlace);
            db.close();

            Log.i(TAG, "'" + place.get_name() + "'" + " has been added to the Database, addVisitedPlace()");
        }

        /**
         * Description: remove the row corresponding to the inputed location name.
         * @param placeName the name of location.
         */
        public void deleteVisitedPlace(String placeName) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE "
                    + COLUME_NAME + "=\"" + placeName + "\";");

            Log.i(TAG, "'" + placeName + "'" + " has been deleted in the Database, deleteVisitedPlace()");
        }

        /**
         * Description: convert all content in the database and return it as a single string.
         * @return dbString a single string containing all the names in the database, separated by "\n".
         */
        public String databaseToString() {
            String dbString = "";
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

            Cursor cursor = db.rawQuery(query, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                if (cursor.getString(cursor.getColumnIndex(COLUME_NAME))!= null) {
                    dbString += cursor.getString(cursor.getColumnIndex(COLUME_NAME));
                    dbString += "\n";
                }
            }

            // some housekeeping
            db.close();
            cursor.close();

            Log.i(TAG, "converted all content in the Database to string , databaseToString()");

            return dbString;
        }

        /**
         * Description: check whether a location is exist in the database.
         * @param place the name of the location.
         * @return true if already exist in the database, false if not in the database.
         */
        public boolean isExistInDatabase(String place) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUME_NAME + " = '" + place + "'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() <= 0) {
                cursor.close();
                Log.i(TAG, "'" + place + "'" + " not found in Database");
                return false;
            } else {
                cursor.close();
                Log.i(TAG, "'" + place + "'" + " founded in Database");
                return true;
            }
        }

        /**
         * Description: delete all rows in the table.
         */
        public void deleteAllContentInTable() {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME, null,null);

            Log.i(TAG, "All content has been deleted, deleteAllContentInTable()");
        }

        /**
         * Description: check whether the table in the database has any content.
         * @return false if table is NOT empty, true if it is.
         */
        public boolean isTableEmpty() {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0) {
                Log.i(TAG, "Table is not empty, isTableEmpty()");
                return false;
            } else {
                Log.i(TAG, "Table is empty, isTableEmpty()");
                return true;
            }
        }

        //TODO return array of VisitedPlaces object.
        VisitedPlace[] getAllContent() {

            return null;
        }

        int getNumberOfRow() {

            return 0;
        }

}//End of Class

