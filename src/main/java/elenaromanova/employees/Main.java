package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;

public class Main {

    // Collections - List (ArrayList)
    // List is an interface, most common options are ArrayList or LinkedList
    // The difference is internal implementation
    // The same for: containAll, indexOf, remove, removeAll and others using equals under the hood
    private static List<IEmployee> employees = new ArrayList<>();
    private static List<IEmployee> workers = new LinkedList<>();
    // Set allows to exclude duplicates (depends on implementation) - order is unpredictable
    // Under Employee class we need to implement hashCode method (usually we generate it together with equals by IDE)
    // Otherwise we will have default implementation (uniqueness for each Object)
    // hashCode() and equals() should relay on the same properties as internally HashSet after getting the same hash sum will check equals() on both objects
    // HashSet is the fastest, but also we have LinkedHashSet and TreeSet
    // Set interface can't access elements with get() method (like indexes)
    // LinkedHashSet keeps the original order of elements, but it's slower than HashSet
    // Set<IEmployee> uniqueEmployees = new LinkedHashSet<>();
    // TreeSet keeps natural order (like alphabetically, but we can keep control)
    // TreeSet uses compareTo() method under the hood, and if compareTo returns 0 - element will be excluded
    // We should be very specific implementing compareTo method
    // Set<IEmployee> uniqueEmployees = new TreeSet<>();
    // Or we could pass a Comparator if our objects do not have compareTo implementation, or we want to change it in flight
    // Set<IEmployee> uniqueEmployees = new TreeSet<>((o1, o2) -> ...comparator code here...);
    private static Set<IEmployee> uniqueEmployees = new HashSet<>();;
    private static double totalSalary = 0;
    // Map allows to keep object to object relations and fast to access the elements
    // In Map if the key is already exists, value will be replaced
    // HashMap is the fastest (no order is guaranteed)
    // TreeMap - naturally ordered by keys (key should be Comparable) - for String alphabetically
    // LinkedHashMap - keeps original order
    private static Map<String, Integer> nameToSalaryMap = new HashMap<>();;

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
        Matcher peopleMatcher = Employee.PATTERN.matcher(people);

        while(peopleMatcher.find()) {
            String row = peopleMatcher.group();
            IEmployee employee =  Employee.createEmployee(peopleMatcher.group());
            Employee emp = (Employee) employee;
            double salary = employee.getSalary();
            // Adding an object to a collections (only Objects are allowed for collections)
            employees.add(employee);
            workers.add(employee);
            uniqueEmployees.add(employee);
            // If we want not to override elements in the Map - we can use myMap.putIfAbsent
            nameToSalaryMap.put(emp.firstName, emp.getSalary());

            totalSalary += salary;
        }

        printTotalSalary(totalSalary);

