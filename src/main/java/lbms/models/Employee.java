package lbms.models;

import java.io.Serializable;

/**
 * Class for an Employee object, used in the library book management system.
 * @author Team B
 */
public class Employee implements Serializable {

    private Visitor v;

    /**
     * Constructor for an Employee object.
     * @param v: the visitor account for the employee
     */
    public Employee(Visitor v) {
        this.v = v;
    }

    /**
     * Getter for the employee's name.
     * @return the first and last name concatenated
     */
    public String getName() {
        return this.v.getName();
    }

    /**
     * Getter for the employee's username.
     * @return the username
     */
    public String getUsername() {
        return this.v.getUsername();
    }

    /**
     * Getter for the employee's password.
     * @return the password
     */
    public String getPassword() {
        return this.v.getPassword();
    }

    /**
     * Getter for the visitor of the employee.
     * @return the visitor account
     */
    public Visitor getVisitor() {
        return this.v;
    }

    /**
     * Creates a string of the employee's data.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "Employee:\n" + this.v.toString();
    }
}
