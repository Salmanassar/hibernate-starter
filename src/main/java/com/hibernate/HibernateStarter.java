package com.hibernate;

import com.hibernate.entity.Birthday;
import com.hibernate.entity.PersonInfo;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import com.hibernate.util.HibernateConfigurationUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateStarter {
    public static void main(String[] args) throws SQLException {
        User user = User.builder()
                .userName("alexx@gmail.com")
                .personInfo(PersonInfo.builder()
                        .firstName("Alex")
                        .lastName("Alexeev")
                        .birthDay(new Birthday(LocalDate.of(1989, 3, 10)))
                        .build())
                        .role(Role.USER)
                        .info("""
                                {
                                "name": "Alex",
                                "id": 15
                                }
                                """)
                        .build();
        log.info("The entity of User is a transient state, object {}", user);

//If you need to use annotations without hibernate.cfg.xml use the java code below
//configuration.addAnnotatedClass(User.class);

        try (SessionFactory sessionFactory = HibernateConfigurationUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is created {}", transaction);
                session1.saveOrUpdate(user);
                log.trace("User is in persistent state {}, session {}", user, transaction);
                session1.getTransaction().commit();
            } catch (Exception exception) {
                log.error("Error occurred {}", exception);
                throw exception;
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                user.getPersonInfo().setFirstName("Ollala");
                session2.merge(user);
//                session2.refresh(user);
                session2.getTransaction().commit();
            } catch (Exception exception) {
                log.error("Error occurred {}", exception);
                throw exception;
            }
        }
    }
}
