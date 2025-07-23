import java.io.Serializable;

class PartTimeEmployee extends Employee implements Serializable {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    @Override
    public String toString() {
        return "PartTimeEmployee [name=" + getName() + ", id=" + getId() + ", hoursWorked=" + hoursWorked + ", hourlyRate=" + String.format("%.2f", hourlyRate) + ", salary=" + String.format("%.2f", calculateSalary()) + "]";
    }
}