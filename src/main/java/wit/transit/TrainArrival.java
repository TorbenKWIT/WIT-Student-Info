package wit.transit;

public class TrainArrival {
    DateTime[] arrival_times;
    String id;

    /**
     * @return List arrival times in the form of a list of DateTime Objects
     */
    public DateTime[] getArrivalTimes() {
        return arrival_times;
    }

    /**
     * @return return the id as a String
     */
    public String getId(){
        return id;
    }
}
