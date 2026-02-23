package wit.transit;

/**
 * in the MBTA API a single train completing its route one way is a Trip
 */
public class Trip {
    String id;
    String name;
    Line line;

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

    /**
     * @return which line a trip is taking as a Line object
     */
    public Line getLine(){
        return line;
    }
}
