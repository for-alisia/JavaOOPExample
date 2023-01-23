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
                Max, Jonas, 10/01/1987, Developer, {locpd=2000,yoe=10,iq=140}
                Lisa, Doll, 22/02/1987, Developer, {locpd=1300,yoe=3,iq=105}
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                """;
        String peopleRegExp = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
        Pattern peoplePattern = Pattern.compile(peopleRegExp);
        Matcher peopleMatcher = peoplePattern.matcher(people);

        int totalSalary = 0;
        while(peopleMatcher.find()) {
            String row = peopleMatcher.group();
            IEmployee employee =  switch(peopleMatcher.group("role")) {
                case "Manager" -> new Manager(row);
                case "Analyst" -> new Analyst(row);
                case "Developer" -> new Developer(row);
                default -> null;
            };
            int salary = employee == null ? 0 : employee.getSalary();
            if (employee != null) {
                System.out.println(employee.toString());
            }
            totalSalary += salary;
        }

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.printf("Total salary is %s%n", currencyInstance.format(totalSalary));
    }
}
