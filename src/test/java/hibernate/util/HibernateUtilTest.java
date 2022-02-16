package hibernate.util;

import com.hibernate.util.HibernateConfigurationUtil;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateUtilTest {
    private static final PostgreSQLContainer<?> psql = new PostgreSQLContainer("postgres:14-alpine");

    static {
        psql.start();
    }

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = HibernateConfigurationUtil.buildConfiguration();
       configuration.setProperty("hibernate.connection.url", psql.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", psql.getUsername());
        configuration.setProperty("hibernate.connection.password", psql.getPassword());
       configuration.configure();
        return configuration.buildSessionFactory();
    }
}
