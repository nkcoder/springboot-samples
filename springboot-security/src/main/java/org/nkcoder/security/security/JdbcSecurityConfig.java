package org.nkcoder.security.security;

import javax.sql.DataSource;
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
@ConditionalOnProperty(value = "auth.store", havingValue = "jdbc")
public class JdbcSecurityConfig extends WebSecurityConfigurerAdapter {

  private DataSource dataSource;

  public JdbcSecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled from user where username = ?")
        .authoritiesByUsernameQuery(
            "select username, authority from user_authority where username = ?")
        .passwordEncoder(passwordEncoder());
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
