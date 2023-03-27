package elenaromanova.employees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
                Andrea, Hack, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                Andrea, Black, 30/03/1987, Developer, {locpd=1500,yoe=5,iq=85}
                """;
        // .lines() - creates a stream of strings
        int totalSalary = people
            .lines()
            .map(Employee::createEmployee) //.map(s -> Employee.createEmployee(s))
            .sorted() // natural order -> relays on implementation os compareTo (comparable interface)
            .sorted(Comparator.comparingInt(IEmployee::getSalary)) // custom Comparator
            .map(e ->(Employee) e) // To get access to firstName we need to cast IEmployee to Employee
            .sorted(Comparator // another way to sort by firstName+ lastName + salary
                .comparing(Employee::getFirstName) // we can use multiple comparing one by one
                .thenComparing(Employee::getLastName)
                .thenComparingInt(Employee::getSalary)
                .reversed()) // Reverse the order
            .distinct() // to remove duplicates
            // .collect(Collectors.toSet()) - allows to put data in a Set (also to a List and other collections),
            // .stream() - if we need to continue a pipe after collect (collect returns a collection)
            .filter(Predicate.not((e) -> e.lastName.equals("Doe")))
            .filter(e -> e instanceof Developer)
            .filter(e -> e.getSalary() > 4500)
            .mapToInt(StreamsExample::getEmpSalaryAndPrint)
            .sum(); // average(),
        System.out.println(totalSalary);

        flatteringStreams(people);
    }

    private static int getEmpSalaryAndPrint(IEmployee emp) {
        System.out.println(emp);

        return emp.getSalary();
    }

    static void reducersExamples(String people) {
        IntStream empSalary = people.lines()
                .map(Employee::createEmployee)
                .mapToInt(IEmployee::getSalary);

        OptionalDouble averSalary = empSalary.average();
        // OptionalInt maxSal = empSalary.max();
        // Long totlCount = empSalary.count();
        // Classic reduce for sum
        // long sum = empSalary.reduce(0, (a, b) -> a + b);
        // Classic reduce for max
        // int maxSal = empSalary.reduce(0, Math::max);
        // reduce(() -> {}) - will return Optional ach time (as accumulator is not provided)

        // Safe work with doubles
        System.out.println(averSalary.orElse(0));

        // With strings
        // If we have 1 item in a stream, it will be returned without being processed
        Optional<String> longStr = Stream.of("Tom", "Ann", "Sam")
                .reduce((a, b) -> a.concat("_").concat(b));
        System.out.println(longStr.orElse("N/A"));
    }

    private static void alternativeFilters(String people) {
        Predicate<String> stringPredicate = s -> s.contains("Smith");
        Predicate<IEmployee> iEmployeePredicate = e -> e instanceof Developer;
        Predicate<IEmployee> salaryPredicate = e -> e.getSalary() > 5000;
        people
            .lines()
            .filter(s -> !s.contains("Analyst"))
            .filter(stringPredicate.negate()) // with extra variable
            .map(Employee::createEmployee)
            .filter(iEmployeePredicate.negate().and(salaryPredicate)) // combining conditions
            .map(e ->(Employee) e);
    }

    private static void matchersExample(String people) {
        boolean allGetsBigSalary = people
                                .lines()
                                .map(Employee::createEmployee)
                                .allMatch(e -> e.getSalary() > 3000); // allMatch is last operation, returns boolean

        // short circuit (stops on first match) - any and none as well
        boolean anyGetsBigSalary = people
                                .lines()
                                .map(Employee::createEmployee)
                                .anyMatch(e -> e.getSalary() > 3000); // boolean, last operation

        boolean noneGetsBigSalary = people
                                .lines()
                                .map(Employee::createEmployee)
                                .noneMatch(e -> e.getSalary() > 3000);

        System.out.println(allGetsBigSalary);
        Optional<IEmployee> first = people
                                    .lines()
                                    .map(Employee::createEmployee)
                                    .findFirst(); // can be findAny - no order in this case
        System.out.println(first.map(IEmployee::getSalary).orElse(0)); // how to extract data from Optional

    }

    private static void flatteringStreams(String people) {
        people
            .lines()
            .map(Employee::createEmployee)
            .map((e) -> (Employee) e)
            .map(Employee::getFirstName)
            .map(String::toLowerCase)
            .map(e -> e.split(""))
            .flatMap(Arrays::stream) // from stream of streams -> one combined stream
            .distinct()
            .sorted()
            .forEach(System.out::println);
    }

    private void createStreamFromFile() {
        // Reading data from file
        try {
            Files
                .lines(Path.of("/Users/alisia/Projects/JavaProjects/basicJava/examplesOOP/src/main/java/elenaromanova/employees/employees.txt"))
                .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSimpleStream() {
        // We can create a stream from any Collection (using .stream() method)
        Collection<String> myList = List.of("one", "two", "three");

        myList
                .stream()
                .map(String::hashCode)
                .map(Integer::toHexString)
                .forEach(System.out::println);

        // Creation from Stream static method
        Stream
            .of("four", "five", "six")
            .map(String::toUpperCase)
            .forEach(System.out::println);

        // Without nulls
        String myStr = null;
        Stream.ofNullable(myStr).forEach(System.out::println);

        // Empty
        Stream.empty();

        // Let's use Objects (for simplicity we will use Records, but it can be Classes)
        record Car(String make, String model){}

        Stream
            .of(new Car("Ford", "Aqua"), new Car("BMW", "i3"))
            .filter(car -> car.make.equals("BMW"))
            .forEach(System.out::println);
    }

    private void createStreamFromArray() {
        String[] names = {"Aron", "Olek", "Anna"};
        Arrays.stream(names).map(String::toUpperCase).forEach(System.out::println);
    }

    private void intStreamExamples() {
        // IntStream
        IntStream
            .range(1,10) // also we have rangeClosed - includes both edges
            .mapToObj(String::valueOf) // we cannot use map for converting types - use mapToObj
            .map(s -> s.concat("-"))
            .forEach(System.out::println);

        // Another way of converting IntStream to Stream of Integers - boxed() method
        // IntStream
        IntStream
            .range(1,10) // also we have rangeClosed - includes both edges
            .boxed() // returns Stream<Integer>
            .map(String::valueOf) // we cannot use map for converting types - use mapToObj
            .map(s -> s.concat("-"))
            .forEach(System.out::println);
    }
}
