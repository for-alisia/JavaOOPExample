package elenaromanova.employees;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager extends Employee {
    private int orgSize;
    private int directReport;
    private final int baseSalary = 3000;
    private final int baseOrgSize = 60;
    private final String managerRegExp = "orgSize=(?<orgSize>\\d+),dr=(?<dr>\\d+)";
    private final Pattern managerPattern = Pattern.compile(managerRegExp);
    public Manager(String personText) {
        super(personText, "Manager");
        Matcher managerMatcher = managerPattern.matcher(peopleMatcher.group("details"));
        if (managerMatcher.matches()) {
            orgSize = Integer.parseInt(managerMatcher.group("orgSize"));
            directReport = Integer.parseInt(managerMatcher.group("dr"));
        }
    }
    public int getSalary() {
        int orgSizeBonus = Math.max(orgSize - baseOrgSize, 0) * 10;
        return baseSalary + orgSizeBonus + directReport * 25;
    }
}
