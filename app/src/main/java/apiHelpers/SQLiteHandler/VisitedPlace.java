package ApiHelpers.SQLiteHandler;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Guan, Allen on 7/12/2015.
 */
public class VisitedPlace {

    /**
     * Private data members
     * _id and _date are automatically generated when creating object
     */

    private int _id;
    private String _name;
    private double _latitude;
    private double _longitude;
    private String _date;

    /** Constructors **/

    /**
     * Description: default constructor
     */
    public VisitedPlace() {
    }

    /**
     * Description: constructor
     * @param _name A string value of the name of the location
     */
    public VisitedPlace(String _name) {
        this(_name, 0.0, 0.0);
    }

    /**
     * Description: constructor
     * @param _latitude A double value of the latitude of the location
     * @param _longitude A double value of the longitude of the location
     */
    public VisitedPlace(double _latitude, double _longitude) {
        this("", _latitude, _longitude);
    }

    /**
     * Description: constructor
     * @param _name A string value of the name of the location
     * @param _latitude A double value of the latitude of the location
     * @param _longitude A double value of the longitude of the location
     */
    public VisitedPlace(String _name, double _latitude, double _longitude) {
        this._id = 0;
        this._name = _name;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._date = getDateFromSystem();
    }

    /** setters **/

    /**
     * Description: setting the id of the entry in the database
     * @param _id An int value of the id for the database
     */
    public void set_id(int _id) {
        this._id = _id;
    }

    /**
     * Description: setting the name of the location
     * @param _name A string value of the name of the location
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * Description: setting the latitude of the location
     * @param _latitude A double value of the latitude of the location
     */
    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    /**
     * Description: setting the longitude of the location
     * @param _longitude A double value of the longitude of the location
     */
    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    /**
     * Description: setting the date when visited the location
     * @param _date A String value of the visited data
     */
    public void set_date(String _date) { this._date = _date;}

    /** getters **/

    /**
     * Description: getting the id of the location
     * @return _id An int value of the id for the database
     */
    public int get_id() {
        return _id;
    }

    /**
     * Description: getting the name of the location
     * @return _name A string value of the name of the location
     */
    public String get_name() {
        return _name;
    }

    /**
     * Description: getting the latitude of the location
     * @return _latitude A double value of the latitude of the location
     */
    public double get_latitude() {
        return _latitude;
    }

    /**
     * Description: getting the longitude of the location
     * @return _longitude A double value of the longitude of the location
     */
    public double get_longitude() {
        return _longitude;
    }

    /**
     * Description: getting the date when visited the location
     * @return _date A String value of the visited data
     */
    public String get_date() { return _date;}

    /**
     * Description: A helper method for getting the current date automatically
     * @return dateInFormat A String value of the current date
     */
    private String getDateFromSystem() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String dateInFormat = dateFormat.format(cal.getTime());

        Log.i("Date", "The current date is " + dateInFormat);

        return dateInFormat;
    }
}
