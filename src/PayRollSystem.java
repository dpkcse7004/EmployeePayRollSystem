import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PayrollSystem {
    private List<Employee> employeeList;
    private static final String DATA_FILE = "employees.dat";

    public PayrollSystem() {
        employeeList = new ArrayList<>();
        loadEmployees();
    }

    public void addEmployee(Employee employee) {
        if (employeeList.stream().anyMatch(e -> e.getId() == employee.getId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " already exists.");
        }
        employeeList.add(employee);
        saveEmployees();
    }

    public void removeEmployee(int id) {
        boolean removed = employeeList.removeIf(employee -> employee.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found.");
        }
        saveEmployees();
    }

    public Optional<Employee> getEmployeeById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    public List<Employee> searchEmployeesByName(String name) {
        List<Employee> results = new ArrayList<>();
        String lowerCaseName = name.toLowerCase();
        for (Employee employee : employeeList) {
            if (employee.getName().toLowerCase().contains(lowerCaseName)) {
                results.add(employee);
            }
        }
        return results;
    }

    public void updateEmployee(int id, double newSalary) {
        Optional<Employee> employeeOpt = getEmployeeById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            if (employee instanceof FullTimeEmployee) {
                ((FullTimeEmployee) employee).setMonthlySalary(newSalary);
            } else if (employee instanceof ContractEmployee) {
                ((ContractEmployee) employee).setContractAmount(newSalary);
            } else {
                throw new UnsupportedOperationException("Direct salary update not supported for Part-Time Employees. Please manage through hours or rate if applicable.");
            }
            saveEmployees();
        } else {
            throw new IllegalArgumentException("Employee with ID " + id + " not found for update.");
        }
    }


    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }

    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employeeList);
            System.out.println("Employee data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving employee data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadEmployees() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                employeeList = (List<Employee>) ois.readObject();
                System.out.println("Employee data loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading employee data: " + e.getMessage());
                employeeList = new ArrayList<>();
            }
        } else {
            System.out.println("No existing employee data file found. Starting with an empty payroll.");
        }
    }
}