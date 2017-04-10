package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * Created by Chris on 4/6/17.
 */
public class CreateController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Text usernameFail;
    @FXML private Text passwordFail;
    @FXML private Text confirmFail;

    String getUsernameField() {
        return usernameField.getText();
    }

    String getPasswordField() {
        return passwordField.getText();
    }

    String getConfirmField() {
        return confirmField.getText();
    }

    void failUsername() {
        usernameFail.setText("*");
    }

    void failPassword() {
        passwordFail.setText("*");
    }

    void failConfirm() {
        confirmFail.setText("*");
    }

    void clearError() {
        usernameFail.setText("");
        passwordFail.setText("");
        confirmFail.setText("");
    }
}
