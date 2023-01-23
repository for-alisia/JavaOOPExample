package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager implements Employee {
    private final String ROLE = "Manager";
    private String lastName;
    private String firstName;
    private LocalDate dob;

    private int orgSize;

    private int directReport;

    private final int BASE_SALARY = 3000;
    private final int BASE_ORG_SIZE = 60;

    private  final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private final String peopleRegExp = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePattern = Pattern.compile(peopleRegExp);

    private final String managerRegExp = "orgSize=(?<orgSize>\\d+),dr=(?<dr>\\d+)";
    private final Pattern managerPattern = Pattern.compile(managerRegExp);
    public Manager(String personText) {
        Matcher peopleMatcher = peoplePattern.matcher(personText);

        if (peopleMatcher.matches()) {
            lastName = peopleMatcher.group("lastName");
            firstName = peopleMatcher.group("firstName");
            dob = LocalDate.from(dtFormatter.parse(peopleMatcher.group("birthdate")));

            Matcher managerMatcher = managerPattern.matcher(peopleMatcher.group("details"));

            if (managerMatcher.matches()) {
                orgSize = Integer.parseInt(managerMatcher.group("orgSize"));
                directReport = Integer.parseInt(managerMatcher.group("dr"));
            }
        }
    }

    public int getSalary() {
        int orgSizeBonus = Math.max(orgSize - BASE_ORG_SIZE, 0) * 10;
        return BASE_SALARY + orgSizeBonus + directReport * 25;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s): %s", firstName, lastName, ROLE, moneyFormat.format(getSalary()));
    }
}
