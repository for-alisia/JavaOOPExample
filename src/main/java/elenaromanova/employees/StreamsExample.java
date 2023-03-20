package elenaromanova.employees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                """;
        // .lines() - creates a stream of strings
        people
            .lines()
            .map(Employee::createEmployee) //.map(s -> Employee.createEmployee(s))
            .forEach(System.out::println); // .forEach((s) -> System.out.println(s))
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
