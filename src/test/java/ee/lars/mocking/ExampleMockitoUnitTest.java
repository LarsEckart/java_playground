package ee.lars.mocking;

import java.util.ArrayList;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ExampleMockitoUnitTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  private Collaborator collaborator;

  @InjectMocks
  private Performer performer;

  @Test
  public void testThePerformMethod() {
    Model model = new Model();
    given(this.collaborator.collaborate(model.getInfo())).willReturn(true);

    performer.perform(model);

    verify(this.collaborator).receive(true);
  }

  @Mock
  ExpectationsCollaborator mock;

  @Test
  public void verify_primitives() throws Exception {
    given(mock.methodForAny1(anyString(), anyInt(), anyBoolean())).willReturn("any");

    assertEquals("any", mock.methodForAny1("barfooxyz", 0, Boolean.FALSE));
  }

  @Test
  public void verify_list() throws Exception {
    mock.methodForAny2(2L, new ArrayList<>());

    verify(mock).methodForAny2(anyLong(), anyList());
  }
}
