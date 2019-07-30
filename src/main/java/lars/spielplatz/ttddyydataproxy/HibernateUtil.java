package lars.spielplatz.ttddyydataproxy;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import static org.hibernate.cfg.AvailableSettings.AUTOCOMMIT;
import static org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS;
import static org.hibernate.cfg.Environment.DATASOURCE;
import static org.hibernate.cfg.Environment.DIALECT;
import static org.hibernate.cfg.Environment.HBM2DDL_AUTO;
import static org.hibernate.cfg.Environment.ORDER_INSERTS;
import static org.hibernate.cfg.Environment.ORDER_UPDATES;
import static org.hibernate.cfg.Environment.STATEMENT_BATCH_SIZE;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    static synchronized SessionFactory getSessionFactory(String url) {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory(url);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory(null).close();
    }

    public static Session getSession(String url) {
        return getSessionFactory(url).openSession();
    }

    private static SessionFactory buildSessionFactory(String url) {
        try {
            final Map<String, Object> settings = new HashMap<>();
            settings.put(DATASOURCE, buildProxyDataSource(url));
            settings.put(HBM2DDL_AUTO, "validate");
            settings.put(ORDER_UPDATES, true);
            settings.put(ORDER_INSERTS, true);
            settings.put(STATEMENT_BATCH_SIZE, 10);
            settings.put(DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(AUTOCOMMIT, false);
            settings.put(HBM2DDL_AUTO, "create");

            final StandardServiceRegistryBuilder standardRegistryBuilder = new StandardServiceRegistryBuilder();
            standardRegistryBuilder.applySettings(settings);

            final StandardServiceRegistry standardRegistry = standardRegistryBuilder.build();

            final MetadataSources metadataSources = new MetadataSources(standardRegistry).addAnnotatedClass(SomeEntity.class);

            final Metadata metadata = metadataSources.getMetadataBuilder().build();
            return metadata.buildSessionFactory();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static DataSource buildProxyDataSource(String url) {
        return ProxyDataSourceBuilder.create(buildDataSource(url))
                .name("ProxyDataSource")
                .countQuery()
                .build();
    }

    private static DataSource buildDataSource(String url) {
        final MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUser("hibernateuser");
        dataSource.setPassword("hibernatepassword");
        dataSource.setURL(url);

        return dataSource;
    }
}

