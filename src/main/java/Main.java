import transit.MBTA;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter API Key: ");
        String apiKey = scanner.nextLine();

        MBTA mbta = new MBTA();
        mbta.setApiKey(apiKey);
        mbta.pullData();
        System.out.println(mbta.getAllStops());


    }
}