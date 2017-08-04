package ee.lars.contacts;

import java.util.List;

public interface ContactRepository {

    List<Contact> findAll();

    void save(Contact contact);
}
