package com.github.driversti.erepublik.friendsadd.jobconfigs;

import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.ADD_BLOCKED;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.ADD_DEAD;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.ERPK;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.EXCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.FROM_ID;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.INCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.TOKEN;
import static com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey.TO_ID;
import static com.github.driversti.erepublik.friendsadd.jobconfigs.Country.ESTONIA;
import static com.github.driversti.erepublik.friendsadd.jobconfigs.Country.IRAN;
import static com.github.driversti.erepublik.friendsadd.jobconfigs.Country.LITHUANIA;
import static com.github.driversti.erepublik.friendsadd.jobconfigs.Country.RUSSIA;
import static com.github.driversti.erepublik.friendsadd.jobconfigs.Country.SERBIA;
import static com.github.driversti.erepublik.friendsadd.jobconfigs.Country.UKRAINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey;
import com.github.driversti.erepublik.friendsadd.clients.EreptoolsApiClient;
import com.github.driversti.erepublik.friendsadd.jobconfigs.JobConfig;
import com.github.driversti.erepublik.friendsadd.jobconfigs.JobConfigFactory;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.Test;

class JobConfigFactoryUnitTest {

  private final String erpk = "erpk";
  private final String token = "token";
  private final String fromId = "222";
  private final String toId = "333";
  private final String includeCountries = "40,72,70";
  private final String excludeCountries = "41,65,56";
  private final String addBlocked = "true";
  private final String addDead = "true";

  private EreptoolsApiClient apiClient = mock(EreptoolsApiClient.class);
  private final JobConfigFactory factory = new JobConfigFactory(apiClient);

  @Test
  void shouldCreateMinimallyValidJobConfig() {
    // given
    Map<ArgumentKey, String> args = Map.of(ERPK, erpk, TOKEN, token);

    // and
    when(apiClient.latestRegisteredPlayerId()).thenReturn(200);

    // when
    JobConfig actual = factory.create(args);

    // then
    assertEquals(erpk, actual.erpk());
    assertEquals(token, actual.token());
  }

  @Test
  void shouldCreateValidJobConfigWithAllCustomFields() {
    // given
    Map<ArgumentKey, String> args = Map.of(ERPK, erpk, TOKEN, token, FROM_ID,
        fromId, TO_ID, toId,
        INCLUDE_COUNTRIES, includeCountries, EXCLUDE_COUNTRIES, excludeCountries,
        ADD_BLOCKED, addBlocked, ADD_DEAD, addBlocked);

    // when
    JobConfig actual = factory.create(args);

    // then
    assertEquals(erpk, actual.erpk());
    assertEquals(token, actual.token());
    assertEquals(Integer.parseInt(fromId), actual.fromId());
    assertEquals(Integer.parseInt(toId), actual.toId());
    assertEquals(Set.of(UKRAINE, LITHUANIA, ESTONIA), actual.includedCountries());
    assertEquals(Set.of(RUSSIA, SERBIA, IRAN), actual.excludedCountries());
    assertEquals(BooleanUtils.toBoolean(addBlocked), actual.addBlocked());
    assertEquals(BooleanUtils.toBoolean(addDead), actual.addDead());
  }
}
