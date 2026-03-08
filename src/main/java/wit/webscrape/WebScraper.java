package wit.webscrape;

//TODO
public class WebScraper {
import wit.transit.Train;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class WebScraper {

    private static final String API_URL = "https://api-v3.mbta.com/vehicles";

    public static ArrayList<Train> getLiveTrains() {

        ArrayList<Train> trains = new ArrayList<>();

        try {

            URL url = new URL(API_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            reader.close();

            JSONObject data = new JSONObject(json.toString());

            JSONArray vehicles = data.getJSONArray("data");

            for (int i = 0; i < vehicles.length(); i++) {

                JSONObject vehicle = vehicles.getJSONObject(i);
                JSONObject attr = vehicle.getJSONObject("attributes");

                if (attr.isNull("latitude") || attr.isNull("longitude"))
                    continue;

                String id = vehicle.getString("id");

                double lat = attr.getDouble("latitude");
                double lon = attr.getDouble("longitude");

                String route = vehicle
                        .getJSONObject("relationships")
                        .getJSONObject("route")
                        .getJSONObject("data")
                        .getString("id");

                trains.add(new Train(id, route, lat, lon));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trains;
    }
}
}
