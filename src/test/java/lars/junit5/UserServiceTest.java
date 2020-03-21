package lars.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
