package elenaromanova.employees;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Developer extends Employee implements Interviewer {
    private final int baseSalary = 2800;
    private int linesOfCode;
    private int yearsOfExperience;

    public int getIq() {
        return iq;
    }

    public void setIq(int iq) {
        this.iq = iq;
    }

    private int iq;
    private final int baseAmountOfLines = 1150;
    private final int yearsBonus = 200;
    private final int iqBase = 100;
    private final int iqBonus = 100;
    private final String devRegExp = "locpd=(?<locpd>\\d+),yoe=(?<yoe>\\d+),iq=(?<iq>\\d+)";
    private final Pattern devPattern = Pattern.compile(devRegExp);
    public Developer(String personText) {
        super(personText, "Developer");
        Matcher devMatcher = devPattern.matcher(peopleMatcher.group("details"));
        if (devMatcher.matches()) {
            linesOfCode = Integer.parseInt(devMatcher.group("locpd"));
            yearsOfExperience = Integer.parseInt(devMatcher.group("yoe"));
            iq = Integer.parseInt(devMatcher.group("iq"));
        }
    }
    public int getSalary() {
        int linesAdd = Math.max(linesOfCode - baseAmountOfLines, 0);
        int yearsAdd = yearsOfExperience * yearsBonus;
        int iqAdd = Math.max(iq - iqBase, 0) * iqBonus;
        return baseSalary + linesAdd + yearsAdd + iqAdd;
    }
}
