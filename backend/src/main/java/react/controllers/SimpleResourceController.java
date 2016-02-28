package react.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import react.models.SimpleModel;
import react.services.SimpleModelService;

import java.util.List;

@RestController
public class SimpleResourceController implements SimpleResource {

  @Autowired
  private SimpleModelService simpleModelService;

  @Override
  public List<SimpleModel> generateSimpleModels() {
    return simpleModelService.generateSimpleModels();
  }
}
