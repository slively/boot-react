package react.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonDeserialize(builder = Credentials.CredentialsBuilder.class)
public class Credentials {
  @NotNull
  private String username;
  @NotNull
  private String password;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CredentialsBuilder {}
}
