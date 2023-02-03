package mockito.example.service;

import java.util.UUID;

public class Unicorn {

  private UUID id;

  private String name;

  private int size;

  private UnicornGender gender;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public UnicornGender getGender() {
    return gender;
  }

  public void setGender(UnicornGender gender) {
    this.gender = gender;
  }
}
