<h1>Employee Payroll Management System (Java Swing + OOP + Serialization)</h1>

<p>
A desktop-based <strong>Payroll Management System</strong> built using 
<strong>Java</strong>, <strong>Swing GUI</strong>, and 
<strong>Object-Oriented Programming</strong> principles.  
It supports <strong>Full-Time, Part-Time, and Contract</strong> employees 
and stores data persistently using <strong>Object Serialization</strong>.
</p>

<hr>

<h2>⭐ Features</h2>

<h3>✓ Employee Management</h3>
<ul>
  <li>Add new employees (Full-Time / Part-Time / Contract)</li>
  <li>Remove employees using ID</li>
  <li>Update salary or contract amount</li>
  <li>Search employee by <strong>ID</strong> or <strong>Name</strong></li>
  <li>Display all employees in a table</li>
</ul>

<h3>✓ Types of Employees</h3>
<ul>
  <li><strong>Full-Time Employee</strong> → Monthly Salary</li>
  <li><strong>Part-Time Employee</strong> → Hours Worked × Hourly Rate</li>
  <li><strong>Contract Employee</strong> → Contract Amount + Duration</li>
</ul>

<h3>✓ File Persistence</h3>
<ul>
  <li>Data stored in <code>employees.dat</code> using serialization</li>
  <li>Auto-loads saved data at startup</li>
</ul>

<h3>✓ GUI Features</h3>
<ul>
  <li>Built using Java Swing</li>
  <li>Clean and organized UI with GridBagLayout</li>
  <li>JTable for employee records</li>
  <li>Real-time validation and messages</li>
</ul>

<hr>

<h2>🛠 Tech Stack</h2>

<table>
<tr><th>Component</th><th>Technology</th></tr>
<tr><td>Language</td><td>Java 8+</td></tr>
<tr><td>GUI Framework</td><td>Swing</td></tr>
<tr><td>Concepts Used</td><td>OOP – Inheritance, Abstraction, Polymorphism</td></tr>
<tr><td>Persistence</td><td>Serialization (Object Streams)</td></tr>
</table>

<hr>

<h2>🚀 How to Run the Project</h2>

<h3>1. Clone the Repository</h3>
<pre><code>git clone https://github.com/your-username/payroll-system.git
cd payroll-system
</code></pre>

<h3>2. Compile the Java Files</h3>

<p>If all <code>.java</code> files are in the root folder:</p>
<pre><code>javac *.java
</code></pre>

<p>If files are inside <code>src/</code> folder:</p>
<pre><code>javac src/*.java
</code></pre>

<h3>3. Run the Application</h3>

<p>If compiled in root:</p>
<pre><code>java Main
</code></pre>

<p>If compiled inside <code>src/</code>:</p>
<pre><code>java -cp src Main
</code></pre>

<hr>

<h2>📁 Project Structure</h2>

<pre>
src/
├── Employee.java
├── FullTimeEmployee.java
├── PartTimeEmployee.java
├── ContractEmployee.java
├── PayrollSystem.java
├── PayRollGUI.java
└── Main.java

employees.dat   (auto-generated)
README.md
</pre>

<hr>

<h2>🧩 Class Overview</h2>

<h3>Employee (Abstract Class)</h3>
<ul>
  <li>Base class for all employees</li>
  <li>Contains <code>name</code>, <code>id</code>, and abstract <code>calculateSalary()</code></li>
</ul>

<h3>FullTimeEmployee</h3>
<ul>
  <li>Has <strong>monthlySalary</strong></li>
  <li>Overrides <code>calculateSalary()</code></li>
</ul>

<h3>PartTimeEmployee</h3>
<ul>
  <li>Has <strong>hoursWorked</strong> & <strong>hourlyRate</strong></li>
  <li>Salary = hoursWorked × hourlyRate</li>
</ul>

<h3>ContractEmployee</h3>
<ul>
  <li>Contract amount & duration (months)</li>
  <li>Salary = contractAmount</li>
</ul>

<h3>PayrollSystem</h3>
<ul>
  <li>Maintains employee list</li>
  <li>Add / Remove / Update / Search functionality</li>
  <li>Serialization-based file persistence</li>
</ul>

<h3>PayRollGUI</h3>
<ul>
  <li>Full Swing interface</li>
  <li>Inputs, table, search, update, delete</li>
  <li>Real-time validation</li>
</ul>

<hr>

<h2>📌 Future Enhancements</h2>
<ul>
  <li>Export data to CSV/Excel</li>
  <li>Admin login system</li>
  <li>JavaFX UI upgrade</li>
  <li>PDF salary slip generation</li>
  <li>Sorting & filtering</li>
</ul>

<hr>

<h2>🤝 Contributing</h2>
<p>Pull requests and feature suggestions are welcome!</p>

<h2>📜 License</h2>
<p>This project is open-source and available under the <strong>MIT License</strong>.</p>
