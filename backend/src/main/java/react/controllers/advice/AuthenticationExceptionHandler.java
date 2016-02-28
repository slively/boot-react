package react.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import react.models.ErrorMessage;

@Slf4j
@ControllerAdvice
public class AuthenticationExceptionHandler {
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthenticationException.class)
  public ErrorMessage handleAuthenticationException(AuthenticationException e) {
    log.debug("Incorrect login");
    return new ErrorMessage(e.getMessage(), "login.error.badLogin");
  }

}
