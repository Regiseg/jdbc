package spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
class EmployeeDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void testCreateThanList(){
        employeeDao.createEmployee("John Doe");

    }

}