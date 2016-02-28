package react.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import react.models.SimpleModel;
import react.persistence.models.SimpleModelPersistence;
import react.persistence.SimpleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class SimpleModelServiceImpl implements SimpleModelService{
  @Autowired
  private SimpleRepository simpleRepository;
  private final Random random = new Random();

  @Override
  public List<SimpleModel> generateSimpleModels() {
    List<SimpleModelPersistence> simplePersistedModels = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      SimpleModelPersistence smp = SimpleModelPersistence.builder().value(random.nextInt(100)).build();
      simplePersistedModels.add(smp);
    }

    simpleRepository.save(simplePersistedModels);
    simpleRepository.flush();

    return simplePersistedModels
      .stream()
      .map(SimpleModelPersistence::toSimpleModel)
      .collect(Collectors.toList());
  }
}
