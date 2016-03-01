package react.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import react.models.ErrorMessage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessAuthenticationFilter extends GenericFilterBean {

  private final TokenAuthenticationService authenticationService;
  private final ObjectMapper objectMapper;

  public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService, ObjectMapper objectMapper) {
    this.authenticationService = authenticationService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    Authentication authentication;
    try {
      authentication = authenticationService.getAuthentication(httpRequest);
    } catch(JwtException je) {
      final HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.setContentType("application/json");
      httpResponse.getWriter().print(objectMapper.writeValueAsString(new ErrorMessage(je.getMessage(), "login.error.badLogin")));
      return;
    }



    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
    SecurityContextHolder.getContext().setAuthentication(null);
  }
}
