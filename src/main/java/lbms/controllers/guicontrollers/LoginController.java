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
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * StateController class used for the state of the GUI.
 * @author Team B
 */
public class LoginController implements StateController {

    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text loginFailedLabel;
    @FXML private Button loginButton;
    @FXML private Text usernameFail;
    @FXML private Text passwordFail;

    /**
     * Initializes the login page.
     */
    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.loginButton.fire();
                e.consume();
            }
        });
    }

    /**
     * Initializes the manager.
     * @param manager: the session manager to be set
     */
    public void initManager(final SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Executes the controller.
     */
    @FXML private void execute() {
        this.usernameFail.setText("");
        this.passwordFail.setText("");
        boolean completed = true;

        if (this.usernameField.getText().isEmpty()) {
            this.usernameFail.setText("*");
            completed = false;
        }
        if (this.passwordField.getText().isEmpty()) {
            this.passwordFail.setText("*");
            completed = false;
        }

        if (completed) {
            try {
                String response = new ProxyCommandController().processRequest(
                        String.format("%s,login,%s,%s;",
                                this.manager.getClientId(), this.usernameField.getText(), this.passwordField.getText()));
                System.out.println(response);       //TODO remove

                HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

                if (responseObject.get("message").equals("success")) {
                    this.manager.setVisitor(ProxyCommandController.getVisitorID(manager.getClientId()));
                    this.manager.setUser(this.usernameField.getText());

                    if (ProxyCommandController.isEmployee(manager.getClientId())) {
                        manager.display("main_employee", manager.getUser());
                    } else {
                        manager.display("main_visitor", manager.getUser());
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                this.loginFailedLabel.setText("Invalid Username or Password. Please Try Again.");
            }
        } else {
            this.loginFailedLabel.setText("* Please enter missing fields.");
        }
    }
}
