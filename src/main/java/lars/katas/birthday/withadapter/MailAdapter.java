package lars.katas.birthday.withadapter;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

class MailAdapter {

  private final String server;
  private final int port;

  public MailAdapter(String server, int port) {
    this.server = server;
    this.port = port;
  }

  public void sendMessage(String subject, String content, String recipient) {
    try {
      java.util.Properties props = new java.util.Properties();
      props.put("mail.smtp.host", server);
      props.put("mail.smtp.port", Integer.valueOf(port));
      Session session = Session.getInstance(props, null);

      // Construct the message
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("sender@example.com"));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      message.setSubject(subject);
      message.setText(content);

      // Send the message
      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
