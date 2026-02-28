package wit.transit;

import java.util.ArrayList;

/**
 * Train Arrival Class
 * arrival Time
 * Departure time
 * direction 1=
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
     * @return
     */
    public String getDepartureTimes(){
        return departureTimes;
    }

    /**
     * @return
     */
    public String getDirection(){
        return direction;
    }

    /**
     * @return return the id as a String
     */
    public String getId(){
        return id;
    }
}
