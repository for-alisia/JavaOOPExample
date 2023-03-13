package elenaromanova.employees;

import java.time.LocalDate;

public record RecordExample(String firstName, String lastName, LocalDate dob) implements IEmployee{
    // Java creates constructor, getters, equals and hash methods
    // There are no setters - records are final (immutable), can't be extended or abstract

    // We can create custom constructors as well
    public RecordExample(String firstName, String lastName) {
        this(firstName, lastName, null);
    }

    // We can add methods to it as well
    public void sayHi() {
        System.out.println(firstName);
    }
    @Override
    public int getSalary() {
        return 0;
    }

    @Override
    public int compareTo(IEmployee o) {
        return 0;
    }
}
