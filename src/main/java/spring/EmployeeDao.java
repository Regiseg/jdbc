package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createEmployee(String name) {
        jdbcTemplate.update("INSERT INTO employees(emp_name) VALUES (?)", name);
    }

    public List<String> listEmployeeNames() {
        return jdbcTemplate.query("SELECT emp_name FROM employees ORDER BY emp_name",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("emp_name");
                    }
                });
    }
}
