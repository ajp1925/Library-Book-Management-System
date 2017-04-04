package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML protected void initialize() {
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                e.consume();
            }
        });
    }

    public void initManager(final SessionManager manager) {
        this.manager = manager;
    }

    @FXML private void execute() {
        // TODO edit request string
        String request = manager.getClientId() + usernameField.getText() + passwordField.getText();
        String response = CommandController.processRequest(true, request);

        // TODO parse response

        if (true) {
            //manager.display("main_visitor"); // TODO create main view
            loginFailedLabel.setFill(Color.GREEN);
            loginFailedLabel.setText("Success");
        } else {
            loginFailedLabel.setFill(Color.FIREBRICK);
            loginFailedLabel.setText("Invalid Username or Password. Please Try Again.");
        }
    }
}
