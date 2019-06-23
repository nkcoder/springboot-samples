package org.nkcoder.validation;

import java.nio.charset.Charset;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Utf8SizeValidator implements ConstraintValidator<UTF8Size, String> {

  private int maxCharSize;

  @Override
  public void initialize(UTF8Size constraintAnnotation) {
    this.maxCharSize = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return true;
    }
    return value.getBytes(Charset.forName("GB18030")).length <= maxCharSize;
  }
}
