package com.github.driversti.erepublik.friendsadd;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class ArgumentParser {

  Map<String, String> parse(String[] args) {
    return Arrays.stream(args)
        .flatMap(splitBySpaceFunction())
        .map(createPairFunction())
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private static Function<String, Pair<String, String>> createPairFunction() {
    return new Function<String, Pair<String, String>>() {
      @Override
      public Pair<String, String> apply(String s) {
        String[] split = s.split("=");
        return Pair.of(split[0], split[1]);
      }
    };
  }

  private static Function<String, Stream<String>> splitBySpaceFunction() {
    return new Function<String, Stream<String>>() {
      @Override
      public Stream<String> apply(String s) {
        return Stream.of(s.split(" "));
      }
    };
  }
}
