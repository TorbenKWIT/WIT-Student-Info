package transit;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Backend class for calling the MBTA API and formats the data into objects to be used in the UI elements
 */
public class MBTA{
    HashMap<String, Line> lines;
    HashMap<String, Stop> stops;
    HashMap<String, TrainArrival> trainArrivals;
    String apiKey;


    public MBTA(){
        apiKey = "";
        lines = new HashMap<>();
        stops = new HashMap<>();
        trainArrivals = new HashMap<>();
    }

    /**
     * @param apiKey
     */
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    /**
     * @return arrayList with all of the Stops
     */
    public ArrayList<Stop> getAllStops(){
        ArrayList<Stop> stopsArray = new ArrayList<>() ;
        stopsArray.addAll(stops.values());
        return stopsArray;
    }

    /**
     * @param from
     * @param to
     * @return
     */
//    public ArrayList<Stop> getAllStopsReachable(Stop from,Stop to){
//
//        return new Stop[0];
//    }

    /**
     * @param from
     * @param to
     * @return
     */
//    public ArrayList<TrainArrival> getTrainArrivals(Stop from, Stop to){
//
//        return new TrainArrival[0];
//    }

    /**
     * pulls data from the MBTA API
     */
    public void pullData(){
        mapRoutes(httpRequest("https://api-v3.mbta.com/routes?type=0", apiKey));
        System.out.println("Request Made");
    }

    /**
     * adds a stop to the hashmap
     * @param id id of the stop
     * @param stop Stop object
     */
    private void addStop(String id, Stop stop){
        if(!stops.containsKey(id)){
            stops.put(id, stop);
        }
    }

    /**
     * maps the json data to route object
     * and calls mapStops to map the stops on the route
     * @param dataArray Json Data from requesting the routes
     */
    private void mapRoutes(JsonNode dataArray){
        for (JsonNode node : dataArray){
            String id = node.get("id").asText(); //Extract id
            String name = node.get("attributes").get("long_name").asText(); //extarct name
            if(!id.equals("Mattapan")){
                new Route(id, name);
                mapStops(httpRequest("https://api-v3.mbta.com/stops?filter[route]="+id, apiKey));
                System.out.printf("%s %s%n", id, name);
            }

        }

    }

    /**
     * maps the stop data from the json to stop objects and adds them to the hashmap
     * @param dataArray Json Data from the http request
     */
    private void mapStops(JsonNode dataArray){
        for (JsonNode node : dataArray) {
            // 2. Extract "id" (direct child of element)
            String id = node.get("id").asText();

            // 3. Extract "name" (inside the "attributes" object)
            String name = node.get("attributes").get("name").asText();
            System.out.printf("%s %s%n", id, name);

            addStop(id, new Stop(id, name));
        }
    }

    /**
     * @param url url of the endpoint
     * @param apiKey api key
     * @return a JsonNode of the data from response of the http request
     */
    private static JsonNode httpRequest(String url, String apiKey){
        HttpClient client = HttpClient.newHttpClient();


        // 2. Define the URI (Starting with the /routes endpoint)
        URI uri = URI.create(url);

        // 3. Build the Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("x-api-key", apiKey) // Adding the API key to the header
                .GET()
                .build();
        // Next: Sending the request...
        try {
            // 4. Send the request and get the response
            // HttpResponse.BodyHandlers.ofString() converts the JSON bytes into a readable String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            // 5. Print the status code and the body
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            return root.get("data");
    } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}