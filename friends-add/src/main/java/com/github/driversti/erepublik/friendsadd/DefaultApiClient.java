package com.github.driversti.erepublik.friendsadd;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultApiClient implements ApiClient {

  private final Logger log = LogManager.getLogger();

  private final HttpClient client = buildHttpClient();
  private final String BASE_URL = "https://www.erepublik.com/en/main";

  @Override
  public void addFriend(RequestConfig requestConfig, int citizenId) {
    String url = BASE_URL + "/citizen-addRemoveFriend";

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofSeconds(20))
        .headers(addFriendHeaders(requestConfig))
        .POST(BodyPublishers.ofString(addFriendPayload(requestConfig.token(), citizenId)))
        .build();
    try {
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      log.info("Response status code: {}", response.statusCode());
      log.info("Response body: {}", response.body());
    } catch (IOException e) {
      log.error("IOException", e);
    } catch (InterruptedException e) {
      log.error("InterruptedException", e);
    }
  }

  @Override
  public void addFriend(RequestConfig requestConfig, Set<Integer> citizenIds) {
    citizenIds.forEach(citizenId -> addFriend(requestConfig, citizenId));
  }

  private HttpClient buildHttpClient() {
    return HttpClient.newBuilder().build();
  }

  private String[] addFriendHeaders(RequestConfig requestConfig) {
    return new String[]{
        "Content-Type", "application/x-www-form-urlencoded",
        "Cookie", "erpk=" + requestConfig.erpk(),
        "User-Agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36"
    };
  }

  private String addFriendPayload(String token, int citizenId) {
    return Map.of("action", "addFriend", "citizenId", String.valueOf(citizenId),
            "url", "%2F%2Fwww.erepublik.com%2Fen%2Fmain%2Fcitizen-addRemoveFriend",
            "_token", token).entrySet().stream()
        .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), UTF_8))
        .collect(Collectors.joining("&"));
  }
}
