package seebach.feuerwehr.maec.obj;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Year;
import java.util.Timer;

public class Divera {

        public void run(String apikey, String ric, String fw_name, String fach_bereich, String meldung, String address, String id) throws Exception {

            String jsonBody = """
            {
              "number": "%id%",
              "priority": true,
              "title": "%title%",
              "text": "BF-TAG Alarm %fw%",
              "address": "%address%",
              "lat": 48.57723753961227,
              "lng": 8.17681648666783,
              "ric": "%ric%"
            }
            """.replace("%id%", Year.now().getValue() + "000" +  id).replace("%title%", fach_bereich + " - " + meldung).replace("%fw%", fw_name).replace("%address%", address).replace("%ric%", ric);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://app.divera247.com/api/alarm"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apikey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Antwort: " + response.body());
        }
    }


