import java.io.Serializable;

class FullTimeEmployee extends Employee implements Serializable {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee [name=" + getName() + ", id=" + getId() + ", monthlySalary=" + String.format("%.2f", monthlySalary) + ", salary=" + String.format("%.2f", calculateSalary()) + "]";
    }
}