package org.nkcoder.cache.service;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CalculatorServiceTest {

  @Autowired
  private CalculateService calculateService;

  @Test
  public void shouldReturnResult_whenCalculate() {
    IntStream.rangeClosed(1, 3).forEach(v -> calculateService.calculate(3, 3));
  }

}
