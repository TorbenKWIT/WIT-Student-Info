import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {

        // 1. Create the Client
        HttpClient client = HttpClient.newHttpClient();


        // 2. Define the URI (Starting with the /routes endpoint)
        String apiKey = "";
        URI uri = URI.create("https://api-v3.mbta.com/routes");

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

            // 5. Print the status code and the body
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}