package elenaromanova.employees;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager extends Employee {
    private final String ROLE = "Manager";
    private int orgSize;
    private int directReport;
    private final int BASE_SALARY = 3000;
    private final int BASE_ORG_SIZE = 60;
    private final String managerRegExp = "orgSize=(?<orgSize>\\d+),dr=(?<dr>\\d+)";
    private final Pattern managerPattern = Pattern.compile(managerRegExp);
    public Manager(String personText) {
        super(personText);
        Matcher managerMatcher = managerPattern.matcher(peopleMatcher.group("details"));
        if (managerMatcher.matches()) {
            orgSize = Integer.parseInt(managerMatcher.group("orgSize"));
            directReport = Integer.parseInt(managerMatcher.group("dr"));
        }
    }
    public int getSalary() {
        int orgSizeBonus = Math.max(orgSize - BASE_ORG_SIZE, 0) * 10;
        return BASE_SALARY + orgSizeBonus + directReport * 25;
    }
}
