package org.nkcoder.admin;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // all url need to authorize, except for /health and /info
    /*
    http.requestMatcher(EndpointRequest.toAnyEndpoint().excluding("health", "info"))
        .authorizeRequests().anyRequest().hasRole("ADMIN")
        .and().httpBasic();
     */

    // only /beans, /threaddump, /heapdump need to authorize
    http.requestMatcher(EndpointRequest.to("beans", "threaddump", "heapdump"))
        .authorizeRequests().anyRequest().hasRole("ADMIN")
        .and()
        .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser("admin")
        .password("password")
        .roles("ADMIN")
        .authorities("ROLE_ADMIN");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}
