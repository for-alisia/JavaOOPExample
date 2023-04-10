package elenaromanova.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Repository<T extends Repository.IDable<V> & Repository.Savable, V> {
    record Person(String firstName, String lastName, Long id) implements IDable<Long>, Savable{};
    interface IDable<S> {
        S id();
    }

    interface Savable {}
    private List<T> records = new ArrayList<>();

    List<T> findAll() {
        return records;
    }

    T save(T record) {
        records.add(record);

        return record;
    }

    T findById(long id) {
        return records.stream().filter(p -> p.id().equals(id)).findFirst().orElseThrow();
    }

    static <T,V>V encrypt(T input, Function<T, V> func) {  // static method that gets 2 generics and lambda
        return func.apply(input);
    }

    public static void main(String[] args) {
//        Repository<String> repo = new Repository<>();
//
//        repo.save("Boat");
//        repo.save("House");
//
//        System.out.println(repo.findAll());

        Repository<Person, Long> pRepo = new Repository<>();

        pRepo.save(new Person("Elena", "Romanova", 10L));
        pRepo.save(new Person("John", "Doe", 20L));

        System.out.println(pRepo.findAll());

        Person foundPerson = pRepo.findById(20L);

        System.out.println(foundPerson);

        // using static method with lambda and 2 generics
        System.out.println(Repository.<String, String>encrypt("Hello", String::toUpperCase));
        System.out.println(Repository.<String, Integer>encrypt("World", String::hashCode));
    }
}
