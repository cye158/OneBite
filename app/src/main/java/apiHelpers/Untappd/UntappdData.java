package apiHelpers.Untappd;

import java.util.List;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdData {
    //TODO add more object fields from Json.
    public Response response;
}
class Response
{
    public int limit;
    public int offset;
    public String type;
    public int radius;
    public Checkins checkins;
}

class Checkins{

    public List<Item> items;
    public int count;
}

class Item{

    public int checkin_id;
    public double distance;
    public String created_at;
    public double rating_score;
    public String checkin_comment;
    public Beer beer;
    public Brewery brewery;
    public Venue venue;

}

class Beer{
    public String beer_name;
    public String beer_style;
}

class Brewery{
    public String  brewery_name;
}

class Venue{

    public Location location;
    public boolean public_venue;
}

class Location{
    public String venue_address;
    public String venue_city;
    public String venue_state;
    public String venue_country;
    public double lat;
    public double lng;
}