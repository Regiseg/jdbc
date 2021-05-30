package jdbc;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectEmployeesMain {

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
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT emp_name FROM employees;")
        ) {
            List<String> names = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("emp_name");
                names.add(name);
            }
            System.out.println(names);
        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot connection");
        }
    }
}
