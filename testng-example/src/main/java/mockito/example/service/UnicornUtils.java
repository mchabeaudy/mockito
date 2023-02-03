package mockito.example.service;

public final class UnicornUtils {

  public static int averageSize(UnicornGender gender) {
    return switch (gender) {
      case MALE -> 135;
      case FEMALE -> 127;
    };
  }

}
