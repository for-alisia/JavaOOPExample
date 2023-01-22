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
        String regEx = "(?<firstName>\\w+),\\s*(?<lastName>\\w+),\\s*(?<birthdate>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)\\n";

        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(people);

        int totalSalary = 0;
        while(mat.find()) {
            totalSalary += switch(mat.group("role")) {
                case "Manager" -> 3000;
                case "Analyst" -> 2500;
                case "Developer" -> 2800;
                default -> 0;
            };
        }

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.printf("Total salary is %s%n", currencyInstance.format(totalSalary));
    }
}
