package apihelpers.Untappd;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Allen Space on 7/12/2015.
 */
public class UntappdData {
    //TODO add more object fields from Json.
    public Response response;

    public class Response
    {
        public int limit;
        public int offset;
        public String type;
        public int radius;
        public Checkins checkins;
    }

    public class Checkins{

        public List<Item> items;
        public int count;
    }

    public class Item{

        public int checkin_id;
        public double distance;
        public String created_at;
        public double rating_score;
        public String checkin_comment;
        public Beer beer;
        public Brewery brewery;
        public Venue venue;
        public Bitmap beerImage;

    }

    public class Beer{
        public String beer_name;
        public String beer_label;
        public String beer_style;
    }

    public class Brewery{
        public String  brewery_name;
    }

    public class Venue{

        public Location location;
        public boolean public_venue;
    }

    public class Location{
        public String venue_address;
        public String venue_city;
        public String venue_state;
        public String venue_country;
        public double lat;
        public double lng;
    }
}














