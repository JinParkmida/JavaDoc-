/**
 * Employee class.
 * TODO: Add detailed description
 * 
 * @author Jin Park
 * @version 1.0
 */
public class Employee {
    /**
     * The name identifier.
     */
    private String name;
    /**
     * The unique identifier.
     */
    private int id;
    /**
     * The salary.
     */
    private double salary;
    /**
     * The employee count.
     */
    private static int employeeCount = 0;
    
    /**
     * Employee.
     * @param name the name string
     * @param salary the salary
     */
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.id = ++employeeCount;
    }
    
    /**
     * Gets the name.
     * @return the result string
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name.
     * @param name the name string
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the salary.
     * @return the result value
     */
    public double getSalary() {
        return salary;
    }
    
    /**
     * Sets the salary.
     * @param salary the salary
     * @throws IllegalArgumentException if TODO
     */
    public void setSalary(double salary) throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
    
    /**
     * Calculate bonus.
     * @param percentage the percentage
     * @return the result value
     */
    public double calculateBonus(double percentage) {
        return salary * (percentage / 100);
    }
    
    /**
     * Checks if high earner.
     * @return true if successful, false otherwise
     */
    public boolean isHighEarner() {
        return salary > 100000;
    }
    
    /**
     * Returns a string representation of this object.
     * @return the result string
     */
    @Override
    public String toString() {
        return String.format("Employee[id=%d, name=%s, salary=%.2f]", id, name, salary);
    }
}