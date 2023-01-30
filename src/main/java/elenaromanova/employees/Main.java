package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

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
        Matcher peopleMatcher = Employee.PATTERN.matcher(people);

        double totalSalary = 0;
        // Collections - List (ArrayList)
        List<IEmployee> employees = new ArrayList<>();
        while(peopleMatcher.find()) {
            String row = peopleMatcher.group();
            Flyer flyer = new Manager(row);
            flyer.fly();
            IEmployee employee =  Employee.createEmployee(peopleMatcher.group());
            double salary = employee.getSalary();
            // Adding an object to a collections (only Objects are allowed for collections)
            employees.add(employee);
            totalSalary += salary;
            // How we can define if object belongs to a certain class
            // getClass will check to a certain class only
            // instanceof checks the whole hierarchy chain
            // emp instanceof Developer - true and emp instanceof Employee - true
            if (employee.getClass().equals(Developer.class)) {
                // Here we need to cast that employee is a Developer
                // Developer dev = (Developer) employee;
                System.out.println(((Developer) employee).getIq());
            } else if (employee instanceof Manager) {
                System.out.println("Manager");
            } else if (employee instanceof Analyst analyst) {
                // With more modern (16+) variation of instanceof we can cast it immediately to a variable
                System.out.println(analyst.getBaseAmountOfProjects());
            }
        }

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.printf("Total salary is %s%n", currencyInstance.format(totalSalary));

        // Looping through the collection
        for (IEmployee employee : employees) {
            System.out.println(employee.toString());
        }

        RecordExample myRecord = new RecordExample("John", "Doe", LocalDate.of(1900, 8, 12));

        System.out.println(myRecord.firstName());

//        Developer newDev = new Developer("");
//
//        System.out.println(newDev.getAmountOfInterviews());
    }
}
