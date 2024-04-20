package lars.junit5;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@EnabledForJreRange(max = JRE.JAVA_22)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  private UserService userService;

  @Mock UserRepository repository;

  @Test
  void givenValidUser_whenSaveUser_thenSucceed() {
    assertNotNull(repository);
  }

  private class User {

    public Object getId() {
      throw new UnsupportedOperationException("implement me!");
    }
  }

  private class UserService {}

  private class UserRepository {

    public void insert(User user) {
      throw new UnsupportedOperationException("implement me!");
    }
  }
}
