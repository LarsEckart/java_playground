package lars.katas.birthday;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

class BirthdayGreetings {

  void sendBirthdayGreetings(String recipient, String firstName) throws MessagingException {
    // Construct the bits of the message
    String body = "Happy Birthday, dear %NAME%".replace("%NAME%", firstName);
    String subject = "Happy Birthday!";

    // Create a mail session
    java.util.Properties props = new java.util.Properties();
    props.put("mail.smtp.host", "smtpHost");
    props.put("mail.smtp.port", Integer.valueOf("587"));
    Session session = Session.getInstance(props, null);

    // Construct the message
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("sender@example.com"));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
    message.setSubject(subject);
    message.setText(body);

    // Send the message
    Transport.send(message);
  }
}
