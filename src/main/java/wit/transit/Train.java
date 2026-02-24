package wit.transit;

public class Train {
    String id;
    String name;
    Trip trip;
    String direction;

    Train(String id, String name, Trip trip, String direction){
        this.id = id;
        this.name=name;
        this.trip=trip;
        this.direction=direction;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Trip getTrip(){
        return trip;
    }

    public String getDirection(){
        return direction;
    }
}
