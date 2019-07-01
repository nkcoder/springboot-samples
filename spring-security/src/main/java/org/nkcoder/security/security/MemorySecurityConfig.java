package org.nkcoder.security.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value = "auth.store", havingValue = "memory")
public class MemorySecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("kobe")
        .password("{noop}123456")
        .authorities("USER")
        .and()
        .withUser("durant")
        .password("{noop}123456")
        .authorities("USER");
  }
}