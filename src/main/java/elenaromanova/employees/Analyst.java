package elenaromanova.employees;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst extends Employee {
    private int projectCount;
    private final int baseSalary = 2500;

    public int getBaseAmountOfProjects() {
        return baseAmountOfProjects;
    }

    private final int baseAmountOfProjects = 5;
    private final String analystRegExp = "projectCount=(?<projectCount>\\d+)";
    private final Pattern analystPattern = Pattern.compile(analystRegExp);
    public Analyst(String personText) {
        super(personText, "Analyst");
        Matcher analystMatcher = analystPattern.matcher(peopleMatcher.group("details"));
        if (analystMatcher.matches()) {
            projectCount = Integer.parseInt(analystMatcher.group("projectCount"));
        }
    }
    public int getSalary() {
        int projectBonus = Math.max(projectCount - baseAmountOfProjects, 0) * 300;
        return baseSalary + projectBonus;
    }
}

