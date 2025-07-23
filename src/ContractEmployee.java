import java.io.Serializable;

class ContractEmployee extends Employee implements Serializable {
    private double contractAmount;
    private int contractDurationMonths;

    public ContractEmployee(String name, int id, double contractAmount, int contractDurationMonths) {
        super(name, id);
        this.contractAmount = contractAmount;
        this.contractDurationMonths = contractDurationMonths;
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public int getContractDurationMonths() {
        return contractDurationMonths;
    }

    public void setContractDurationMonths(int contractDurationMonths) {
        this.contractDurationMonths = contractDurationMonths;
    }

    @Override
    public double calculateSalary() {
        return contractAmount;
    }

    @Override
    public String toString() {
        return "ContractEmployee [name=" + getName() + ", id=" + getId() + ", contractAmount=" + String.format("%.2f", contractAmount) + ", duration=" + contractDurationMonths + " months, salary=" + String.format("%.2f", calculateSalary()) + "]";
    }
}