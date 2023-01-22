package elenaromanova.employees;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String people = """
                Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}
                Stephen, Rider, 11/02/1976, Manager, {orgSize=150,dr=5}
                Anton, Lids, 11/02/1976, Manager, {orgSize=180,dr=8}
                John, Smith, 23/09/1999, Analyst, {projectCount=5}
                William, Carter, 23/09/1999, Analyst, {projectCount=7}
                Alex, Green, 23/09/1999, Analyst, {projectCount=10}
                Max, Jonas, 16/11/1987, Developer, {locpd=2000,yoe=10,iq=140}
                Lisa, Doll, 16/11/1987, Developer, {locpd=1300,yoe=3,iq=105}
                Andrea, Black, 16/11/1987, Developer, {locpd=1600,yoe=7,iq=100}
                """;
        String peopleRegExp = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
        Pattern peoplePattern = Pattern.compile(peopleRegExp);
        Matcher peopleMatcher = peoplePattern.matcher(people);

        String progRegExp = "locpd=(?<locpd>\\d+),yoe=(?<yoe>\\d+),iq=(?<iq>\\d+)";
        Pattern coderPat = Pattern.compile(progRegExp);

        String anRegExp = "projectCount=(?<projectCount>\\d+)";
        Pattern anPat = Pattern.compile(anRegExp);

        String managerRegExp = "orgSize=(?<orgSize>\\d+),dr=(?<dr>\\d+)";
        Pattern managerPat = Pattern.compile(managerRegExp);


        int totalSalary = 0;
        while(peopleMatcher.find()) {
            String details = peopleMatcher.group("details");
            String firstName = peopleMatcher.group("firstName");
            String lastName = peopleMatcher.group("lastName");
            int empSalary =  switch(peopleMatcher.group("role")) {
                case "Manager" -> {
                    int salary = 3000;
                    Matcher manMat = managerPat.matcher(details);

                    if (manMat.matches()) {
                        int orgSize = Integer.parseInt(manMat.group("orgSize"));
                        int dr = Integer.parseInt(manMat.group("dr"));

                        salary += orgSize * dr;
                    }

                    yield salary;
                }
                case "Analyst" -> {
                    int salary = 2500;
                    Matcher anMat = anPat.matcher(details);
                    if (anMat.matches()) {
                        int projectCount = Integer.parseInt(anMat.group("projectCount"));

                        salary += projectCount * 100;
                    }

                    yield salary;
                }
                case "Developer" -> {
                    int salary = 2800;
                    Matcher coderMat = coderPat.matcher(details);
                    if (coderMat.matches()) {
                        int locpd = Integer.parseInt(coderMat.group("locpd"));
                        int yoe = Integer.parseInt(coderMat.group("yoe"));
                        int iq = Integer.parseInt(coderMat.group("iq"));

                        salary += locpd * yoe * iq;
                    };

                    yield salary;
                }
                default -> {
                    yield 0;
                }
            };

            totalSalary += empSalary;
            System.out.printf("%s, %s: %s%n", firstName, lastName, NumberFormat.getCurrencyInstance(Locale.US).format(empSalary));
        }

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.printf("Total salary is %s%n", currencyInstance.format(totalSalary));
    }
}
