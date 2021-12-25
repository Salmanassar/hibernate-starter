package tests;

import com.hibernate.entity.User;
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