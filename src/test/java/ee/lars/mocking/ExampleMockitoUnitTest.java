package ee.lars.mocking;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
}
