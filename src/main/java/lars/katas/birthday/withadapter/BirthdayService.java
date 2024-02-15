package lars.katas.birthday.withadapter;

class BirthdayService {

  private final MailAdapter mailAdapter;

  public BirthdayService(MailAdapter mailAdapter) {
    this.mailAdapter = mailAdapter;
  }

  public void sendBirthdayGreetings(String recipientEmail, String name) {
    mailAdapter.sendMessage("Happy Birthday!", "Happy Birthday, dear " + name, recipientEmail);
  }
}
