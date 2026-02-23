package wit.transit;

/**
 * Routes for each line (for example, Green-B)
 */
public class Line {
    String id;
    String name;

    /**
     * @return the line id as a String
     */
    public String getId() {
        return id;
    }

    /**
     * @return name of the line as a String
     */
    public String getName() {
        return name;
    }
}
