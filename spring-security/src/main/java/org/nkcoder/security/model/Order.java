package org.nkcoder.security.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order implements Serializable {

  private static final long serialVersionUID = -2756445012312541507L;

  private final Long id;

  private final String name;

  private final String contactName;

  private final LocalDateTime createdAt;
}
