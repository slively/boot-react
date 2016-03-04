package react.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import react.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationService {

  private static final String AUTH_HEADER_NAME = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";

  @Value("${authentication.secret:change-this}")
  private String secret;

  private final TokenHandler tokenHandler;

  @Autowired
  public TokenAuthenticationService(TokenHandler tokenHandler) {
    this.tokenHandler = tokenHandler;
  }

  public void addAuthentication(HttpServletResponse response, User user) {
    response.addHeader(AUTH_HEADER_NAME,TOKEN_PREFIX + tokenHandler.createTokenForUser(user));
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    UserAuthentication userAuthentication;
    if (token != null) {
      final User user = tokenHandler.parseUserFromToken(token.replace(TOKEN_PREFIX, ""));
      if (user != null) {
        userAuthentication = new UserAuthentication(user);
        return userAuthentication;
      }
    }
    userAuthentication = new UserAuthentication(null);
    return userAuthentication;
  }
}
