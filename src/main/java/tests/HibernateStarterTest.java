package tests;

import com.hibernate.entity.*;
import com.hibernate.util.HibernateConfigurationUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateStarterTest {
    @Test
    public void companyLocaleInfoTest() {
        try (var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var company = session.get(Company.class, 7L);
//            company.getLocaleInfos().add(LocaleInfo.of("RU", "Новая компания"));
//            company.getLocaleInfos().add(LocaleInfo.of("EN", "New company"));
            System.out.println(company.getLocaleInfos());
            session.getTransaction().commit();
        }
    }

    @Test
    public void manyToManyTest() {
        try (var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var user = session.get(User.class, 1L);
            var chat = Chat.builder()
                    .name("Bob Mary")
                    .build();
            user.addChat(chat);
            session.save(chat);
            session.getTransaction().commit();
        }
    }

    @Test
    public void oneToOneTest() {
        try (var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var user = User.builder()
                    .userName("serega.test5@net.com")
                    .build();
            var profile = Profile.builder()
                    .street("Shwejka 44")
                    .language("tr")
                    .build();
            profile.setUser(user);
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Test
    public void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var company = Company.builder()
                .name("Yandex")
                .build();
        var user = User.builder()
                .userName("sveta@net.com")
                .build();
        company.addUser(user);
        session.save(company);
        session.getTransaction().commit();
    }

    @Test
    public void oneToMany() {
        @Cleanup var sessionFactory = HibernateConfigurationUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = session.get(Company.class, 5L);
        System.out.println(" ");
        session.getTransaction().commit();
    }

    @Test
    public void checkReflectionAPI() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();
        String sql = """
                insert
                 into 
                 %s (%s)
                 values 
                 (%s)""";
        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());
        Field[] fields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name).orElse(field.getName()))
                .collect(Collectors.joining(", "));
        String columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        System.out.println(sql.formatted(tableName, columnNames, columnValues));
        Connection connection = null;
        PreparedStatement ps = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field field : fields) {
            field.setAccessible(true);
            ps.setObject(1, field.get(user));
        }
    }
}