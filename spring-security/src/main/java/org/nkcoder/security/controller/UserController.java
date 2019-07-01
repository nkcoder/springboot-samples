package org.nkcoder.security.controller;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @GetMapping("/principal")
  public String getUserByPrincipal(Principal principal) {
    log.info("username: {}", principal.getName());
    return principal.getName();
  }

  @GetMapping("/authentication")
  public String getUserByAuthentication(Authentication authentication) {
    String userName = ((User) authentication.getPrincipal()).getUsername();
    log.info("username: {}, authority: {}", userName, authentication.getAuthorities());
    return userName;
  }

  @GetMapping("/auth-principal")
  public String getUserByAuthPrincipal(@AuthenticationPrincipal User user) {
    log.info("username: {}", user.getUsername());
    return user.getUsername();
  }

  @GetMapping("/security-context")
  public String getUserBySecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    log.info("username: {}", user.getUsername());
    return user.getUsername();
  }


}
