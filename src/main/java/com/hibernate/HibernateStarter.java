package com.hibernate;

import com.hibernate.entity.Birthday;
import com.hibernate.coverter.BirthdayConvertor;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateStarter {
    public static void main(String[] args) throws SQLException {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConvertor(),true);
        configuration.configure("hibernate.cfg.xml");
//If you need to use annotations without hibernate.cfg.xml use the java code below
//configuration.addAnnotatedClass(User.class);
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            User user = User.builder()
                    .userName("alexx@gmail.com")
                    .firstName("Alex")
                    .lastName("Alexeev")
                    .birthDay(new Birthday(LocalDate.of(1989, 3, 10)))
                    .role(Role.USER)
                    .build();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
    }
}
