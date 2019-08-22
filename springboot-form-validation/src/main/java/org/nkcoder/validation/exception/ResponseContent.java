package org.nkcoder.validation.exception;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseContent implements Serializable {

  private static final long serialVersionUID = -2369285516295767134L;

  private final String code;
  private final String message;
  private final Serializable data;

}
