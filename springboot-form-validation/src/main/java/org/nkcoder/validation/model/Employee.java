package org.nkcoder.validation.model;

import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.nkcoder.validation.validator.UTF8Size;

@Getter
@ToString
public class Employee implements Serializable {

  private static final long serialVersionUID = -8224860450904540019L;

  @NotEmpty(message = "name is required")
  @UTF8Size(max = 16, message = "name should be short than 128")
  private final String name;

  @NotBlank(message = "city is required")
  @Size(max = 128, message = "city should be short than 128")
  private final String city;

  @CreditCardNumber(message = "invalid credit card number")
  private final String ccNumber;

  @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$", message = "required format MM/YY")
  private final String ccExpiration;

  @Digits(integer = 3, fraction = 0, message = "invalid CVV")
  private final String ccCVV;

  public Employee(
      String name,
      String city,
      String ccNumber,
      String ccExpiration,
      String ccCVV) {
    this.name = name;
    this.city = city;
    this.ccNumber = ccNumber;
    this.ccExpiration = ccExpiration;
    this.ccCVV = ccCVV;
  }

}
