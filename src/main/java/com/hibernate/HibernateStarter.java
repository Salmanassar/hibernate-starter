package com.hibernate;

import com.hibernate.entity.Birthday;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import com.hibernate.util.HibernateConfigurationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateStarter {
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

//If you need to use annotations without hibernate.cfg.xml use the java code below
//configuration.addAnnotatedClass(User.class);

        try (SessionFactory sessionFactory = HibernateConfigurationUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                session1.saveOrUpdate(user);
                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                user.setFirstName("Ollala");
                session2.merge(user);
//                session2.refresh(user);
                session2.getTransaction().commit();
            }
        }
    }
}
