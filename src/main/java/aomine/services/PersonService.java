package aomine.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import aomine.model.Reniec;
import aomine.model.Sunat;
import io.github.cdimascio.dotenv.Dotenv;

public class PersonService {
  private static HttpClient client;
  private static HttpRequest request;
  private static String token;

  public static Reniec getPersonByDNI(String number) throws Exception {
    token = getToken();

    client = HttpClient.newHttpClient();

    request = HttpRequest.newBuilder()
      .uri(URI.create("https://api.apis.net.pe/v2/reniec/dni?numero=" + number))
      .header("accept", "application/json")
      .header("Authorization", "Bearer " + token)
      .GET()
      .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
    Reniec person = new ObjectMapper().readValue(response.body(), Reniec.class);

    return person;
  }

  public static Sunat getInfoByRUC(String ruc) throws Exception {
    token = getToken();

    client = HttpClient.newHttpClient();

    request = HttpRequest.newBuilder()
      .uri(URI.create("https://api.apis.net.pe/v2/sunat/ruc?numero=" + ruc))
      .header("accept", "application/json")
      .header("Authorization", "Bearer " + token)
      .GET()
      .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body());

    Sunat person = new ObjectMapper().readValue(response.body().replace("EsAgenteRetencion", "esAgenteRetencion"), Sunat.class);

    return person;
  }

  public static void full(String ruc) throws Exception {
    token = getToken();

    client = HttpClient.newHttpClient();

    request = HttpRequest.newBuilder()
      .uri(URI.create("https://api.apis.net.pe/v2/sunat/ruc/full?numero=" + ruc))
      .header("accept", "application/json")
      .header("Authorization", "Bearer " + token)
      .GET()
      .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body());
  }

  private static String getToken() {
    Dotenv dotenv = Dotenv.load();

    return dotenv.get("API_TOKEN");
  }
}
