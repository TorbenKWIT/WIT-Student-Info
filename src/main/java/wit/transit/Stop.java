package wit.transit;

public class Stop {
    String id;
    String name;

    Stop(String id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * @return id of stop
     */
    public String getId() {
        return id;
    }

    /**
     * @return name of stop
     */
    public String getName(){
        return name;
    }
}
