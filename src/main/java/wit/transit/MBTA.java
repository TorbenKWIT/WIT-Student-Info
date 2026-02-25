package wit.transit;


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
    HashMap<String, Trip> trips;
    HashMap<String, Stop> stops;
    ArrayList<TrainArrival> trainArrivals;
    ArrayList<Stop> stopsOnArrival;
    HashMap<String, Train> trains;
    HashMap<String, Route> routes;
    String apiKey;


    public MBTA(){
        apiKey = "";
        trips = new HashMap<>();
        stops = new HashMap<>();
        trainArrivals = new ArrayList<>();
        trains = new HashMap<>();
        routes = new HashMap<>();
        stopsOnArrival = new ArrayList<>();
    }

    /**
     * @param apiKey set the api key for mbta api
     */
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    /**
     * @return arrayList with all of the Stops
     */
    public HashMap<String, Stop> getAllStops(){
        return stops;
    }

    /**
     * @return hash map of all the routes
     */
    public HashMap<String, Route> getAllRoutes(){
        return routes;
    }


    /**
     * @param route
     * @return Array List of the stops reachable by the Route
     */
    public ArrayList<Stop> getStopsReachable(Route route){
        return route.getStopsOnRoute();
    }

    /**
     * @param stop The Train station you are departing from
     * @param route Route Object of the train route you are taking
     * @return
     */
    public ArrayList<TrainArrival> getTrainArrivals(Stop stop, Route route){
        clearStoredArrivals();
        mapTrainArrivals(httpRequest("https://api-v3.mbta.com/predictions?stop="+ stop.getId() + "&route=" + route.getId(), apiKey));
        return trainArrivals;
    }

    /**
     * pulls data from the MBTA API
     */
    public void pullData(){
        mapRoutes(httpRequest("https://api-v3.mbta.com/routes?type=0", apiKey));
        mapTrips(httpRequest("https://api-v3.mbta.com/trips?filter[route]=Green-B", apiKey));
        mapTrips(httpRequest("https://api-v3.mbta.com/trips?filter[route]=Green-C", apiKey));
        mapTrips(httpRequest("https://api-v3.mbta.com/trips?filter[route]=Green-D", apiKey));
        mapTrips(httpRequest("https://api-v3.mbta.com/trips?filter[route]=Green-E", apiKey));



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

    private void addTrip(String id, Trip trip){
        trips.put(id, trip);
    }

    private void clearStoredArrivals(){
        trainArrivals.clear();
    }

    private void addArrival(TrainArrival arrival){
        trainArrivals.add(arrival);
    }

    private void mapTrainArrivals(JsonNode data){
        for (JsonNode node :data){
            String id = node.get("id").asText();
            String arrivalTime = node.get("attributes").get("arrival_time").asText();
            String departureTime = node.get("attributes").get("departure_time").asText();
            String direction = node.get("attributes").get("direction_id").asText();
            addArrival(new TrainArrival(arrivalTime, departureTime, direction, id));

        }

    }

    private void mapTrips(JsonNode data){
        for (JsonNode node : data){
            String id = node.get("id").asText(); //Extract id
            String name = node.get("attributes").get("headsign").asText(); //extarct name
            String direction = node.get("attributes").get("direction_id").asText();
            String route = node.get("relationships").get("route").get("data").get("id").asText();

            addTrip(id, new Trip(id, name, direction, routes.get(route)));
            System.out.printf("%s %s%n", id, name);

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
                mapStops(httpRequest("https://api-v3.mbta.com/stops?filter[route]="+id, apiKey), id);
                routes.put(id, new Route(id, name));
                System.out.printf("%s %s%n", id, name);

            }

        }

    }

    /**
     * maps the stop data from the json to stop objects and adds them to the hashmap
     * @param dataArray Json Data from the http request
     */
    private void mapStops(JsonNode dataArray, String routeId){
        for (JsonNode node : dataArray) {
            // 2. Extract "id" (direct child of element)
            String id = node.get("id").asText();

            // 3. Extract "name" (inside the "attributes" object)
            String name = node.get("attributes").get("name").asText();

            System.out.printf("%s %s%n", id, name);

            addStop(id, new Stop(id, name));
            routes.get(routeId).addStopToRoute(stops.get(id));

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