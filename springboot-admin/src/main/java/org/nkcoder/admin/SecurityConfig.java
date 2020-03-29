package org.nkcoder.admin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final AdminServerProperties adminServerProperties;

  public SecurityConfig(AdminServerProperties adminServerProperties) {
    this.adminServerProperties = adminServerProperties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String contextPath = adminServerProperties.getContextPath();

    SavedRequestAwareAuthenticationSuccessHandler successHandler =
        new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(contextPath + "/");

    http.authorizeRequests()
        .antMatchers(
            contextPath + "/instances",
            contextPath + "/login",
            contextPath + "/assets/**",
            contextPath + "/actuator/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage(contextPath + "/login")
        .successHandler(successHandler)
        .and()
        .logout()
        .logoutUrl(contextPath + "/logout")
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringRequestMatchers(
            new AntPathRequestMatcher(contextPath + "/instances", HttpMethod.POST.toString()),
            new AntPathRequestMatcher(contextPath + "/instances/*", HttpMethod.DELETE.toString()),
            new AntPathRequestMatcher(contextPath + "/actuator/**"))
        .and()
        .rememberMe()
        .key(UUID.randomUUID().toString())
        .tokenValiditySeconds(60 * 60 * 24);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("admin2020"))
        .roles("ADMIN");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
