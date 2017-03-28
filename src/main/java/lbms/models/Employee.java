package lbms.models;

import lbms.LBMS;

import java.io.Serializable;

/**
 * Class for an Employee object, used in the library book management system.
 * @author Team B
 */
public class Employee implements Account, Serializable {

    private String firstName, lastName;
    private String username;
    private String password;
    private long employeeID;

    /**
     * Constructor for an Employee class.
     * @param firstName: the employee's first name
     * @param lastName: the employee's last name
     * @param username: the employee's username to login
     * @param password: the employee's password
     */
    public Employee(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.employeeID = LBMS.totalAccounts() + 1;
    }

    /**
     * Getter for the employee's name.
     * @return the first and last name concatenated
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    /**
     * Getter for the employee's username.
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter for the employee's password.
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }
}
