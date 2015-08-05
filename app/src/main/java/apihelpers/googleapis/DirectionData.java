package apihelpers.googleapis;

import java.util.List;

/**
 * Created by core6_000 on 8/4/2015.
 */
public class DirectionData {

    public List<Routes> routes;

    public class Routes
    {
        public OverviewPolyline overview_polyline;
    }

    public class OverviewPolyline
    {
        public String points;
    }
}
