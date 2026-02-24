package wit.transit;

public class DateTime {
    String dateTime;

    DateTime(String dateTime){
        this.dateTime = dateTime;
    }
    /**
     * @return converts the dateTime from the API to a more readable format
     */
    private String getTime(){
        return dateTime;
    }
}
