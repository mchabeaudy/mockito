package mockito.example.service;

public class CreateUnicornRequest {

  private String name;

  private int size;

  private UnicornGender gender;

  public CreateUnicornRequest() {
  }

  public CreateUnicornRequest(String name, int size, UnicornGender gender) {
    this.name = name;
    this.size = size;
    this.gender = gender;
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
