package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long createEmployee(String name) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO employees(emp_name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

    public List<String> listEmployeeNames() {
        return jdbcTemplate.query("SELECT emp_name FROM employees ORDER BY emp_name",
                (rs, rowNum) -> rs.getString("emp_name"));
    }

    public String findEmployeeNameById(long id) {
        return jdbcTemplate.queryForObject("SELECT emp_name FROM employees WHERE id=?",
                new Object[]{id}, (rs, rowNum) -> rs.getString("emp_name"));
    }
}
