package lars.katas.birthday.withadapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

@EnabledForJreRange(max = JRE.JAVA_22)
class BirthdayServiceTest {

  @Test
  public void testSendGreetings() throws Exception {
    MailAdapter mailAdapter = mock(MailAdapter.class);

    new BirthdayService(mailAdapter).sendBirthdayGreetings("jane.doe@example.com", "Jane");

    verify(mailAdapter)
        .sendMessage("Happy Birthday!", "Happy Birthday, dear Jane", "jane.doe@example.com");
  }
}
