package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Developer {
    private final String ROLE = "Developer";
    private String lastName;
    private String firstName;
    private LocalDate dob;

    private int linesOfCode;
    private int yearsOfExperience;
    private int iq;

    private final int BASE_SALARY = 2800;
    private final int BASE_AMOUNT_OF_LINES = 1150;
    private final int YEARS_BONUS = 200;
    private final int IQ_BASE = 100;
    private final int IQ_BONUS = 100;

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private final String peopleRegExp = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePattern = Pattern.compile(peopleRegExp);

    private final String devRegExp = "locpd=(?<locpd>\\d+),yoe=(?<yoe>\\d+),iq=(?<iq>\\d+)";
    private final Pattern devPattern = Pattern.compile(devRegExp);
    public Developer(String personText) {
        Matcher peopleMatcher = peoplePattern.matcher(personText);

        if (peopleMatcher.matches()) {
            lastName = peopleMatcher.group("lastName");
            firstName = peopleMatcher.group("firstName");
            dob = LocalDate.from(dtFormatter.parse(peopleMatcher.group("birthdate")));

            Matcher devMatcher = devPattern.matcher(peopleMatcher.group("details"));

            if (devMatcher.matches()) {
                linesOfCode = Integer.parseInt(devMatcher.group("locpd"));
                yearsOfExperience = Integer.parseInt(devMatcher.group("yoe"));
                iq = Integer.parseInt(devMatcher.group("iq"));
            }
        }
    }

    public int getSalary() {
        int linesAdd = Math.max(linesOfCode - BASE_AMOUNT_OF_LINES, 0);
        int yearsAdd = yearsOfExperience * YEARS_BONUS;
        int iqAdd = Math.max(iq - IQ_BASE, 0) * IQ_BONUS;
        return BASE_SALARY + linesAdd + yearsAdd + iqAdd;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s): %s", firstName, lastName, ROLE, moneyFormat.format(getSalary()));
    }
}
