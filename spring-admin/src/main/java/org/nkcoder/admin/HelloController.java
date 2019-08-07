package org.nkcoder.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

  private final HelloRepository helloRepository;

  public HelloController(HelloRepository helloRepository) {
    this.helloRepository = helloRepository;
  }

  @GetMapping("")
  public String hello() {
    log.debug("receive request: /hello");
    return helloRepository.hello();
  }

}
