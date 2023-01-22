package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst {
    private final String ROLE = "Analyst";
    private String lastName;
    private String firstName;
    private LocalDate dob;

    private int projectCount;

    private final int BASE_SALARY = 2500;
    private final int BASE_AMOUNT_OF_PROJECTS = 5;

    private  final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private final String peopleRegExp = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePattern = Pattern.compile(peopleRegExp);

    private final String analystRegExp = "projectCount=(?<projectCount>\\d+)";
    private final Pattern analystPattern = Pattern.compile(analystRegExp);
    public Analyst(String personText) {
        Matcher peopleMatcher = peoplePattern.matcher(personText);

        if (peopleMatcher.matches()) {
            lastName = peopleMatcher.group("lastName");
            firstName = peopleMatcher.group("firstName");
            dob = LocalDate.from(dtFormatter.parse(peopleMatcher.group("birthdate")));

            Matcher analystMatcher = analystPattern.matcher(peopleMatcher.group("details"));

            if (analystMatcher.matches()) {
                projectCount = Integer.parseInt(analystMatcher.group("projectCount"));
            }
        }
    }

    public int getSalary() {
        int projectBonus = Math.max(projectCount - BASE_AMOUNT_OF_PROJECTS, 0) * 300;
        return BASE_SALARY + projectBonus;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s): %s", firstName, lastName, ROLE, moneyFormat.format(getSalary()));
    }
}

