package com.github.driversti.erepublik.friendsadd;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DefaultApiClientUnitTest {

  private final ApiClient apiClient = new DefaultApiClient();

  @Test
  @Disabled
  void shouldCallRealApi() {
    // given
    RequestConfig requestConfig = new RequestConfig("690201f37a93c93658ac98da9e70ce82",
        "5ea9e8274b3675524160101539d3b377");

    // expect
    assertDoesNotThrow(() -> apiClient.addFriend(requestConfig, 2594));
  }
}
