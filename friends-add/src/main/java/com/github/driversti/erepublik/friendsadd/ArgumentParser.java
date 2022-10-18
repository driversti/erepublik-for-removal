package com.github.driversti.erepublik.friendsadd;

import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ADD_BLOCKED;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ADD_DEAD;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ERPK;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.EXCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.FROM_ID;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.INCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TOKEN;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TO_ID;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class ArgumentParser {

  Map<ArgumentKey, String> parse(String[] args) {
    return Arrays.stream(args)
        .flatMap(splitBySpaceFunction())
        .map(createPairFunction())
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  RequestConfig create(String[] args) {
    Map<ArgumentKey, String> map = parse(args);
    Set<Country> includedCountries = Arrays.stream(map.get(INCLUDE_COUNTRIES).split(","))
        .map(Integer::parseInt).map(Country::getById).collect(Collectors.toSet());
    Set<Country> excludedCountries = Arrays.stream(map.get(EXCLUDE_COUNTRIES).split(","))
        .map(Integer::parseInt).map(Country::getById).collect(Collectors.toSet());

    return new RequestConfig.Builder(map.get(ERPK), map.get(TOKEN))
        .fromId(Integer.parseInt(map.get(FROM_ID))).toId(Integer.parseInt(map.get(TO_ID)))
        .includedCountries(includedCountries)
        .excludedCountries(excludedCountries)
        .addBlocked(Boolean.parseBoolean(map.get(ADD_BLOCKED)))
        .addDead(Boolean.parseBoolean(map.get(ADD_DEAD)))
        .build();
  }

  private static Function<String, Stream<String>> splitBySpaceFunction() {
    return new Function<String, Stream<String>>() {
      @Override
      public Stream<String> apply(String s) {
        return Stream.of(s.split(" "));
      }
    };
  }

  private static Function<String, Pair<ArgumentKey, String>> createPairFunction() {
    return new Function<String, Pair<ArgumentKey, String>>() {
      @Override
      public Pair<ArgumentKey, String> apply(String s) {
        String[] split = s.split("=");
        return Pair.of(ArgumentKey.fromValue(split[0]), split[1]);
      }
    };
  }
}
