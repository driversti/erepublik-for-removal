package com.github.driversti.erepublik.friendsadd;

import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ADD_BLOCKED;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ADD_DEAD;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.ERPK;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.EXCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.FROM_ID;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.INCLUDE_COUNTRIES;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TOKEN;
import static com.github.driversti.erepublik.friendsadd.ArgumentKey.TO_ID;

import com.github.driversti.erepublik.friendsadd.RequestConfig.Builder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.BooleanUtils;

public class RequestConfigFactory {

  RequestConfig create(Map<ArgumentKey, String> argumentMap) {
    Builder builder = new Builder(argumentMap.get(ERPK), argumentMap.get(TOKEN));
    setFromId(argumentMap.get(FROM_ID), builder);
    setToId(argumentMap.get(TO_ID), builder);
    setIncludedCountries(argumentMap.get(INCLUDE_COUNTRIES), builder);
    setExcludedCountries(argumentMap.get(EXCLUDE_COUNTRIES), builder);

    return builder
        .addBlocked(BooleanUtils.toBoolean(argumentMap.get(ADD_BLOCKED)))
        .addDead(BooleanUtils.toBoolean(argumentMap.get(ADD_DEAD)))
        .build();
  }

  private void setFromId(String fromId, Builder builder) {
    if (fromId == null) {
      return;
    }
    builder.fromId(Integer.parseInt(fromId));
  }

  private void setToId(String toId, Builder builder) {
    if (toId == null) {
      return;
    }
    builder.toId(Integer.parseInt(toId));
  }

  private void setIncludedCountries(String includedCountries, Builder builder) {
    if (includedCountries == null) {
      return;
    }
    Set<Country> countries = Arrays.stream(includedCountries.split(","))
        .map(String::strip).map(Integer::parseInt).map(Country::getById)
        .collect(Collectors.toSet());
    builder.includedCountries(countries);
  }

  private void setExcludedCountries(String excludedCountries, Builder builder) {
    if (excludedCountries == null) {
      return;
    }
    Set<Country> countries = Arrays.stream(excludedCountries.split(","))
        .map(String::strip).map(Integer::parseInt).map(Country::getById)
        .collect(Collectors.toSet());
    builder.excludedCountries(countries);
  }
}
