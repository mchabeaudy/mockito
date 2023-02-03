package mockito.example.service;

import java.util.UUID;

import mockito.example.repository.UnicornRepository;

public class UnicornService {

  private final UnicornRepository repository;

  public UnicornService(UnicornRepository repository) {
    this.repository = repository;
  }

  public void updateUnicornSize(UUID id, int size) {
    var unicorn = getUnicorn(id);
    unicorn.setSize(size);
    repository.save(unicorn);
  }

  public Unicorn getUnicorn(UUID id) {
    return repository.findById(id)
      .orElseThrow(() -> new UnicornNotFoundException("Cannot find unicorn with id:" + id));
  }

  public UUID createUnicorn(CreateUnicornRequest request) {
    var unicorn = new Unicorn();
    unicorn.setId(UUID.randomUUID());
    unicorn.setSize(request.getSize());
    unicorn.setName(request.getName());
    repository.save(unicorn);
    return unicorn.getId();
  }

  public long getUnicornTotalCount() {
    return repository.count();
  }

  public UnicornsInfo getInfo() {
    var info = new UnicornsInfo();
    info.setTotalUnicorns(repository.count());
    info.setFemaleAverageSize(UnicornUtils.averageSize(UnicornGender.FEMALE));
    info.setMaleAverageSize(UnicornUtils.averageSize(UnicornGender.MALE));
    return info;
  }

}
