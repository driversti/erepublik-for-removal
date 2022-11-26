package com.github.driversti.erepublik.friendsadd.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultEreptoolsApiClient implements EreptoolsApiClient {

  private static final int LATEST_KNOWN_PLAYER_ID = 9675728;
  private static Logger log = LogManager.getLogger();
  private final HttpClient httpClient;
  private final ObjectMapper om;

  public DefaultEreptoolsApiClient(HttpClient httpClient) {
    this.httpClient = httpClient;
    this.om = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @Override
  public int latestRegisteredPlayerId() {
    HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(createUrl()))
        .timeout(Duration.ofSeconds(20))
        .GET().build();
    try {
      HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        log.info("Got the list of the latest registered players.");
        RegisteredPlayers players = om.readValue(response.body(), RegisteredPlayers.class);
        return players.citizen.stream().mapToInt(citizen -> citizen.id).max()
            .orElse(LATEST_KNOWN_PLAYER_ID);
      }
    } catch (IOException | InterruptedException e) {
      log.warn("Cannot get the latest registered player id.", e);
    }
    return LATEST_KNOWN_PLAYER_ID;
  }

  private String createUrl() {
    LocalDate today = LocalDate.now();
    LocalDate yesterday = today.minusDays(1);
    return String.format("https://service.erepublik.tools/api/v1/citizen-registration/0/%s/%s/1",
        yesterday, today);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class RegisteredPlayers {

    Collection<Citizen> citizen;

    void setCitizen(Collection<Citizen> citizen) {
      this.citizen = citizen;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Citizen {

      int id;

      void setId(int id) {
        this.id = id;
      }
    }
  }
}
