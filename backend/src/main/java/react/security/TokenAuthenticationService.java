package react.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import react.security.TokenHandler;
import react.security.UserAuthentication;
import react.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationService {

  private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

  @Value("${authentication.secret:change-this}")
  private String secret;

  private final TokenHandler tokenHandler;

  @Autowired
  public TokenAuthenticationService(TokenHandler tokenHandler) {
    this.tokenHandler = tokenHandler;
  }

  public void addAuthentication(HttpServletResponse response, User user) {
    response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    UserAuthentication userAuthentication;
    if (token != null) {
      final User user = tokenHandler.parseUserFromToken(token);
      if (user != null) {
        userAuthentication = new UserAuthentication(user);
        return userAuthentication;
      }
    }
    userAuthentication = new UserAuthentication(null);
    return userAuthentication;
  }
}
