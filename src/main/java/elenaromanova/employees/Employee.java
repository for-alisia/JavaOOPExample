package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee implements IEmployee {
    protected String role = "Employee";
    protected final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    public final static String PEOPLE_REG_EXP = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
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
                case "WithLambda" -> () -> 0;
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

    // NESTED CLASS
    // We also have inner classes (no static, they have access to fields of upper class not via inheritance)
    // it means we can not extend them from outer class, but they will have access to its methods and fields
    private static final class BaseEmployee extends Employee {
        @Override
        public int getSalary() {
            return 0;
        }
    }
}
