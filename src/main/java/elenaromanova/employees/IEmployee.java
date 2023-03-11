package elenaromanova.employees;

public interface IEmployee extends Comparable<IEmployee> {
    int getSalary();

    @Override
    int compareTo(IEmployee o);
}
