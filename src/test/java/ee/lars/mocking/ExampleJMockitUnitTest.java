package ee.lars.mocking;

import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JMockit.class)
public final class ExampleJMockitUnitTest {

    @Injectable
    private Collaborator collaborator;

    @Tested
    private Performer performer;

    @Test
    public void testThePerformMethod(@Mocked Model model) {
        new Expectations() {{
            model.getInfo();
            result = "bar";
            collaborator.collaborate("bar");
            result = true;
        }};

        performer.perform(model);

        new Verifications() {{
            collaborator.receive(true);
        }};
    }

    @Test
    public void test(@Mocked ExpectationsCollaborator mock) throws Exception {
        new Expectations() {{
            mock.methodForAny1(anyString, anyInt, anyBoolean);
            result = "any";
        }};

        assertEquals("any", mock.methodForAny1("barfooxyz", 0, Boolean.FALSE));


        mock.methodForAny2(2L, new ArrayList<>());

        new FullVerifications() {{
            mock.methodForAny2(anyLong, (List<String>) any);
        }};
    }
}
