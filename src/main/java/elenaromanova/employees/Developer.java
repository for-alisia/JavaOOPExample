package elenaromanova.employees;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Developer extends Employee {
    private final String ROLE = "Developer";
    private final int BASE_SALARY = 2800;
    private int linesOfCode;
    private int yearsOfExperience;
    private int iq;
    private final int BASE_AMOUNT_OF_LINES = 1150;
    private final int YEARS_BONUS = 200;
    private final int IQ_BASE = 100;
    private final int IQ_BONUS = 100;
    private final String devRegExp = "locpd=(?<locpd>\\d+),yoe=(?<yoe>\\d+),iq=(?<iq>\\d+)";
    private final Pattern devPattern = Pattern.compile(devRegExp);
    public Developer(String personText) {
        super(personText);
        Matcher devMatcher = devPattern.matcher(peopleMatcher.group("details"));
        if (devMatcher.matches()) {
            linesOfCode = Integer.parseInt(devMatcher.group("locpd"));
            yearsOfExperience = Integer.parseInt(devMatcher.group("yoe"));
            iq = Integer.parseInt(devMatcher.group("iq"));
        }
    }
    public int getSalary() {
        int linesAdd = Math.max(linesOfCode - BASE_AMOUNT_OF_LINES, 0);
        int yearsAdd = yearsOfExperience * YEARS_BONUS;
        int iqAdd = Math.max(iq - IQ_BASE, 0) * IQ_BONUS;
        return BASE_SALARY + linesAdd + yearsAdd + iqAdd;
    }
}
