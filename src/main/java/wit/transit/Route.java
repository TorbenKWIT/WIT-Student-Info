package wit.transit;

import java.util.ArrayList;

/**
 * a route is the
 */
public class Route {
    String id;
    String name;
    ArrayList<Stop> stops;

    Route(String id, String name){
        this.id=id;
        this.name=name;
        this.stops= new ArrayList<>();
    }

    protected void addStopToRoute(Stop stop){
        stops.add(stop);
    }

    protected ArrayList<Stop> getStopsOnRoute(){
        return stops;
    }
    /**
     * @return string Id of the Route
     */
    public String getId() {
        return id;
    }

    /**
     * @return String name of the route
     */
    public String getName(){
        return name;
    }
}
