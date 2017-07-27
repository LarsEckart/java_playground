package ee.lars.spring.knight;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BraveKnightTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Quest questMock;

    @Before
    public void initialize() throws Exception {

    }

    @Test
    public void should_embark_quest_when_embackOnQuest() throws Exception {
        // given
        final Knight knight = new BraveKnight(this.questMock);

        // when
        knight.embarkOnQuest();

        // then
        verify(this.questMock, times(1)).embark();
    }
}