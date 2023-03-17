package elenaromanova.employees;

import java.util.regex.Matcher;

public class StreamsExample {
    public static void main(String[] args) {
        String people = """
                Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}
                Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}
                Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}
                Stephen, Rider, 11/02/1976, Manager, {orgSize=150,dr=5}
                Anton, Lids, 11/02/1976, Manager, {orgSize=180,dr=8}
                John, Smith, 23/09/1999, Analyst, {projectCount=5}
                William, Carter, 23/09/1999, Analyst, {projectCount=7}
                Alex, Green, 23/09/1999, Analyst, {projectCount=10}
                Max, Jonas, 10/01/1987, Developer, {locpd=2000,yoe=10,iq=140}
                Lisa, Doll, 22/02/1987, Developer, {locpd=1300,yoe=3,iq=105}
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                """;
        // .lines() - creates a stream of strings
        people
            .lines()
            .map(Employee::createEmployee) //.map(s -> Employee.createEmployee(s))
            .forEach(System.out::println); // .forEach((s) -> System.out.println(s))
    }
}
