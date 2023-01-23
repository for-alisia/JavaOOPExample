package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst extends Employee {
    private final String ROLE = "Analyst";
    private int projectCount;
    private final int BASE_SALARY = 2500;
    private final int BASE_AMOUNT_OF_PROJECTS = 5;
    private final String analystRegExp = "projectCount=(?<projectCount>\\d+)";
    private final Pattern analystPattern = Pattern.compile(analystRegExp);
    public Analyst(String personText) {
        super(personText);
        Matcher analystMatcher = analystPattern.matcher(peopleMatcher.group("details"));
        if (analystMatcher.matches()) {
            projectCount = Integer.parseInt(analystMatcher.group("projectCount"));
        }
    }
    public int getSalary() {
        int projectBonus = Math.max(projectCount - BASE_AMOUNT_OF_PROJECTS, 0) * 300;
        return BASE_SALARY + projectBonus;
    }
}

