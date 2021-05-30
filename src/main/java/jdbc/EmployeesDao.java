package jdbc;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDao {

    private DataSource dataSource;

    public EmployeesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long createEmployee(String name) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO employees(emp_name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, name);
            ps.executeUpdate();

            return getIdByStatement(ps);

        } catch (SQLException se) {
            throw new IllegalStateException("Cannot insert", se);
        }
    }

    public void createEmployees(List<String> names) {
        try (
                Connection conn = dataSource.getConnection()
        ) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO employees(emp_name) VALUES (?)")
            ) {
                for (String name : names) {
                    if (name.startsWith("x")) {
                        throw new IllegalArgumentException("Invalid name");
                    }
                    ps.setString(1, name);
                    ps.executeUpdate();
                }
                conn.commit();

            } catch (IllegalArgumentException iae) {
                conn.rollback();
            }

        } catch (SQLException sqlException) {
            throw new IllegalStateException("Cannot insert", sqlException);
        }

    }

    private long getIdByStatement(PreparedStatement ps) {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new IllegalStateException("Cannot get id");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot get id", se);
        }
    }

    public List<String> listEmployeesNames() {
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

            return names;

        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot connection");
        }
    }

    public String findEmployeeNameById(long id) {

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT emp_name FROM employees WHERE id = ?;")
        ) {
            ps.setLong(1, id);

            return selectNameByPrepareStatement(ps);

        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot connection");
        }
    }

    private String selectNameByPrepareStatement(PreparedStatement ps) {

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String name = rs.getString("emp_name");
                return name;
            }

            throw new IllegalArgumentException("Wrong id");

        } catch (SQLException se) {
            throw new IllegalStateException("Cannot query", se);
        }
    }
}
