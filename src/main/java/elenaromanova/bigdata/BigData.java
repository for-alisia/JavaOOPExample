package elenaromanova.bigdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BigData {
    record Person(String firstName, String lastName, long salary, String state, char gender) {}

    record Person1(String firstName, String lastName, BigDecimal salary, String state, char gender) {}

    public static String path = "/Users/alisia/Projects/JavaProjects/basicJava/examplesOOP/Hr5m.csv";
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        // exampleWithRecord();
        // exampleWithStrings();
        // anotherGroupExample();
        // nestedGroupExample();
        // reducingExample();
        // partitioningExample();
        otherCollectionsMethodExample();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public static void exampleWithRecord() {
        try {
            long result = Files
                .lines(Path.of(path))
                .parallel() // to make stream faster - with this it takes approx 5 sec
                .skip(1)
                .map(s -> s.split(","))
                .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                .collect(Collectors.summingLong(Person::salary));

            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void groupingExample() {
        try {
            Map<String, List<Person>> groupByState = Files
                    .lines(Path.of(path))
                    .parallel() // to make stream faster - with this it takes approx 5 sec
                    .skip(1)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(Collectors.groupingBy(
                            Person::state,
                            TreeMap::new,
                            Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void anotherGroupExample() {
        try {
            Files
                    .lines(Path.of(path))
                    .skip(1)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(Collectors.groupingBy(
                            Person::state,
                            TreeMap::new,
                            Collectors.collectingAndThen(
                                    Collectors.summingLong(Person::salary),
                                    NumberFormat.getCurrencyInstance(Locale.US)::format
                    )))
                    .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void nestedGroupExample() {
        try {
        Files
            .lines(Path.of(path))
            .skip(1)
            .map(s -> s.split(","))
            .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
            .collect(Collectors.groupingBy(
                Person::state,
                TreeMap::new,
                Collectors.groupingBy(
                    Person::gender,
                    Collectors.collectingAndThen(
                        Collectors.averagingLong(Person::salary),
                        NumberFormat.getCurrencyInstance(Locale.US)::format
                        )
                    )
                )
            )
            .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void partitioningExample() {
        try {
            Files
                    .lines(Path.of(path))
                    .skip(1)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(Collectors.partitioningBy(
                        p -> p.gender == 'F',
                        Collectors.groupingBy(Person::state, Collectors.counting())
                    ))
                    .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void otherCollectionsMethodExample() {
        try {
            Map<String, Long> result = Files
                .lines(Path.of(path))
                .skip(1)
                .map(s -> s.split(","))
                .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                .collect(Collectors.groupingBy(
                    Person::state,
                    Collectors.counting()
                ));
            result.replaceAll((k, v) -> v * 2); // increase coun 2 times
            result.compute("CA", (k, v) -> v * 10); // will provide lambda for specific key
            result.merge("CA", 1L, (x, y) -> x + y); // if not exist, provide 1L, otherwise lambda will be applied
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reducingExample() {
        try {
            Files
                .lines(Path.of(path))
                .skip(1)
                .map(s -> s.split(","))
                .map(a -> new Person1(a[2], a[4], new BigDecimal(a[25]), a[32], a[5].charAt(0)))
                .collect(Collectors.groupingBy(
                    Person1::state,
                    TreeMap::new,
                    Collectors.collectingAndThen(
                        Collectors.reducing(BigDecimal.ZERO, Person1::salary, BigDecimal::add),
                        NumberFormat.getCurrencyInstance(Locale.US)::format
                    )
                ))
                .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exampleWithStrings() {
        try {
            long result = Files
                    .lines(Path.of(path))
                    .parallel() // to make stream faster - with this it takes approx 5 sec
                    .skip(1)
                    .map(s -> s.split(",")[25])
                    // .collect(Collectors.summingLong(s -> Long.parseLong(s))); // this takes approx. 7 sec
                    .mapToLong(Long::parseLong).sum(); // this also takes approx 7 sec
            // .count(); // One option to count all items
            // .collect(Collectors.counting()); // Second option to count amount of elements
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
