package groupthree.gitruler.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Bean
  public RestOperations restOperations() {
    return new RestTemplate();
  }

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
            .permitAll()
            .tokenEndpoint()
            .accessTokenResponseClient(new CustomOAuth2AccessTokenResponseClient(restOperations()));
  }
}