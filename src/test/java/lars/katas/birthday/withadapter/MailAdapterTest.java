package lars.katas.birthday.withadapter;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.junit.jupiter.api.Test;

class MailAdapterTest {

  private MailAdapter mailAdapter;

  @Test
  public void sendEmailMessage() throws Exception {
    try (SimpleSmtpServer dumbster = SimpleSmtpServer.start(SimpleSmtpServer.AUTO_SMTP_PORT)) {
      mailAdapter = new MailAdapter("localhost", dumbster.getPort());
      mailAdapter.sendMessage("the subject", "the body", "foo@bar.com");

      assertThat(dumbster.getReceivedEmails()).hasSize(1);
      SmtpMessage message = (SmtpMessage) dumbster.getReceivedEmails().getFirst();
      assertEquals("the body", message.getBody());
      assertEquals("the subject", message.getHeaderValue("Subject"));
      assertThat(message.getHeaderValues("To")).hasSize(1);
      assertEquals("foo@bar.com", message.getHeaderValues("To").getFirst());
    }
  }
}
