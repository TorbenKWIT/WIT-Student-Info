package wit.transit;

public class Route {
    String id;
    String name;

    Route(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
