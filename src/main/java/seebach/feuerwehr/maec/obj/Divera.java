package seebach.feuerwehr.maec.obj;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Divera {

        public static void main(String[] args) throws Exception {
            String apiKey = "DEIN_API_KEY_HIER";  // Ersetze mit deinem echten Token

            String jsonBody = """
            {
              "number": "2025-00001",
              "priority": true,
              "title": "Probealarm Lagerhalle",
              "text": "Test.",
              "address": "Teststraße 12, 54321 Beispielstadt",
              "lat": 50.123456,
              "lng": 8.654321
            }
            """;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://app.divera247.com/api/alarm"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Antwort: " + response.body());
        }
    }


