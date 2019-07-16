package org.nkcoder.jms;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class Order implements Serializable {

  private final String id;
  private final Integer count;

}
