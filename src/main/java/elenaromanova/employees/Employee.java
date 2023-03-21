package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee implements IEmployee {
    protected String role = "Employee";
    protected final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    public final static String PEOPLE_REG_EXP = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})";
    public final static Pattern PATTERN = Pattern.compile(PEOPLE_REG_EXP);
    protected Matcher peopleMatcher;
    protected String lastName;
    protected String firstName;
    protected LocalDate dob;
    protected DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected Employee(String personText, String role) {
        this.role = role;
        setAttributes(personText);
    }

    protected Employee() {
        lastName = "N/A";
        firstName = "N/A";
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return lastName.equals(employee.lastName) && firstName.equals(employee.firstName) && dob.equals(employee.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, dob);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setAttributes(String personText) {
        peopleMatcher = Employee.PATTERN.matcher(personText);
        if (peopleMatcher.matches()) {
            lastName = peopleMatcher.group("lastName");
            firstName = peopleMatcher.group("firstName");
            dob = LocalDate.from(dtFormatter.parse(peopleMatcher.group("birthdate")));
        }
    }

    // Factory
    public static IEmployee createEmployee(String empText) {
        Matcher peopleMatcher = Employee.PATTERN.matcher(empText);
        if (peopleMatcher.matches()) {
            return switch(peopleMatcher.group("role")) {
                case "Manager" -> new Manager(empText);
                case "Analyst" -> new Analyst(empText);
                case "Developer" -> new Developer(empText);
                // case "WithLambda" -> () -> 0; - not possible if interface implements more than 1 method
                default -> new BaseEmployee();
            };
        }

        // Nested anonymous class (as example, we can use BaseEmployee here)
        // No need to set up modifications on it - we can't access it in other places
        // Also can be created out of interface (IEmployee here)
        // Also we can have local nested classes
        // They created under local scope
        return new Employee() {
            @Override
            public int getSalary() {
                return 0;
            }

            @Override
            public int compareTo(IEmployee o) {
                return 0;
            }
        };
    }

    @Override
    public abstract int getSalary();

    public double getBonus() {
        return getSalary() * 0.1;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s): %s (bonus: %s)", firstName, lastName, role, moneyFormat.format(getSalary()), moneyFormat.format(getBonus()));
    }

    @Override
    public int compareTo(IEmployee o) {
        if (o instanceof Employee emp) {
            String fullName = lastName.concat(firstName);
            return fullName.compareTo(emp.lastName.concat(emp.firstName));
        }
        return 0;
    }

    // NESTED CLASS
    // We also have inner classes (no static, they have access to fields of upper class not via inheritance)
    // it means we can not extend them from outer class, but they will have access to its methods and fields
    private static final class BaseEmployee extends Employee {
        @Override
        public int getSalary() {
            return 0;
        }

        @Override
        public int compareTo(IEmployee o) {
            return 0;
        }
    }
}
