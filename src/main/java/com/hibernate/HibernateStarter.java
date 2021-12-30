package com.hibernate;

import com.hibernate.entity.Birthday;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import com.hibernate.util.HibernateConfigurationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateStarter {
    private static final Logger logger = LoggerFactory.getLogger(HibernateStarter.class);
    public static void main(String[] args) throws SQLException {
        User user = User.builder()
                .userName("alexx@gmail.com")
                .firstName("Alex")
                .lastName("Alexeev")
                .birthDay(new Birthday(LocalDate.of(1989, 3, 10)))
                .role(Role.USER)
                .info("""
                        {
                        "name": "Alex",
                        "id": 15
                        }
                        """)
                .build();
        logger.info("The entity of User is a transient state, object {}", user);

//If you need to use annotations without hibernate.cfg.xml use the java code below
//configuration.addAnnotatedClass(User.class);

        try (SessionFactory sessionFactory = HibernateConfigurationUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                Transaction transaction = session1.beginTransaction();
                logger.trace("Transaction is created {}", transaction);
                session1.saveOrUpdate(user);
                logger.trace("User is in persistent state {}, session {}", user, transaction);
                session1.getTransaction().commit();
            } catch (Exception exception){
                logger.error("Error occurred {}",exception);
                throw exception;
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                user.setFirstName("Ollala");
                session2.merge(user);
//                session2.refresh(user);
                session2.getTransaction().commit();
            }catch (Exception exception) {
                logger.error("Error occurred {}", exception);
                throw exception;
            }
        }
    }
}
