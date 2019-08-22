package org.nkcoder.admin;

import java.util.Random;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {

  String hello() {
    return "world!";
  }

  public Integer numberOfUsers() {
    return new Random().nextInt(100);
  }


}
