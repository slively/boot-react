package react.security;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import react.models.User;
import react.services.UserService;

@Component
public final class TokenHandler {

  @Value("${security.token.secret:some-secret}")
  private String secret;

  @Value("${security.token.TTLMinutes:1440}")
  private Integer ttlMinutes;

  private final UserService userService;

  @Autowired
  public TokenHandler(UserService userService) {
    this.userService = userService;
  }

  public User parseUserFromToken(String token) {
    Claims claims = Jwts.parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody();

    // TODO: make this lazy?
    return userService.loadUserByUsername(claims.getSubject());
  }

  public String createTokenForUser(User user) {
    return Jwts.builder()
      .setSubject(user.getUsername())
      .setExpiration(DateTime.now().plusMinutes(ttlMinutes).toDate())
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
  }
}
