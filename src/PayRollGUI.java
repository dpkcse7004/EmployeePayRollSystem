import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class PayRollGUI extends JFrame {
    private PayrollSystem payrollSystem;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextArea outputArea;

    public PayRollGUI() {
        payrollSystem = new PayrollSystem();
        setTitle("Employee Payroll System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        refreshEmployeeTable();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // --- Input Panel (North) ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Actions"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time", "Contract"});

        JLabel salaryLabel = new JLabel("Monthly/Contract Amount:");
        JTextField salaryField = new JTextField(10);
        JLabel hoursRateLabel = new JLabel("Hours Worked / Hourly Rate:");
        JTextField hoursWorkedField = new JTextField(5);
        JTextField hourlyRateField = new JTextField(5);
        JLabel contractDurationLabel = new JLabel("Contract Duration (Months):");
        JTextField contractDurationField = new JTextField(5);


        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                String type = (String) typeComboBox.getSelectedItem();

                if (name.isEmpty() || idField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (id <= 0) {
                    JOptionPane.showMessageDialog(this, "Employee ID must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                switch (type) {
                    case "Full-Time":
                        double monthlySalary = Double.parseDouble(salaryField.getText());
                        if (monthlySalary < 0) {
                            JOptionPane.showMessageDialog(this, "Monthly salary cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        payrollSystem.addEmployee(new FullTimeEmployee(name, id, monthlySalary));
                        break;
                    case "Part-Time":
                        int hoursWorked = Integer.parseInt(hoursWorkedField.getText());
                        double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                        if (hoursWorked < 0 || hourlyRate < 0) {
                            JOptionPane.showMessageDialog(this, "Hours worked or hourly rate cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        payrollSystem.addEmployee(new PartTimeEmployee(name, id, hoursWorked, hourlyRate));
                        break;
                    case "Contract":
                        double contractAmount = Double.parseDouble(salaryField.getText());
                        int contractDuration = Integer.parseInt(contractDurationField.getText());
                        if (contractAmount < 0 || contractDuration <= 0) {
                            JOptionPane.showMessageDialog(this, "Contract amount cannot be negative and duration must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        payrollSystem.addEmployee(new ContractEmployee(name, id, contractAmount, contractDuration));
                        break;
                }
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                clearInputFields(nameField, idField, salaryField, hoursWorkedField, hourlyRateField, contractDurationField);
                refreshEmployeeTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format. Please enter valid numbers for ID, salary, hours, rate, or duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton removeEmployeeButton = new JButton("Remove Employee");
        removeEmployeeButton.addActionListener(e -> {
            try {
                String idInput = JOptionPane.showInputDialog(this, "Enter Employee ID to remove:");
                if (idInput == null || idInput.trim().isEmpty()) return; // User cancelled or entered empty
                int idToRemove = Integer.parseInt(idInput);
                payrollSystem.removeEmployee(idToRemove);
                JOptionPane.showMessageDialog(this, "Employee removed successfully!");
                refreshEmployeeTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton searchEmployeeButton = new JButton("Search Employee");
        searchEmployeeButton.addActionListener(e -> {
            String searchTerm = JOptionPane.showInputDialog(this, "Enter Employee ID or Name to search:");
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                try {
                    int idSearch = Integer.parseInt(searchTerm);
                    Optional<Employee> employeeOpt = payrollSystem.getEmployeeById(idSearch);
                    if (employeeOpt.isPresent()) {
                        outputArea.setText("Search Result:\n" + employeeOpt.get().toString());
                    } else {
                        outputArea.setText("No employee found with ID: " + idSearch);
                    }
                } catch (NumberFormatException ex) {
                    List<Employee> results = payrollSystem.searchEmployeesByName(searchTerm);
                    if (!results.isEmpty()) {
                        StringBuilder sb = new StringBuilder("Search Results (by name):\n");
                        for (Employee emp : results) {
                            sb.append(emp.toString()).append("\n");
                        }
                        outputArea.setText(sb.toString());
                    } else {
                        outputArea.setText("No employee found with name containing: " + searchTerm);
                    }
                }
            } else {
                outputArea.setText(""); // Clear search results if input is empty
            }
        });

        JButton updateEmployeeButton = new JButton("Update Employee Salary/Amount");
        updateEmployeeButton.addActionListener(e -> {
            try {
                String idInput = JOptionPane.showInputDialog(this, "Enter Employee ID to update:");
                if (idInput == null || idInput.trim().isEmpty()) return;
                int idToUpdate = Integer.parseInt(idInput);
                Optional<Employee> employeeOpt = payrollSystem.getEmployeeById(idToUpdate);

                if (employeeOpt.isPresent()) {
                    Employee emp = employeeOpt.get();
                    String newSalaryStr = null;
                    if (emp instanceof FullTimeEmployee) {
                        newSalaryStr = JOptionPane.showInputDialog(this, "Enter new Monthly Salary for " + emp.getName() + " (Current: " + String.format("%.2f", ((FullTimeEmployee) emp).getMonthlySalary()) + "):");
                    } else if (emp instanceof ContractEmployee) {
                        newSalaryStr = JOptionPane.showInputDialog(this, "Enter new Contract Amount for " + emp.getName() + " (Current: " + String.format("%.2f", ((ContractEmployee) emp).getContractAmount()) + "):");
                    } else {
                        JOptionPane.showMessageDialog(this, "Update not directly supported for Part-Time employees (hourly rate/hours).", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    if (newSalaryStr != null && !newSalaryStr.trim().isEmpty()) {
                        double newSalary = Double.parseDouble(newSalaryStr);
                        if (newSalary < 0) {
                            JOptionPane.showMessageDialog(this, "Salary cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        payrollSystem.updateEmployee(idToUpdate, newSalary);
                        JOptionPane.showMessageDialog(this, "Employee details updated successfully!");
                        refreshEmployeeTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Employee with ID " + idToUpdate + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException | UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(nameField, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; inputPanel.add(idLabel, gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; inputPanel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(typeComboBox, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; inputPanel.add(salaryLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(salaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; inputPanel.add(hoursRateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(hoursWorkedField, gbc);
        gbc.gridx = 2; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(new JLabel("/"), gbc);
        gbc.gridx = 3; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(hourlyRateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; inputPanel.add(contractDurationLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST; inputPanel.add(contractDurationField, gbc);


        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) typeComboBox.getSelectedItem();
                salaryLabel.setText("Monthly/Contract Amount:");

                boolean isFullTime = "Full-Time".equals(selectedType);
                boolean isPartTime = "Part-Time".equals(selectedType);
                boolean isContract = "Contract".equals(selectedType);

                salaryField.setVisible(isFullTime || isContract);
                salaryLabel.setVisible(isFullTime || isContract);

                hoursWorkedField.setVisible(isPartTime);
                hourlyRateField.setVisible(isPartTime);
                hoursRateLabel.setVisible(isPartTime);

                contractDurationField.setVisible(isContract);
                contractDurationLabel.setVisible(isContract);

                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });
        typeComboBox.setSelectedIndex(0);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(removeEmployeeButton);
        buttonPanel.add(searchEmployeeButton);
        buttonPanel.add(updateEmployeeButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER; inputPanel.add(buttonPanel, gbc);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Type", "Calculated Salary", "Specific Details"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputArea.setBorder(BorderFactory.createTitledBorder("Messages/Search Results"));
        mainPanel.add(outputScrollPane, BorderLayout.SOUTH);
    }

    private void refreshEmployeeTable() {
        tableModel.setRowCount(0);
        for (Employee emp : payrollSystem.getAllEmployees()) {
            String type = "";
            String details = "";

            if (emp instanceof FullTimeEmployee) {
                type = "Full-Time";
                details = "Monthly: " + String.format("%.2f", ((FullTimeEmployee) emp).getMonthlySalary());
            } else if (emp instanceof PartTimeEmployee) {
                type = "Part-Time";
                PartTimeEmployee pte = (PartTimeEmployee) emp;
                details = "Hours: " + pte.getHoursWorked() + ", Rate: " + String.format("%.2f", pte.getHourlyRate());
            } else if (emp instanceof ContractEmployee) {
                type = "Contract";
                ContractEmployee cte = (ContractEmployee) emp;
                details = "Amount: " + String.format("%.2f", cte.getContractAmount()) + ", Duration: " + cte.getContractDurationMonths() + " months";
            }
            tableModel.addRow(new Object[]{emp.getId(), emp.getName(), type, String.format("%.2f", emp.calculateSalary()), details});
        }
    }

    private void clearInputFields(JTextField nameField, JTextField idField, JTextField salaryField, JTextField hoursWorkedField, JTextField hourlyRateField, JTextField contractDurationField) {
        nameField.setText("");
        idField.setText("");
        salaryField.setText("");
        hoursWorkedField.setText("");
        hourlyRateField.setText("");
        contractDurationField.setText("");
    }
}