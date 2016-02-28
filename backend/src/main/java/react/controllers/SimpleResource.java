package react.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import react.models.SimpleModel;

import java.util.List;

@RequestMapping("/api/simple")
public interface SimpleResource {

  @RequestMapping(value = "", method = RequestMethod.GET)
  List<SimpleModel> generateSimpleModels();
}
