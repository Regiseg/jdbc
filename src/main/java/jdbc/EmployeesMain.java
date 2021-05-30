package jdbc;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.List;

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

        EmployeesDao employeesDao = new EmployeesDao(dataSource);

        employeesDao.createEmployee("Bibi");

        List<String> names = employeesDao.listEmployeesNames();

        System.out.println(names);

        String name = employeesDao.findEmployeeNameById(2);

        System.out.println(name);
    }
}
