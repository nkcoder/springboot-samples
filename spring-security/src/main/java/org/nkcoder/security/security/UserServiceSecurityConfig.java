package org.nkcoder.security.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value = "auth.store", havingValue = "service")
public class UserServiceSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  public UserServiceSecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
        .antMatchers("/", "/h2-console/**").permitAll()

        .and()
        .csrf().ignoringAntMatchers("/h2-console/**")

        .and()
        .formLogin()

        .and()
        .httpBasic()

        .and()
        .headers().frameOptions().sameOrigin();

  }
}
