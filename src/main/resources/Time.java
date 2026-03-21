import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private String currentDayOfWeek;
    private String currentTime;


    Time(String currentDayOfWeek, String currentTime){
        this.currentDayOfWeek = currentDayOfWeek;
        this.currentTime = currentTime;

    }

    /**
     * @param time the time in 12 hour format (ex. 6:00)
     * @param amPm 0 for AM and 1 for PM
     * @return String in 24 hour format (ex. 18:00)
     */
    public static String convertTo24Hour(String time, int amPm){
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        String minutes = parts[1];

        if (amPm == 1) { // PM
            if (hour != 12) {
                hour += 12;
            }
        } else { // AM
            if (hour == 12) {
                hour = 0;
            }
        }

        return String.format("%02d:%s", hour, minutes);
    }


    /**
     * @param openTime opening time in 24hour format
     * @param closedTime  closing time in 24hour format
     * @parm currentTime current time in 24hour format
     * @return if the current time is within the opening and closing times it should return OPEN and if it is not it should return CLOSED
     */
    public String checkIfOpen(String openTime, String closedTime, String currentTime){
        if (currentTime.compareTo(openTime) >= 0 && currentTime.compareTo(closedTime) <= 0) {
            return "OPEN";
        } else {
            return "CLOSED";
        }
    }

    /**
     * @param currentDay the current day in the format of YYYY-MM-DD
     * @return the day of the week (ex. Monday)
     */
    public String getDayOfWeek(String currentDay){
        java.time.LocalDate date = java.time.LocalDate.parse(currentDay);
        return date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);


    }

    public String currentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String currentDay(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getCurrentDayOfWeek() {
        return currentDayOfWeek;
    }

    public String getCurrentTime() {
        return currentTime;
    }
}