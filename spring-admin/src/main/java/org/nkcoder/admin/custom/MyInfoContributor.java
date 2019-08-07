package org.nkcoder.admin.custom;

import java.util.Map;
import org.nkcoder.admin.HelloRepository;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class MyInfoContributor implements InfoContributor {

  private final HelloRepository helloRepository;

  public MyInfoContributor(HelloRepository helloRepository) {
    this.helloRepository = helloRepository;
  }

  @Override
  public void contribute(Builder builder) {
    Integer numberOfUsers = helloRepository.numberOfUsers();

    Map<String, Object> myInfo = Map.of("number-of-users", numberOfUsers);

    builder.withDetail("spring.demo.admin", myInfo);

  }

}
