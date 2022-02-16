package hibernate;

import com.hibernate.entity.*;
import com.hibernate.util.HibernateConfigurationUtil;
import hibernate.util.HibernateUtilTest;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

class HibernateStarterTest {

    @Test
    public void memoryDatabase() {
        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var company = Company.builder()
                    .name("Sega")
                    .build();
            session.save(company);
            var programmer = Programmer.builder()
                    .userName("ivan@gmail.com")
                    .language(Language.JAVA)
                    .company(company)
                    .build();
            session.save(programmer);
            var maneger = Manager.builder()
                    .userName("sergay@gmail.com")
                    .company(company)
                    .projectName("High Performance")
                    .build();
            session.save(maneger);
            session.flush();
            var programmer1 = session.get(Programmer.class, 1L);
            var manager1 = session.get(User.class, 2L);
            session.clear();
            session.getTransaction().commit();
        }
    }

    @Test
    public void companyLocaleInfoTest() {
        try (var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var company = session.get(Company.class, 7L);
//            company.getUsers().forEach((k, v) -> System.out.println(v));
            session.getTransaction().commit();
        }
    }

//    @Test
//    public void manyToManyTest() {
//        try (var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            var user = session.get(User.class, 1L);
//            var chat = Chat.builder()
//                    .name("Bob Mary")
//                    .build();
//            user.addChat(chat);
//            session.save(chat);
//            session.getTransaction().commit();
//        }
//    }

    @Test
    public void oneToMany() {
        @Cleanup var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = session.get(Company.class, 5L);
        System.out.println(" ");
        session.getTransaction().commit();
    }
}