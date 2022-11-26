package com.github.driversti.erepublik.friendsadd.argsparser;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class ArgumentParser {

  public Map<ArgumentKey, String> parse(String[] args) {
    return Arrays.stream(args)
        .flatMap(splitBySpaceFunction())
        .map(createPairFunction())
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private static Function<String, Stream<String>> splitBySpaceFunction() {
    return argValue -> Stream.of(argValue.split(" "));
  }

  private static Function<String, Pair<ArgumentKey, String>> createPairFunction() {
    return argValue -> {
      String[] split = argValue.split("=");
      return Pair.of(ArgumentKey.fromValue(split[0]), split[1]);
    };
  }
}