        // Set doesn't contain duplicates, but order is not predictable
        for(IEmployee uniqueEmployee : uniqueEmployees) {
            System.out.println(uniqueEmployee.toString());
        }
    }

    // Business logic
    public int getSalarySlow(String firstName) {
        // This implementation works, but it's too slow as we need to iterate through whole collection
        for (IEmployee employee : uniqueEmployees) {
            Employee emp = (Employee) employee;

            if (firstName.equals(emp.firstName)) {
                return emp.getSalary();
            }
        }

        return 0;
    }

    public int getSalary(String firstName) {
        // Map is much faster (no need to iterate through collection)
        return nameToSalaryMap.getOrDefault(firstName, -1); // If name is not in Map, default will be returned
    }

    private static void printTotalSalary(double totalSalary) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.printf("Total salary is %s%n", currencyInstance.format(totalSalary));
    }

    // Maps
    private static void mapMethodsExamples() {
        System.out.println(nameToSalaryMap.values()); // Collection of all values
        System.out.println(nameToSalaryMap.keySet()); // Set of keys - as keys are unique
        System.out.println(nameToSalaryMap.size());
        System.out.println(nameToSalaryMap.entrySet()); // Set of Entries (key - value pairs) - easy to iterate
        for (Map.Entry<String, Integer> entry : nameToSalaryMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            entry.setValue(10); // reset value of entry
        }
        nameToSalaryMap.remove("Anna"); // removing an entry
    }

    // Collections - methods and usage examples
    // Looping
    private static void loopingThroughCollection() {
        // Looping through the collection - enhanced loop
        for (IEmployee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    // Sorting
    // First option
    private static void sortingCollectionWithClass() {
        // Let's print all employees in alphabetical order
        // We use anonymous class (as we use it only here)
        // Sort do not return new collection, but modify original
        // It should return 0 for equal values, 1 - if first should go first, -1 - if first should go second
        // We can use more old "classic" approach with anonymous class
        employees.sort(new Comparator<IEmployee>() {
            @Override
            public int compare(IEmployee o1, IEmployee o2) {
                // we can compare only Employees (as having lastName)
                if (o1 instanceof  Employee emp1 && o2  instanceof Employee emp2) {
                    int lnResult = emp1.lastName.compareTo(emp2.lastName);

                    return lnResult != 0 ? lnResult : emp1.firstName.compareTo(emp2.firstName);
                }
                return 0;
            }
        });
    }

    // Second option
    private static void sortingCollectionWithLambda() {
        // The second option is to use lambda
        employees.sort((o1, o2) -> {
            if (o1 instanceof  Employee emp1 && o2  instanceof Employee emp2) {
                int lnResult = emp1.lastName.compareTo(emp2.lastName);

                return lnResult != 0 ? lnResult : emp1.firstName.compareTo(emp2.firstName);
            }
            return 0;
        });
    }

    // Third option
    private static void sortingCollectionWithUtilityClass() {
        // The third option is Collections (utility) class
        Collections.sort(employees, (o1, o2) -> {
            if (o1 instanceof  Employee emp1 && o2  instanceof Employee emp2) {
                int lnResult = emp1.lastName.compareTo(emp2.lastName);

                return lnResult != 0 ? lnResult : emp1.firstName.compareTo(emp2.firstName);
            }
            return 0;
        });
    }

    // Fourth option
    // Note - class should implement Comparable!
    private static void sortingCollectionWithBuiltInComparatorAndComparable() {
        // The fourth option - to implement Comparable on the class and use built-in Comparators
        Collections.sort(employees, Comparator.naturalOrder());
    }

    // Modifying and accessing elements
    private static void modifyingElementsInTheCollection() {
        // Adding multiple values to the List
        // List created with List.of is unmodified
        List<String> removalNames = new ArrayList<>(List.of("Doe", "Lids", "Aaron"));
        removalNames.sort(Comparator.naturalOrder());
        removeUndesirable(employees, removalNames);

        // Adding one collection to another one
        List<String> newList = new ArrayList<>();
        newList.addAll(removalNames);
        // The same with the constructor
        List<String> secondList = new ArrayList<>(removalNames);
        newList.set(0, "Replaced");
        // To get a subset
        newList.subList(0, 2);
        System.out.println(newList.size());
        // We can clear the whole collection
        secondList.clear();
        System.out.println(secondList.isEmpty()); // check if collection is empty
    }

    private static void removeUndesirable(List<IEmployee> employees, List<String> removalNames) {
        // Looping with iterators
        // if you need to remove elements during the iteration - enhanced loop throws an error
        for (Iterator<IEmployee> it = employees.iterator(); it.hasNext(); ) {
            IEmployee employee = it.next();
            if (employee instanceof Employee empl) {
                // We need to cast as IEmployee doesn't have getters
                if (removalNames.contains(empl.getLastName())) {
                    it.remove();
                }
            }
        }
    }

    private static void convertCollectionToArrayExample() {
        // Convert collection to array with predefined custom type
        IEmployee[] employeesArray = employees.toArray(new IEmployee[0]);
    }

    private static void ifElementIsInCollectionExample() {
        IEmployee customEmployee = Employee.createEmployee("Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}");
        // To make it work we need to Override equals method on Employee class to "teach" it how to compare instances
        // Otherwise it will use default method and compare memory locations for 2 objects
        System.out.println(employees.contains(customEmployee));
    }

    private static void accessToElementsInCollection() {
        // We can access by indexes
        IEmployee third = employees.get(2);
        System.out.println(employees.indexOf(third)); // 2 - will return an index of the element
    }


    // Sets
    private static void accessToElementsInSet() {
        // For sets:
        List<IEmployee> tempEmps = new ArrayList<>(uniqueEmployees); // first convert to an array (expensive operation)
        IEmployee fourth = tempEmps.get(3);
        System.out.println(fourth);
    }

    // Record example
    private static void recordUsageExample() {
        RecordExample myRecord = new RecordExample("John", "Doe", LocalDate.of(1900, 8, 12));

        System.out.println(myRecord.firstName());
    }

    // Composition usage example
    private static void createCompositionExample(String row) {
        // Example of using composition
        Flyer flyer = new Manager(row);
        flyer.fly();
    }

    // Example of instanceof, getClass
    private static void showEmployeeDetails(IEmployee employee) {
        // How we can define if object belongs to a certain class
        // getClass will check to a certain class only
        // instanceof checks the whole hierarchy chain
        // emp instanceof Developer - true and emp instanceof Employee - true
        if (employee.getClass().equals(Developer.class)) {
            // Here we need to cast that employee is a Developer
            // Developer dev = (Developer) employee;
            System.out.println(((Developer) employee).getIq());
        } else if (employee instanceof Manager) {
            System.out.println("Manager");
        } else if (employee instanceof Analyst analyst) {
            // With more modern (16+) variation of instanceof we can cast it immediately to a variable
            System.out.println(analyst.getBaseAmountOfProjects());
        }
    }
}
