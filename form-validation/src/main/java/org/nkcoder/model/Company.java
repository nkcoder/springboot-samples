package org.nkcoder.model;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.nkcoder.validation.UTF8Size;

@Getter
@RequiredArgsConstructor
public class Company {

  @NotEmpty(message = "name is required")
  @UTF8Size(max = 32, message = "name should be short than 32")
  private final String name;

  @NotEmpty(message = "employees is required")
  private final List<Employee> employees;

}
