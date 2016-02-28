package react.services;

import org.springframework.security.access.prepost.PreAuthorize;
import react.models.SimpleModel;
import java.util.List;

public interface SimpleModelService {
  @PreAuthorize("hasRole('USER')")
  List<SimpleModel> generateSimpleModels();
}
