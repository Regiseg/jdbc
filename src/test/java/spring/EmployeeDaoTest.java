package spring;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
class EmployeeDaoTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    private EmployeeDao employeeDao;

    @BeforeEach
    public void init() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void testCreateThanList() {
        employeeDao.createEmployee("John Doe");
        List<String> employees = employeeDao.listEmployeeNames();
        assertEquals(List.of("John Doe"), employees);

        employeeDao.createEmployee("Bibi");
        assertEquals(2, employeeDao.listEmployeeNames().size());
    }

    @Test
    void testThanFind() {
        long id = employeeDao.createEmployee("john doe");
        String name = employeeDao.findEmployeeNameById(id);
        assertEquals("john doe", name);
    }


}