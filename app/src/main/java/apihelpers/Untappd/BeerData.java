package apihelpers.Untappd;

import java.util.List;

/**
 * Created by core6_000 on 8/2/2015.
 */
public class BeerData {

    public Response response;

    public class Response{
        public Beer beer;
    }

    public class Beer{
        public String bid;
        public String beer_name;
        public String beer_label;
        public String beer_abv;
        public String beer_ibu;
        public String beer_style;

        public String beer_description;

        public int rating_count;

        public Brewery brewery;

        public Checkins checkins;

    }

    public class Brewery
    {
        public int brewery_id;
        public String brewery_name;
        public String country_name;

        public Location loction;
    }

    public class Location{

        public String brewery_city;
        public String brewery_state;
    }

    public class Checkins
    {
        public int count;
        public List<Items> items;
    }

    public class Items
    {
        public int check_id;
        public String checkin_comment;
        public String location;
    }

}