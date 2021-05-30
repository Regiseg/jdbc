package jdbc;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindByIdMain {

    public void selectNameByID(DataSource ds, long id) {
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT emp_name FROM employees WHERE id = ?;")
        ) {
            ps.setLong(1, id);

            selectNameByPrepareStatement(ps);

        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot connection");
        }
    }

    private void selectNameByPrepareStatement(PreparedStatement ps) {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String name = rs.getString("emp_name");
                System.out.println(name);
            }
            throw new IllegalArgumentException("Wrong id");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot query", se);
        }
    }

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

        new FindByIdMain().selectNameByID(dataSource, 2);

    }
}
