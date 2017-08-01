package ee.lars.springmvc.spittr.data;

import ee.lars.springmvc.spittr.Spitter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    private final JdbcOperations jdbcOperations;

    @Inject
    public JdbcSpitterRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Spitter save(Spitter spitter) {
        this.jdbcOperations.update(
                "insert into Spitter (username, password, first_name, last_name, email)" +
                        " values (?, ?, ?, ?, ?)",
                spitter.getUsername(),
                spitter.getPassword(),
                spitter.getFirstName(),
                spitter.getLastName(),
                spitter.getEmail());
        return spitter;
    }

    @Override
    @Cacheable("userCache")
    public Spitter findByUsername(String username) {
        return this.jdbcOperations.queryForObject(
                "select id, username, null, first_name, last_name, email from Spitter where username=?",
                (rs, row) -> new Spitter(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email")),
                username);
    }
}
