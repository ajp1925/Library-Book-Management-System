package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * NewUserController class for the Library Book Management System.
 * @author Team B
 */
public class NewUserController implements UserController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneNumberField;
    @FXML private Text firstNameFail;
    @FXML private Text lastNameFail;
    @FXML private Text addressFail;
    @FXML private Text phoneNumberFail;

    /**
     * Determines if it is a new controller.
     * @return true always
     */
    @Override
    public boolean isNew() {
        return true;
    }

    /**
     * Gets the fields of the user.
     * @return an array of strings of the user's information
     */
    @Override
    public ArrayList<String> getFields() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(this.firstNameField.getText());
        arr.add(this.lastNameField.getText());
        arr.add(this.addressField.getText());
        arr.add(this.phoneNumberField.getText());
        return arr;
    }

    /**
     * Fills the text with an '*' if the text is empty.
     */
    @Override
    public void fail() {
        if (this.firstNameField.getText().isEmpty()) {
            this.firstNameFail.setText("*");
        }
        if (this.lastNameField.getText().isEmpty()) {
            this.lastNameFail.setText("*");
        }
        if (this.addressField.getText().isEmpty()) {
            this.addressFail.setText("*");
        }
        if (this.phoneNumberField.getText().isEmpty()) {
            this.phoneNumberFail.setText("*");
        }
    }

    /**
     * Sets the text to empty for the user fields.
     */
    public void clearError() {
        this.firstNameFail.setText("");
        this.lastNameFail.setText("");
        this.addressFail.setText("");
        this.phoneNumberFail.setText("");
    }

}
