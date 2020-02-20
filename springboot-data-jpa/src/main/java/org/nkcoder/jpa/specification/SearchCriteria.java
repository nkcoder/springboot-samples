package org.nkcoder.jpa.specification;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchCriteria implements Serializable {

  private static final long serialVersionUID = 8305013367680572873L;

  private final String key;
  private final Operation operation;
  private final transient Object value;
}
