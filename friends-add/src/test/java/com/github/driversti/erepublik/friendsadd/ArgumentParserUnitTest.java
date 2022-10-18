package com.github.driversti.erepublik.friendsadd;

import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ADD_BLOCKED;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ERPK;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.EXCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.FROM_ID;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.INCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TOKEN;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TO_ID;
import static com.github.driversti.erepublik.friendsadd.Country.CROATIA;
import static com.github.driversti.erepublik.friendsadd.Country.LITHUANIA;
import static com.github.driversti.erepublik.friendsadd.Country.POLAND;
import static com.github.driversti.erepublik.friendsadd.Country.RUSSIA;
import static com.github.driversti.erepublik.friendsadd.Country.SERBIA;
import static com.github.driversti.erepublik.friendsadd.Country.UKRAINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ArgumentParserUnitTest {

  private final ArgumentParser parser = new ArgumentParser();

  @Test
  void shouldCreateRequestConfig() {
    // given
    String erpk = "zxcvbnm";
    String token = "qwerty";
    int fromId = 123;
    int toId = 321;
    Set<Country> includedCountries = Set.of(UKRAINE, POLAND, LITHUANIA);
    Set<Country> excludedCountries = Set.of(CROATIA, RUSSIA, SERBIA);
    boolean addBlocked = true;
    boolean addDead = true;

    String[] args = new String[]{"erpk=" + erpk, "token=" + token,
        "from_id=" + fromId, "to_id=" + toId,
        "include_countries=" + includedCountries.stream().map(Country::id).map(String::valueOf)
            .collect(Collectors.joining(",")),
        "exclude_countries=" + excludedCountries.stream().map(Country::id).map(String::valueOf)
            .collect(Collectors.joining(",")),
        "add_blocked=" + addBlocked, "add_dead=" + addDead};

    // and
    RequestConfig expected = new RequestConfig.Builder(erpk, token)
        .fromId(fromId).toId(toId).includedCountries(includedCountries)
        .excludedCountries(excludedCountries).addBlocked(addBlocked).addDead(addDead).build();

    // when
    RequestConfig actual = parser.create(args);

    // then
    assertEquals(expected, actual);
  }

  @Test
  void shouldSuccessfullyParseGivenEmptyArguments() {
    // given
    String[] givenArguments = new String[]{};

    // when
    Map<ArgumentKey, String> result = parser.parse(givenArguments);

    // then
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldSuccessfullyParseGivenNotEmptyArguments() {
    // given
    String[] givenArguments = new String[]{
        "token=one", "erpk=two", "from_id=123", "to_id=321", "include_countries=UA,PL,US",
        "exclude_countries=HR,RU,HU", "add_blocked=false"
    };

    // and
    Map<ArgumentKey, String> expected = Map.of(TOKEN, "one", ERPK, "two",
        FROM_ID, "123", TO_ID, "321", INCLUDE_COUNTRIES, "UA,PL,US",
        EXCLUDE_COUNTRIES, "HR,RU,HU", ADD_BLOCKED, "false");

    // when
    Map<ArgumentKey, String> result = parser.parse(givenArguments);

    // then
    assertEquals(expected, result);
  }
}
