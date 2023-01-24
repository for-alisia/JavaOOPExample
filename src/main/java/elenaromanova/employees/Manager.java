package elenaromanova.employees;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager extends Employee implements Flyer {
    // Composition to make Manager also a Pilot
    // In Java we can't extend from multiple classes
    // This is a workaround how to do it
    // 1. extract interface from the second class
    // 2. crete field of type of this interface
    // 3. Delegate methods
    private Pilot pilot = new Pilot(1000, true);
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

    // Delegated methods (Ctrl + N -> Delegate methods)
    public void fly() {
        pilot.fly();
    }

    public int getHoursFlown() {
        return pilot.getHoursFlown();
    }

    public void setHoursFlown(int hoursFlown) {
        pilot.setHoursFlown(hoursFlown);
    }

    public boolean isIfr() {
        return pilot.isIfr();
    }

    public void setIfr(boolean ifr) {
        pilot.setIfr(ifr);
    }
}
