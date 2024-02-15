package lars.katas.birthday;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import jakarta.mail.Message;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class BirthdayGreetingsTest {

  public BirthdayService birthdayService = new BirthdayService();

  @Test
  public void testSendGreetings() throws Exception {
    try (var transportMock = Mockito.mockStatic(Transport.class)) {
      String recipient = "jane.doe@example.com";
      String firstName = "Jane";

      birthdayService.sendBirthdayGreetings(recipient, firstName);

      ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
      transportMock.verify(() -> Transport.send(argumentCaptor.capture()));
      Message sentMessage = argumentCaptor.getValue();
      assertThat(sentMessage.getSubject(), is("Happy Birthday!"));
      assertThat(sentMessage.getContent(), is("Happy Birthday, dear Jane"));
      assertThat(sentMessage.getRecipients(Message.RecipientType.TO).length, is(1));
      assertThat(
          sentMessage.getRecipients(Message.RecipientType.TO)[0],
          is(new InternetAddress("jane.doe@example.com")));
    }
  }
}
