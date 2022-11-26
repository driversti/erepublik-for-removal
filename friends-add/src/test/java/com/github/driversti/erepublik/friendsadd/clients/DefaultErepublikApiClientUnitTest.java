package com.github.driversti.erepublik.friendsadd.clients;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.driversti.erepublik.friendsadd.clients.DefaultErepublikApiClient;
import com.github.driversti.erepublik.friendsadd.clients.ErepublikApiClient;
import com.github.driversti.erepublik.friendsadd.jobconfigs.AddFriendRequestConfig;
import java.net.http.HttpClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DefaultErepublikApiClientUnitTest {

  private final HttpClient client = HttpClient.newHttpClient();
  private final ErepublikApiClient erepublikApiClient = new DefaultErepublikApiClient(client);

  @Test
  @Disabled
  void shouldCallRealApi() {
    // given
    AddFriendRequestConfig config = new AddFriendRequestConfig.Builder("690201f37a93c93658ac98da9e70ce82",
        "5ea9e8274b3675524160101539d3b377", 9733213).build();

    // expect
    assertDoesNotThrow(() -> erepublikApiClient.addFriend(config));
  }
}
