package lbms.models;

import java.io.Serializable;

/**
 * Class for an Employee object, used in the library book management system.
 * @author Team B
 */
public class Employee implements Serializable {

    private Visitor v;

    /**
     * Constructor for an Employee class.
     * @param firstName: the employee's first name
     * @param lastName: the employee's last name
     * @param username: the employee's username to login
     * @param password: the employee's password
     */
    public Employee(String firstName, String lastName, String username, String password, String address, long phoneNumber) {
        v = new Visitor(firstName, lastName, username, password, address, phoneNumber);
    }

    /**
     * Getter for the employee's name.
     * @return the first and last name concatenated
     */
    public String getName() {
        return v.getName();
    }

    /**
     * Getter for the employee's username.
     * @return the username
     */
    public String getUsername() {
        return v.getUsername();
    }

    /**
     * Getter for the employee's password.
     * @return the password
     */
    public String getPassword() {
        return v.getPassword();
    }
}
