package apihelpers.googleapis;

import java.util.List;

/**
 * Created by Allen Space on 8/4/2015.
 */
public class DirectionData {

    public List<Routes> routes;
    public String status;

    public class Routes
    {
        public OverviewPolyline overview_polyline;
    }

    public class OverviewPolyline
    {
        public String points;
    }
}
