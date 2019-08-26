package org.nkcoder.jpa.specification;

import java.time.LocalDate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.nkcoder.jpa.entity.Player;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecification implements Specification<Player> {

  private final SearchCriteria searchCriteria;

  public PlayerSpecification(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  @Override
  public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    String key = searchCriteria.getKey();
    Operation operation = searchCriteria.getOperation();
    Object value = searchCriteria.getValue();

    Predicate predicate = null;
    Class<?> keyType = root.get(key).getJavaType();

    switch (operation) {
      case GREATER_THAN:
        if (keyType == LocalDate.class) {
          predicate = criteriaBuilder.greaterThan(root.get(key), (LocalDate) value);
        } else {
          predicate = criteriaBuilder.greaterThan(root.get(key), value.toString());
        }
        break;
      case LESS_THAN:
        if (keyType == LocalDate.class) {
          predicate = criteriaBuilder.lessThan(root.get(key), (LocalDate) value);
        } else {
          predicate = criteriaBuilder.lessThan(root.get(key), value.toString());
        }
        break;
      case EQUAL:
        if (root.get(key).getJavaType() == String.class) {
          predicate = criteriaBuilder.like(root.get(key), "%" + value + "%");
        } else {
          predicate = criteriaBuilder.equal(root.get(key), value);
        }
    }

    return predicate;
  }

}
