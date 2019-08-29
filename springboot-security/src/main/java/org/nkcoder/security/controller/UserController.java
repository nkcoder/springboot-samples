package org.nkcoder.security.controller;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/principal")
  public Principal getUserByPrincipal(Principal principal) {
    return principal;
  }

  @GetMapping("/authentication")
  public Principal getUserByAuthentication(Authentication authentication) {
    return authentication;
  }

  @GetMapping("/auth-principal")
  public User getUserByAuthPrincipal(@AuthenticationPrincipal User user) {
    return user;
  }

  @GetMapping("/security-context")
  public Principal getUserBySecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication;
  }

}
