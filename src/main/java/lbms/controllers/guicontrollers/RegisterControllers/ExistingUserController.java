package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * ExistingUserController class for the Library Book Management System.
 * @author Team B
 */
public class ExistingUserController implements UserController {

    @FXML private TextField visitorIdField;
    @FXML private Text visitorIdFail;

    /**
     * Determines if the user controller is new.
     * @return always false
     */
    @Override
    public boolean isNew() {
        return false;
    }

    /**
     * Gets the fields for the user.
     * @return an array list of strings of the users information
     */
    @Override
    public ArrayList<String> getFields() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(this.visitorIdField.getText());
        return arr;
    }

    /**
     * Sets the fields to '*' on a failure.
     */
    @Override
    public void fail() {
        this.visitorIdFail.setText("*");
    }

    /**
     * Clears the text fields by setting them to empty string.
     */
    @Override
    public void clearError() {
        this.visitorIdFail.setText("");
    }

}
