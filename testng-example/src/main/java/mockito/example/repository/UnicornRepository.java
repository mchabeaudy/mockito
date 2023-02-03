package mockito.example.repository;

import java.util.Optional;
import java.util.UUID;

import mockito.example.service.Unicorn;

public interface UnicornRepository {

  long count();

  void save(Unicorn unicorn);

  void delete(UUID id);

  Optional<Unicorn> findById(UUID id);

}