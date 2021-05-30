package jdbc;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeesMain {

    public static void main(String[] args) {

        MariaDbDataSource dataSource;

        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");

        } catch (SQLException sqlException) {
            throw new IllegalStateException("Connection failed!", sqlException);
        }

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO employees(emp_name) VALUES (?)")
        ) {
            ps.setString(1, "Sziszi");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot insert");
        }
    }
}
