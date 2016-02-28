package react.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import react.models.Credentials;
import react.models.User;

import javax.validation.Valid;

@RequestMapping("/api/auth")
public interface AuthenticationResource {

  @RequestMapping(path = "/me", method = RequestMethod.GET)
  ResponseEntity<User> me();

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  ResponseEntity<User> login(@RequestBody @Valid  Credentials credentials);

  @RequestMapping(path = "/register", method = RequestMethod.POST)
  ResponseEntity<User> register(@RequestBody @Valid  User user);
}
