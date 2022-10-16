package com.github.driversti.erepublik.friendsadd;

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
    Map<String, String> result = parser.parse(givenArguments);

    // then
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldSuccessfullyParseGivenNotEmptyArguments() {
    // given
    String[] givenArguments = new String[]{"first=one", "second=two"};

    // and
    Map<String, String> expected = Map.of("first", "one", "second", "two");

    // when
    Map<String, String> result = parser.parse(givenArguments);

    // then
    assertEquals(expected, result);
  }
}
