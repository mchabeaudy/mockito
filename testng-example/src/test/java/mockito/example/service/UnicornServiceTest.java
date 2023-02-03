package mockito.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.UUID;

import mockito.example.repository.UnicornRepository;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UnicornServiceTest {

  @Mock
  UnicornRepository repository;

  @InjectMocks
  UnicornService service;

  // init mocks once in before class method
  @BeforeClass
  public void setUpMocks() {
    initMocks(this);
  }

  // reset mocks before each method
  @BeforeMethod
  public void resetMocks() {
    reset(repository);
  }


  // doReturn vs thenReturn
  // when(mock.someMethod()).then will call someMethod once before it's getting mocked
  // in most of the cases it has no impact is safe, but you have to keep it in mind.

  @Test
  public void testCountFails() {
    doThrow(new RuntimeException("boom!")).when(repository).count();

    assertThatThrownBy(() -> service.getUnicornTotalCount()).hasMessage("boom!");
  }

  @Test
  public void testCount() {
    // when(mock.someMethod()) might be dangerous if repository.count() has not been reset
    // in this case testCountFails is mocking a failure, if we remove reset(repository) in @BeforeMethod
    // this might throw an exception
    when(repository.count()).thenReturn(15L);

    // doReturn is safer
    doReturn(15L).when(repository).count();

    assertThat(service.getUnicornTotalCount()).isEqualTo(15L);
  }

  @Test
  public void testCountWithSpyNotWorking() {
    // Same thing with spy, if you create a spy you have to use doReturn/doThrow
    // If not you code may not work
    // In this example this code fails
    var mockedRepo = mock(UnicornRepository.class);
    var spiedService = spy(new UnicornService(mockedRepo));
    UUID id = UUID.randomUUID();

    when(spiedService.getUnicorn(any())).thenReturn(new Unicorn());

    spiedService.updateUnicornSize(id, 130);
    verify(mockedRepo).save(any());
  }

  @Test
  public void testCountWithSpyWorking() {
    // working with doReturn
    var mockedRepo = mock(UnicornRepository.class);
    var spiedService = spy(new UnicornService(mockedRepo));
    UUID id = UUID.randomUUID();

    doReturn(new Unicorn()).when(spiedService).getUnicorn(any());

    spiedService.updateUnicornSize(id, 130);
    verify(mockedRepo).save(any());
  }

  @Test
  public void useStaticImportPlease(){
    Mockito.doReturn(Mockito.mock(Unicorn.class)).when(repository).save(Mockito.any());
    // all the Mockito. there make the test less readable
  }

  // any() vs any(T.class)
  @Test
  public void anyVsAnyClass(){
    // any() does not check anything while any(T.class) will check the argument

    // if you don't have to check the argument use this
    doReturn(new Unicorn()).when(repository).save(any());

    // if you have to check the argument use this
    doReturn(new Unicorn()).when(repository).save(any(Unicorn.class));

    // in most of the cases you don't have to check the argument
    // checking the argument is useful when you want to mock the same method with different behavior
  }



  //// comes with new version

  // artThat
  @Test
  public void testSave() {
    var request = new CreateUnicornRequest("Honey", 125, UnicornGender.MALE);

    service.createUnicorn(request);

    // argThat verifies a condition of the argument
    verify(repository).save(argThat(unicorn -> unicorn.getName().equals("Honey")));
  }

  // Static mock and AdditionalAnswers
  @Test
  public void testMockStatic() {
    try (MockedStatic<UnicornUtils> mocked = mockStatic(UnicornUtils.class)) {
      mocked.when(() -> UnicornUtils.averageSize(any())).thenAnswer(
        AdditionalAnswers.<Integer, UnicornGender>answer(gender -> gender == UnicornGender.MALE ? 140 : 130));

      assertThat(UnicornUtils.averageSize(UnicornGender.MALE)).isEqualTo(140);
      assertThat(UnicornUtils.averageSize(UnicornGender.FEMALE)).isEqualTo(130);
    }
  }

}
