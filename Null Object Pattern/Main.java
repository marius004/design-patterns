import dp.Employee;
import dp.NoEmployee;

public abstract class Employee {

    protected String name;

    public abstract boolean isNull();
    public abstract String getName();
}

public class Coder extends Employee {

    public Coder(String name) {
        this.name = name;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

public class EmployeeRepository {

    final static String[] employees = { "John", "Patrick", "Alex", "Eminem", "Jimmy" };

    public static dp.Employee getEmployee(String name) {

        for(var employee : employees) {

            if(employee.equalsIgnoreCase(name))
                return new dp.Coder(name);
        }

        return new NoEmployee();
    }

}

public class NoEmployee extends Employee {

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String getName() {
        return "Not Available";
    }
}

public class Main {

    public static void main(String[] args) {

        Employee emp1 = EmployeeRepository.getEmployee("John");
        Employee emp2 = EmployeeRepository.getEmployee("Trump");
        Employee emp3 = EmployeeRepository.getEmployee("Patrick");

        System.out.println(emp1.getName());
        System.out.println(emp2.getName());
        System.out.println(emp3.getName());
    }
}