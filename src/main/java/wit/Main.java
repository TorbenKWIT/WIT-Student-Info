package wit;

import wit.transit.MBTA;
import wit.transit.Stop;
import wit.transit.Train;
import wit.transit.TrainArrival;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String API_KEY = "";  // INSERT MBTA API KEY HERE

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter API Key: "); // temp way to add the api key to prevent accidentally leaking apikeys
        String apiKey = scanner.nextLine();

        MBTA mbta = new MBTA();
        mbta.setApiKey(apiKey);
        mbta.pullData();
        HashMap<String, Stop> stops = mbta.getAllStops();
        ArrayList<TrainArrival> arrivals = mbta.getTrainArrivals(stops.get("place-tapst"), mbta.getAllRoutes().get("Green-C"));;
        System.out.println(arrivals.get(1).getArrivalTime());


        System.out.println(mbta.getAllStops());


    }
}