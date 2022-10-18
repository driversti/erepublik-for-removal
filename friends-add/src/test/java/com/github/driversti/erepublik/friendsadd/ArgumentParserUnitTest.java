package com.github.driversti.erepublik.friendsadd;

import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ADD_BLOCKED;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ERPK;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.EXCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.FROM_ID;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.INCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TOKEN;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TO_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ArgumentParserUnitTest {

  private final ArgumentParser parser = new ArgumentParser();

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
