package elenaromanova.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        String people = """
                Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}
                Stephen, Rider, 11/02/1976, Manager, {orgSize=150,dr=5}
                Anton, Lids, 11/02/1976, Manager, {orgSize=180,dr=8}
                John, Smith, 23/09/1999, Analyst, {projectCount=5}
                William, Carter, 23/09/1999, Analyst, {projectCount=7}
                Alex, Green, 23/09/1999, Analyst, {projectCount=10}
                Max, Jonas, 10/01/1987, Developer, {locpd=2000,yoe=10,iq=140}
                Lisa, Doll, 22/02/1987, Developer, {locpd=1300,yoe=3,iq=105}
                Andrea, Black, 30/03/1987, Developer, {locpd=1600,yoe=7,iq=100}
                """;
        Matcher peopleMatcher = Employee.PATTERN.matcher(people);

        double totalSalary = 0;
        // Collections - List (ArrayList)
        // List is an interface, most common options are ArrayList or LinkedList
        // The difference is internal implementation
        // The same for: containAll, indexOf, remove, removeAll and others using equals under the hood
        List<IEmployee> employees = new ArrayList<>();
        List<IEmployee> workers = new LinkedList<>();
        while(peopleMatcher.find()) {
            String row = peopleMatcher.group();
            // Composition example
            createCompositionExample(row);
            IEmployee employee =  Employee.createEmployee(peopleMatcher.group());
            double salary = employee.getSalary();
            // Adding an object to a collections (only Objects are allowed for collections)
            employees.add(employee);
            workers.add(employee);
            totalSalary += salary;
            // Example of instanceof, getClass
            showEmployeeDetails(employee);
        }

        printTotalSalary(totalSalary);

        // We can access by indexes
        IEmployee third = employees.get(2);
        System.out.println(employees.indexOf(third)); // 2 - will return an index of the element

        IEmployee customEmployee = Employee.createEmployee("Anna, Doe, 11/02/1976, Manager, {orgSize=300,dr=10}");
        // To make it work we need to Override equals method on Employee class to "teach" it how to compare instances
        // Otherwise it will use default method and compare memory locations for 2 objects
        System.out.println(employees.contains(customEmployee));

        // Convert collection to array with predefined custom type
        IEmployee[] employeesArray = employees.toArray(new IEmployee[0]);

        // Adding multiple values to the List
        // List created with List.of is unmodified
        List<String> removalNames = new ArrayList<>(List.of("Doe", "Lids", "Aaron"));
        removalNames.sort(Comparator.naturalOrder());
        System.out.println(removalNames);
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

        // The second option is to use lambda
        employees.sort((o1, o2) -> {
            if (o1 instanceof  Employee emp1 && o2  instanceof Employee emp2) {
                int lnResult = emp1.lastName.compareTo(emp2.lastName);

                return lnResult != 0 ? lnResult : emp1.firstName.compareTo(emp2.firstName);
            }
            return 0;
        });

        // The third option is Collections (utility) class
        Collections.sort(employees, (o1, o2) -> {
            if (o1 instanceof  Employee emp1 && o2  instanceof Employee emp2) {
                int lnResult = emp1.lastName.compareTo(emp2.lastName);

                return lnResult != 0 ? lnResult : emp1.firstName.compareTo(emp2.firstName);
            }
            return 0;
        });

        // The fourth option - to implement Comparable on the class and use built-in Comparators
        Collections.sort(employees, Comparator.naturalOrder());

        // Looping through the collection - enhanced loop
        for (IEmployee employee : employees) {
            System.out.println(employee.toString());
        }

        RecordExample myRecord = new RecordExample("John", "Doe", LocalDate.of(1900, 8, 12));

        System.out.println(myRecord.firstName());

//        Developer newDev = new Developer("");
//
//        System.out.println(newDev.getAmountOfInterviews());
    }

    private static void printTotalSalary(double totalSalary) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.printf("Total salary is %s%n", currencyInstance.format(totalSalary));
    }

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
}
