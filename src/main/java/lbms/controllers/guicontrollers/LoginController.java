package lbms.controllers.guicontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lbms.controllers.CommandController;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 3/31/17.
 */
public class LoginController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text loginFailedLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private Text usernameFail;
    @FXML private Text passwordFail;

    @FXML protected void initialize() {
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                e.consume();
            }
        });

        registerLink.setOnAction((ActionEvent event) -> { manager.display("register"); });
    }

    public void initManager(final SessionManager manager) {
        this.manager = manager;
    }

    @FXML private void execute() {
        usernameFail.setText("");
        passwordFail.setText("");
        boolean completed = true;

        if (usernameField.getText().isEmpty()) {
            usernameFail.setText("*");
            completed = false;
        }
        if (passwordField.getText().isEmpty()) {
            passwordFail.setText("*");
            completed = false;
        }

        if (completed) {
            String response = CommandController.processRequest(
                    String.format("%s,login,%s,%s;",
                            manager.getClientId(), usernameField.getText(), passwordField.getText()));

            String[] fields = response.replace(",", "").split(",");

            if (fields[2].equals("success")) {
                manager.display(""); // TODO create main view
            } else {
                loginFailedLabel.setText("Invalid Username or Password. Please Try Again.");
            }
        } else {
            loginFailedLabel.setText("* Please enter missing fields.");
        }
    }
}
