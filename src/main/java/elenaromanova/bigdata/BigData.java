package elenaromanova.bigdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class BigData {
    public static void main(String[] args) {
        try {
            long recordsCount = Files
                .lines(Path.of("/Users/alisia/Projects/JavaProjects/basicJava/examplesOOP/Hr5m.csv"))
                .skip(1)
                // .count(); // One option to count all items
                .collect(Collectors.counting());
            System.out.println(recordsCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
