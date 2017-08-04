package ee.lars.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcContactRepository implements ContactRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcContactRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Contact> findAll() {
        return jdbc.query(
                "select id, firstName, lastName, phoneNumber, emailAddress " +
                        "from contacts order by lastName",
                (rs, rowNum) -> {
                    Contact contact =
                            new Contact(
                                    rs.getLong(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5));
                    return contact;
                }
        );
    }

    public void save(Contact contact) {
        jdbc.update(
                "insert into contacts " +
                        "(firstName, lastName, phoneNumber, emailAddress) " +
                        "values (?, ?, ?, ?)",
                contact.getFirstName(), contact.getLastName(),
                contact.getPhoneNumber(), contact.getEmailAddress());
    }
}