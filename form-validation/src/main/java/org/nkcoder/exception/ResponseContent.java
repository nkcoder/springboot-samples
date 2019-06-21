package org.nkcoder.exception;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseContent implements Serializable {

  private final String code;
  private final String message;
  private final Serializable data;

}
