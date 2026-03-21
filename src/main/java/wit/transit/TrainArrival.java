package wit.transit;

/**
 * Train Arrival Class
 * arrival Time
 * Departure time
 * direction 0=Outbound 1=Inbound
 */
public class TrainArrival {
    String arrivalTimes;
    String departureTimes;
    String direction;
    String id;

    TrainArrival(String arrivalTimes, String departureTimes, String direction, String id){
        this.arrivalTimes=arrivalTimes;
        this.departureTimes=departureTimes;
        this.direction = direction;
        this.id=id;
    }


    /**
     * @return List arrival times in the form of a list of DateTime Objects
     */
    public String getArrivalTime() {
        return arrivalTimes;
    }

    /**
     * @return the Departure time as a String
     */
    public String getDepartureTimes(){
        return departureTimes;
    }

    /**
     * @return
     */
    public String getDirection(){
        if(direction.equals("0")){
            return "Outbound";
        } else if (direction.equals("1")) {
            return "Inbound";
        } else {
            return  "NULL-DIR";
        }
    }

    /**
     * @return return the id as a String
     */
    public String getId(){
        return id;
    }
}
