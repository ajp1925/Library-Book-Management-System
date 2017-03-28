package lbms.models;

/**
 * Account interface used for Employees and Visitors.
 * @author Team B
 */
public interface Account {

    /**
     * Getter for the account's first and last name concatenated.
     * @return the account owner's name
     */
    String getName();

    /**
     * Getter for the account username.
     * @return the username
     */
    String getUsername();

    /**
     * Getter for the account password.
     * @return the password
     */
    String getPassword();

}
