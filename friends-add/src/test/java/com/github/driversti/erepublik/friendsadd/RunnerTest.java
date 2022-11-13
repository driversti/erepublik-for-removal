package com.github.driversti.erepublik.friendsadd;

import static com.github.driversti.erepublik.friendsadd.Country.HUNGARY;
import static com.github.driversti.erepublik.friendsadd.Country.RUSSIA;
import static com.github.driversti.erepublik.friendsadd.Country.SERBIA;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RunnerTest {

  private final ApiClient apiClient = Mockito.mock(ApiClient.class);
  private final Runner runner = new Runner(apiClient);

  // WARNING! Long-running tests!
  @Test
  void shouldSuccessfullyCallAddFriend() {
    // given
    JobConfig jobConfig = new JobConfig.Builder("erpk", "token")
        .fromId(178).toId(180).build();

    // and
    Player player = new Player().isBanned(false).isDead(false).isBlocked(false)
        .citizenship(Country.POLAND);
    when(apiClient.getCitizen(isA(GetCitizenRequestConfig.class))).thenReturn(player);

    // when
    runner.run(jobConfig);

    // then
    verify(apiClient, times(3)).addFriend(isA(AddFriendRequestConfig.class));
  }

  @Test
  void shouldNotCallAddFriendWhenPlayerIsBanned() {
    // given
    JobConfig jobConfig = new JobConfig.Builder("erpk", "token")
        .fromId(178).toId(178).build();

    // and
    Player player = new Player().isBanned(true).citizenNickname("John Doe");
    when(apiClient.getCitizen(isA(GetCitizenRequestConfig.class))).thenReturn(player);

    // when
    runner.run(jobConfig);

    // then
    verify(apiClient, times(0)).addFriend(isA(AddFriendRequestConfig.class));
  }

  @Test
  void shouldNotCallAddFriendWhenPlayerIsDead() {
    // given
    JobConfig jobConfig = new JobConfig.Builder("erpk", "token")
        .fromId(178).toId(178).excludedCountries(Set.of(RUSSIA, HUNGARY, SERBIA)).build();

    // and
    Player player = new Player().isBanned(false).isDead(true).isBlocked(false)
        .citizenNickname("John Doe").citizenship(Country.HUNGARY);
    when(apiClient.getCitizen(isA(GetCitizenRequestConfig.class))).thenReturn(player);

    // when
    runner.run(jobConfig);

    // then
    verify(apiClient, times(0)).addFriend(isA(AddFriendRequestConfig.class));
  }

  @Test
  void shouldNotCallAddFriendWhenPlayerIsBlocked() {
    // given
    JobConfig jobConfig = new JobConfig.Builder("erpk", "token")
        .fromId(178).toId(178).excludedCountries(Set.of(RUSSIA, HUNGARY, SERBIA)).build();

    // and
    Player player = new Player().isBanned(false).isBlocked(true)
        .isDead(false).citizenNickname("John Doe").citizenship(Country.HUNGARY);
    when(apiClient.getCitizen(isA(GetCitizenRequestConfig.class))).thenReturn(player);

    // when
    runner.run(jobConfig);

    // then
    verify(apiClient, times(0)).addFriend(isA(AddFriendRequestConfig.class));
  }

  @Test
  void shouldNotCallAddFriendWhenPlayerIsAmongExcludedCountries() {
    // given
    JobConfig jobConfig = new JobConfig.Builder("erpk", "token")
        .fromId(178).toId(178).excludedCountries(Set.of(RUSSIA, HUNGARY, SERBIA)).build();

    // and
    Player player = new Player().isBanned(false).isBlocked(true)
        .isDead(false).citizenNickname("John Doe").citizenship(Country.HUNGARY);
    when(apiClient.getCitizen(isA(GetCitizenRequestConfig.class))).thenReturn(player);

    // when
    runner.run(jobConfig);

    // then
    verify(apiClient, times(0)).addFriend(isA(AddFriendRequestConfig.class));
  }
}
