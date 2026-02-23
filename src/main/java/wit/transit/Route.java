package wit.transit;

/**
 * a route is the
 */
public class Route {
    String id;
    String name;

    Route(String id, String name){
        this.id=id;
        this.name=name;
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
