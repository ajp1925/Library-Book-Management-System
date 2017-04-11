package lbms.controllers.guicontrollers.RegisterControllers;

import java.util.ArrayList;

/**
 * UserController interface for the Library Book Management System.
 * @author Team B
 */
public interface UserController {

    /**
     * Determines if the controller is new or not.
     * @return true if new, false if not
     */
    boolean isNew();

    /**
     * Gets the fields of the user.
     * @return an array of the fields of the user
     */
    ArrayList<String> getFields();

    /**
     * Fills the text if a field is empty.
     */
    void fail();

    /**
     * Sets the text to empty strings.
     */
    void clearError();

}
