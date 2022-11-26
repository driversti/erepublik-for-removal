package com.github.driversti.erepublik.friendsadd.clients;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.driversti.erepublik.friendsadd.exceptions.CSRFAttackDetectedException;
import com.github.driversti.erepublik.friendsadd.exceptions.NotAuthorizedException;
import com.github.driversti.erepublik.friendsadd.jobconfigs.AddFriendRequestConfig;
import com.github.driversti.erepublik.friendsadd.jobconfigs.GetCitizenRequestConfig;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class DefaultErepublikApiClient implements ErepublikApiClient {

  private static final ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule())
      .disable(FAIL_ON_UNKNOWN_PROPERTIES)
      .disable(WRITE_DATES_AS_TIMESTAMPS);
  private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36";
  private final Logger log = LogManager.getLogger();
  private final HttpClient client;
  private final String BASE_URL = "https://www.erepublik.com/en/main";

  public DefaultErepublikApiClient(HttpClient client) {
    this.client = client;
  }

  @Override
  public void addFriend(AddFriendRequestConfig config) {
    String url = BASE_URL + "/citizen-addRemoveFriend";

    HttpRequest request = HttpRequest.newBuilder(URI.create(url))
        .timeout(Duration.ofSeconds(20))
        .headers(addFriendHeaders(config.erpk()))
        .POST(BodyPublishers.ofString(addFriendPayload(config.token(), config.citizenId())))
        .build();
    log.debug("Adding citizen with ID {}", config.citizenId());
    Optional<HttpResponse<String>> responseOpt = makeRequest(request);
    if (responseOpt.isEmpty() || responseOpt.get().statusCode() == 404
        || responseOpt.get().statusCode() == 500 || responseOpt.get().body().startsWith("<")) {
      log.warn("Cannot add a citizen with ID {} to your friend-list. Request failed",
          config.citizenId());
      return;
    }
    HttpResponse<String> httpResponse = responseOpt.get();
    AddFriendResponse addFriendResponse = toAddFriendResponse(httpResponse.body());
    if (addFriendResponse.hasError()) {
      String possibleReason = Stream.of(addFriendResponse.message, addFriendResponse.error)
          .filter(Objects::nonNull).collect(Collectors.joining(","));
      interruptIfNotAuthorized(possibleReason);
      log.warn("Request failed. Possible reason: {}", possibleReason);
    } else {
      log.debug("{} ID: {}", addFriendResponse.message, config.citizenId());
    }
  }

  private void interruptIfNotAuthorized(String errorMessage) {
    if ("not_authorized".equals(errorMessage)) {
      throw new NotAuthorizedException();
    }
  }


  @Nullable
  @Override
  public Player getCitizen(GetCitizenRequestConfig config) {
    String url = BASE_URL + "/citizen-profile-json/" + config.citizenId();
    HttpRequest request = HttpRequest.newBuilder(URI.create(url))
        .timeout(Duration.ofSeconds(20))
        .headers(basicHeaders(config.erpk()))
        .GET().build();

    log.debug("Getting citizen's profile by ID {}", config.citizenId());
    Optional<HttpResponse<String>> responseOpt = makeRequest(request);
    if (responseOpt.isEmpty() || responseOpt.get().statusCode() == 404
        || responseOpt.get().statusCode() == 500) {
      return null;
    }
    return toPlayer(responseOpt.get().body());
  }

  private String[] addFriendHeaders(String erpk) {
    String[] extraHeaders = {"Content-Type", "application/x-www-form-urlencoded"};
    return ArrayUtils.addAll(basicHeaders(erpk), extraHeaders);
  }

  private String addFriendPayload(String token, int citizenId) {
    return Map.of("action", "addFriend", "citizenId", String.valueOf(citizenId),
            "url", "%2F%2Fwww.erepublik.com%2Fen%2Fmain%2Fcitizen-addRemoveFriend",
            "_token", token).entrySet().stream()
        .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), UTF_8))
        .collect(Collectors.joining("&"));
  }

  private AddFriendResponse toAddFriendResponse(String body) {
    // TODO: consider passing as a dependency (it's expensive)
    if ("\"CSRF attack detected.\"".equals(body)) {
      throw new CSRFAttackDetectedException("'erpk' or/and 'token' not valid!");
    }
    return mapToObject(body, AddFriendResponse.class);
  }

  private String[] basicHeaders(String erpk) {
    return new String[]{
        "Cookie", "erpk=" + erpk,
        "User-Agent", USER_AGENT
    };
  }

  private Optional<HttpResponse<String>> makeRequest(HttpRequest request) {
    try {
      return Optional.of(client.send(request, BodyHandlers.ofString()));
    } catch (IOException e) {
      log.error("IOException", e);
    } catch (InterruptedException e) {
      log.error("InterruptedException", e);
    } catch (Exception e) {
      log.error("Internal Server Error. ", e);
    }
    return Optional.empty();
  }

  private Player toPlayer(String body) {
    if (body.contains("500 - Internal Server Error | eRepublik")) {
      log.warn("500 - Internal Server Error | eRepublik");
      return null;
    }
    // TODO: consider passing as a dependency (it's expensive)
    return mapToObject(body, Player.class);
  }

  private static <T> T mapToObject(String json, Class<T> clazz) {
    try {
      return om.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  // cannot be used as a record due to Gson limitations
  private static class AddFriendResponse {

    String message;
    String status;
    String error;

    void setMessage(String message) {
      this.message = message;
    }

    void setStatus(String status) {
      this.status = status;
    }

    void setError(String error) {
      this.error = error;
    }

    boolean hasError() {
      return Arrays.asList("true", "not_authorized").contains(error);
    }
  }

}
