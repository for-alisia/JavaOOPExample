package elenaromanova.datastore;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public class OptionalExample {
    record Car(String make, String model, String color, Year year) {}
    record Person(Long id, String firstName, String lastName, Optional<Car> car) implements Repository.IDable<Long>, Repository.Savable {}
    public static void main(String[] args) {
        Optional<String> optMsg = Optional.of("I am an optional string");
        System.out.println(optMsg); // Optional[I am an optional]
        String msg = null;
        Optional<String> optMsg2 = Optional.ofNullable(msg);
        System.out.println(optMsg2.orElse("Hi").toUpperCase());
        if (optMsg2.isPresent()) {
            System.out.println(optMsg2.get());
        }
//        optMsg2.orElseThrow(() -> new RuntimeException("Nothing found"));
//        optMsg2.orElseGet(() -> "pass here something");
//        optMsg2.or(() -> Optional.of("Nothing here"));
//        optMsg.filter(s -> s.length() > 3).orElse("Invalid");

        Repository<Person, Long> repo = new Repository<>();

        Person p1 = new Person(
                100L,
                "John",
                "Doe",
                Optional.of(new Car("Ford", "Accord", "black", Year.of(2013)))
        );
        Person p2 = new Person(
                200L,
                "Anna",
                "Smith",
                Optional.of(new Car("Opel", "Tia", "red", Year.of(2020)))
        );
        Person p3 = new Person(
                300L,
                "Sabina",
                "Rio",
                Optional.of(new Car("Suzuki", "Swift", "yellow", Year.of(2018)))
        );

        repo.save(p1);
        repo.save(p2);

        Optional<Person> optP1 = Optional.of(p1);
        System.out.println(optP1
                .flatMap(Person::car)
                .map(Car::make)
                .orElse("N/A"));
        Optional<Person> optP3 = Optional.ofNullable(null);
        System.out.println(optP3.map(Person::firstName).orElse("N/A"));

        System.out.println(repo
                .findById(100L)
                .map(Person::firstName)
                .orElse("N/A")
        );
        System.out.println(repo
                .findById(110L)
                .map(Person::firstName)
                .orElse("N/A")
        );

        List<Optional<Person>> personList = List.of(Optional.of(p1), Optional.of(p2), Optional.ofNullable(null), Optional.of(p3));

        personList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Person::firstName)
                .forEach(System.out::println);
    }
}
