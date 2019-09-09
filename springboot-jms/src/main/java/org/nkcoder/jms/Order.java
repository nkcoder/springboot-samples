package org.nkcoder.jms;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Order implements Serializable {

  private static final long serialVersionUID = -6563479310877027215L;

  private final String id;
  private final Integer count;

}
