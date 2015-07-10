package apiHelpers.YelpApiHandler.YelpData;
import java.util.List;





/**
 * Created by darver on 7/2/15.
 */
public class SearchForBusinessesResponse {

        public List<Business> businesses;
        public Region region;
        public int total;
    }

    class Business {
        public String display_phone;
        public String id;
        public String image_url;
        public String mobile_url;
        public String name;
        public String phone;
        public String rating_img_url;
        public String rating_img_url_large;
        public String rating_img_url_small;
        public String snippet_image_url;
        public String snippet_text;
        public String url;

        public Boolean is_claimed;
        public Boolean is_closed;

        public float rating;
        public int review_count;

        public List<Deal> deals;
        public List<List<String>> caetgories;

        public Location location;
    }

    class Deal {
        public String additional_restrictions;
        public String currency_code;
        public String id;
        public String image_url;
        public String title;
        public String url;
        public String what_you_get;

        public Boolean is_popular;

        public int time_start;

        public List<Option> options;
    }

    class Option
    {
        public String formatted_original_price;
        public String formatted_price;
        public String purchase_url;
        public String title;

        public Boolean is_quantity_limited;

        public int original_price;
        public int price;

    }

    class Location
    {
        public List<String> address;
        public String city;
        public Coordinate coordinate;
        public String country_code;
        public String cross_streets;
        public List<String> display_address;
        public float geo_accuracy;
        public List<String> neighborhoods;

    }

    class Coordinate
    {
        double latitude;
        double longitude;
    }

    class Region {
        public Center center;
        public Span span;
    }

    class Center
    {
        public double latitude;
        public double longitude;
    }

    class Span
    {
        public double latitude_delta;
        public double longitude_delta;
    }
