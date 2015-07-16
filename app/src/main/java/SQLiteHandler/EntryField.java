package SQLiteHandler;

/**
 * Created by Allen Space on 6/16/2015.
 * @author Allen Space
 * Description: for Entryfields and entry in SQL.
 */
public class EntryField {

    /**Private Data Fields*/
    private int  _id;
    private String _entryname;
    private double _longitude;
    private double _latitude;


    /**
     * @author Allen Space
     * Description: Empty default constructor.
     * */
    public EntryField()
    {
        //Empty Constructor.
    }

    /**
     * @author Allen Space
     * @param entryname Entry to name in database.
     * */
    public EntryField(String entryname)
    {
        this._entryname = entryname;
    }

    public EntryField(String _entryname, double _latitude, double _longitude)
    {
        this._entryname = _entryname;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }
    /**
     * @author Allen Space
     * Description: sets id.
     * @param _id A integer value the id will be set to.
     *
     * */
    public void set_id(int _id)
    {
        this._id = _id;
    }

    /**
     * @author
     * Description: Use to set new entry from original.
     * @param _entryname New entry name to be set too.
     * */
    public void set_entryname(String _entryname)
    {
        this._entryname = _entryname;
    }

    /**
     * @author Allen Space
     * Description: gets _id.
     * @return _id Data member _id.
     * */
    public int get_id()
    {
        return _id;
    }

    /**
     * @author Allen Space
     * Description: Getter for entry name.
     * @return _entryname Data member value return which is a string.
     * */
    public String get_entryname()
    {
        return _entryname;
    }

    public double get_latitude()
    {
        return this._latitude;
    }

    public double get_longitude()
    {
        return _longitude;
    }
}
