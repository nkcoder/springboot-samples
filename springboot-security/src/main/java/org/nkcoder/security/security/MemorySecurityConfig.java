package org.nkcoder.security.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value = "auth.store", havingValue = "memory")
public class MemorySecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("kobe")
        .password(passwordEncoder().encode("123456"))
        .authorities("USER")
        .and()
        .withUser("durant")
        .password(passwordEncoder().encode("123456"))
        .roles("ADMIN")
        .authorities("USER");
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
