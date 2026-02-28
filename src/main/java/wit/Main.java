package wit;

import wit.transit.MBTA;
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






        //System.out.println(mbta.getAllStops());


    }
}