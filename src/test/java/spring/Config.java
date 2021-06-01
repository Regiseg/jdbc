package spring;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class Config {

    @Bean
    public Flyway flyway() {
        return Flyway.configure().dataSource(dataSource()).load();
    }

    @Bean
    public DataSource dataSource() {
        MariaDbDataSource dataSource;

        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");

        } catch (SQLException sqlException) {
            throw new IllegalStateException("Connection failed!", sqlException);
        }
        return dataSource;
    }

    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDao(dataSource());
    }
}
