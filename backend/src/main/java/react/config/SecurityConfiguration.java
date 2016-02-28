package react.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import react.security.StatelessAuthenticationFilter;
import react.security.TokenAuthenticationService;
import react.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("react.security")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  @Autowired
  private UserService userService;

  public SecurityConfiguration() {
    super(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .exceptionHandling().and()
      .servletApi().and()
      .authorizeRequests().anyRequest().permitAll().and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .headers().cacheControl();

    http.addFilterBefore(
      new StatelessAuthenticationFilter(tokenAuthenticationService),
      UsernamePasswordAuthenticationFilter.class
    );
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(userDetailsService());
  }

  @Override
  public UserService userDetailsService() {
    return userService;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
