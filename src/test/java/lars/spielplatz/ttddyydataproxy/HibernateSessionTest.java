package lars.spielplatz.ttddyydataproxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

// from https://blog.codecentric.de/en/2019/07/hibernate-caching/

@Testcontainers
@EnabledOnOs({OS.LINUX, OS.MAC})
@Tag("slow")
class HibernateSessionTest {

  @Container
  private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres"))
          .withUsername("hibernateuser")
          .withPassword("hibernatepassword")
          .withDatabaseName("hibernatetest");

  private static void deleteAllEntities() {
    try (final Session session = HibernateUtil.getSession(POSTGRE_SQL_CONTAINER.getJdbcUrl())) {
      final Transaction transaction = session.beginTransaction();
      session.createMutationQuery("DELETE FROM SomeEntity").executeUpdate();
      transaction.commit();

      QueryCountHolder.clear();
    }
  }

  @BeforeAll
  static void setUp() {
    deleteAllEntities();
  }

  @AfterAll
  static void tearDown() {
    deleteAllEntities();
    HibernateUtil.shutdown();
  }

  @Test
  void sessionCacheIsInterTransactional() {
    try (final Session session = HibernateUtil.getSession(POSTGRE_SQL_CONTAINER.getJdbcUrl())) {

      final Transaction transactionA = session.beginTransaction();
      final int entityId = 1;
      createEntity(session, entityId);
      transactionA.commit();

      final Transaction transactionB = session.beginTransaction();
      // clear cache after entity creation, otherwise we would have no select at all
      session.clear();
      // intended only select
      final Date entityCreationDateA = readEntityCreationDate(session, entityId);
      transactionB.commit();

      final Transaction transactionC = session.beginTransaction();
      // another read, but no further select expected although we opened a different transaction
      // context
      final Date entityCreationDateB = readEntityCreationDate(session, entityId);
      transactionC.commit();

      assertThat(entityCreationDateB).isEqualTo(entityCreationDateA);

      final QueryCount grandTotal = QueryCountHolder.getGrandTotal();
      assertThat(grandTotal.getInsert()).isEqualTo(1);
      assertThat(grandTotal.getSelect()).isEqualTo(1);
      assertThat(grandTotal.getDelete()).isEqualTo(0);
      assertThat(grandTotal.getUpdate()).isEqualTo(0);
    }
  }

  private Date readEntityCreationDate(final Session session, final int entityId) {
    return session.load(SomeEntity.class, entityId).getCreatedDate();
  }

  private void createEntity(final Session session, final int entityId) {
    session.save(new SomeEntity(entityId));
  }
}
