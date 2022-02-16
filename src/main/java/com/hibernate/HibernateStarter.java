package com.hibernate;

import com.hibernate.entity.Company;
import com.hibernate.util.HibernateConfigurationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;

//@Slf4j
public class HibernateStarter {
    public static void main(String[] args) throws SQLException {
        Company company = Company.builder()
                .name("NevaDa")
                .build();

//        log.info("The entity of User is a transient state, object {}");

//If you need to use annotations without hibernate.cfg.xml use the java code below
//configuration.addAnnotatedClass(User.class);

        try (SessionFactory sessionFactory = HibernateConfigurationUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                Transaction transaction = session1.beginTransaction();
//               User user1 = session1.get(User.class, 5L);
//               session1.evict(user1);
                session1.saveOrUpdate(company);
                session1.getTransaction().commit();
            } catch (Exception exception) {
//                log.error("Error occurred {}", exception);
                throw exception;
            }
        }
    }
}
