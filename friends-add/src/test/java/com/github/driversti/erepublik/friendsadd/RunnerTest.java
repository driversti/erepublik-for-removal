package com.github.driversti.erepublik.friendsadd;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RunnerTest {

  private final ApiClient apiClient = Mockito.mock(ApiClient.class);
  private final Runner runner = new Runner(apiClient);

  // WARNING! Long-running tests!
  @Test
  void shouldSuccessfullyCallApiClient() {
    // given
    JobConfig jobConfig = new JobConfig.Builder("erpk", "token")
        .fromId(178).toId(180).build();

    // when
    runner.run(jobConfig);

    // then
    System.out.println("Done");
    verify(apiClient, times(3)).addFriend(isA(RequestConfig.class));
  }
}
