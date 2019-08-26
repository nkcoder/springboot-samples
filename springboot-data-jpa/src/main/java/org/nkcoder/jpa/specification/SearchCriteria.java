package org.nkcoder.jpa.specification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchCriteria {

  private final String key;
  private final Operation operation;
  private final Object value;
}
