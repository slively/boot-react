package react.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import react.models.Credentials;
import react.models.User;
import react.security.TokenAuthenticationService;
import react.services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
public class AuthenticationResourceController implements AuthenticationResource {
  @Autowired
  UserService userService;

  @Autowired
  HttpServletResponse httpServletResponse;

  @Autowired
  TokenAuthenticationService tokenAuthenticationService;

  @Override
  public ResponseEntity<User> me() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return ResponseEntity.ok((User)authentication.getDetails());
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @Override
  public ResponseEntity<User> login(@RequestBody @Valid Credentials credentials) {
    final User user = userService.authenticate(credentials);
    if (user != null) {
      tokenAuthenticationService.addAuthentication(httpServletResponse, user);
      return ResponseEntity.ok(user);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @Override
  public ResponseEntity<User> register(@RequestBody @Valid  User user) {
    return ResponseEntity.ok(userService.register(user));
  }
}
