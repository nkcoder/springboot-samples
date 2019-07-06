package org.nkcoder.jpa.repository;

import java.time.LocalDateTime;
import org.nkcoder.jpa.entity.Player;
import org.springframework.data.jpa.domain.Specification;

public class PlaySpecification {

  public static Specification<Player> bornAtYearsAgo(int years) {
    return (Specification<Player>) (root, query, criteriaBuilder) -> {
      LocalDateTime yearsAgo = LocalDateTime.now().minusYears(years);
      return criteriaBuilder.greaterThan(root.get("bornAt"), yearsAgo);
    };
  }

  public static Specification<Player> teamEquals(String team) {
    return (Specification<Player>) (root, query, builder) ->
        builder.equal(root.get("team"), team);
  }

}
