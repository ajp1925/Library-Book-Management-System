package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * CreateController class for the Library Book Management System.
 * @author Team B
 */
public class CreateController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Text usernameFail;
    @FXML private Text passwordFail;
    @FXML private Text confirmFail;

    /**
     * Getter for the username.
     * @return a string of the username
     */
    String getUsernameField() {
        return this.usernameField.getText();
    }

    /**
     * Getter for the password.
     * @return a string of the password
     */
    String getPasswordField() {
        return this.passwordField.getText();
    }

    /**
     * Getter for the confirm field.
     * @return the value of the confirm field
     */
    String getConfirmField() {
        return this.confirmField.getText();
    }

    /**
     * Sets the username when it fails.
     */
    void failUsername() {
        this.usernameFail.setText("*");
    }

    /**
     * Sets the password when it fails.
     */
    void failPassword() {
        this.passwordFail.setText("*");
    }

    /**
     * Confirms that it has failed.
     */
    void failConfirm() {
        this.confirmFail.setText("*");
    }

    /**
     * Clears all the fields by setting them to empty strings.
     */
    void clearError() {
        this.usernameFail.setText("");
        this.passwordFail.setText("");
        this.confirmFail.setText("");
    }
}
