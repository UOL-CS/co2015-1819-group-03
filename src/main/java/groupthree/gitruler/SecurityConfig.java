package groupthree.gitruler;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/css/**")
          .permitAll()
        .and()
          .authorizeRequests()
            .anyRequest()
            .authenticated()
        .and()
          .oauth2Login()
            .loginPage("/")
            .defaultSuccessUrl("/exercises")
            .permitAll();
  }
}