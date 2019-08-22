package org.nkcoder.cache.controller;

import org.nkcoder.cache.service.CalculateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CalculateController {

  private final CalculateService calculateService;

  public CalculateController(CalculateService calculateService) {
    this.calculateService = calculateService;
  }

  @GetMapping("")
  public Integer calculate(@RequestParam("x") int x, @RequestParam("y") int y) {
    return calculateService.calculate(x, y);
  }

}
