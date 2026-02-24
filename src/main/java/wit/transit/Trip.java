package wit.transit;

/**
 * in the MBTA API a single train completing its route one way is a Trip
 */
public class Trip {
    String id;
    String name;
    String direction;
    Route route;

    Trip(String id, String name, String direction, Route route){
        this.id=id;
        this.name=name;
        this.direction=direction;
        this.route=route;
    }
    /**
     * @return id as a String
     */
    public String getId(){
        return id;
    }

    /**
     * @return name of the Trip as a String
     */
    public String getName(){
        return name;
    }

    public String getDirection(){ return direction; }
    /**
     * @return which Route a trip is taking as a route object
     */
    public Route getLine(){
        return route;
    }
}
