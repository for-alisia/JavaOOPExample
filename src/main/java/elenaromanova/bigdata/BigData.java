package elenaromanova.bigdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BigData {
    record Person(String firstName, String lastName, long salary, String state) {}

    public static String path = "/Users/alisia/Projects/JavaProjects/basicJava/examplesOOP/Hr5m.csv";
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        exampleWithRecord();
        // exampleWithStrings();
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
                .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32]))
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
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32]))
                    .collect(Collectors.groupingBy(Person::state, TreeMap::new, Collectors.toList()));
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
