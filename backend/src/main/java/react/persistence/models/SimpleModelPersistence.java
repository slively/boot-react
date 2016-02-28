package react.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import react.models.SimpleModel;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "simple_models")
public class SimpleModelPersistence {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "value", nullable = false)
  private Integer value;

  public SimpleModel toSimpleModel() {
    return SimpleModel.builder().value(value).build();
  }
}
