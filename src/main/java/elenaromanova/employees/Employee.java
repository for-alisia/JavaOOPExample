package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee implements IEmployee {
    protected String role = "Employee";
    protected final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private final String peopleRegExp = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    protected final Pattern peoplePattern = Pattern.compile(peopleRegExp);
    protected Matcher peopleMatcher;
    protected String lastName;
    protected String firstName;
    protected LocalDate dob;
    protected DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Employee(String personText, String role) {
        this.role = role;
        setAttributes(personText);
    }

    public Employee(String personText) {
        setAttributes(personText);
    }

    private void setAttributes(String personText) {
        peopleMatcher = peoplePattern.matcher(personText);
        if (peopleMatcher.matches()) {
            lastName = peopleMatcher.group("lastName");
            firstName = peopleMatcher.group("firstName");
            dob = LocalDate.from(dtFormatter.parse(peopleMatcher.group("birthdate")));
        }
    }

    @Override
    public int getSalary() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s): %s", firstName, lastName, role, moneyFormat.format(getSalary()));
    }
}
