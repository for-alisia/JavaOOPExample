package elenaromanova.employees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void testNameToSalary() {
        Main main = new Main();
        main.main(new String[0]);

        int salary = main.getSalary("Lisa");

        assertEquals(4050, salary);
    }

    @Test
    public void testBadNameToSalary() {
        Main main = new Main();
        main.main(new String[0]);

        int salary = main.getSalary("XXX");

        assertEquals(-1, salary);
    }
}