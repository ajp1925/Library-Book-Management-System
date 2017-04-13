package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * CreateController class for the Library Book Management System.
 * @author Team B
 */
public class CreateController implements StateController{
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField visitorField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private RadioButton visitorButton;
    @FXML private RadioButton employeeButton;
    @FXML private Text visitorFail;
    @FXML private Text usernameFail;
    @FXML private Text passwordFail;
    @FXML private Text confirmFail;
    @FXML private Text roleFail;
    @FXML private Text failedLabel;
    @FXML private Button createButton;

    private ToggleGroup group;

    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.createButton.fire();
                e.consume();
            }
        });

        group = new ToggleGroup();
        visitorButton.setToggleGroup(group);
        visitorButton.setUserData("visitor");
        employeeButton.setToggleGroup(group);
        employeeButton.setUserData("employee");
        visitorButton.setSelected(true);
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    public void setVisitor(String visitorID) {
        visitorField.setText(visitorID);
    }

    /**
     * Submit function used when the submit button is pressed.
     */
    @FXML private void create() {
        clearError();
        boolean completed = true;

        String visitor = visitorField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmField.getText();
        String role = group.getSelectedToggle().getUserData().toString();

        if (visitor.isEmpty()) {
            completed = false;
            visitorFail.setText("*");
        }
        if (username.isEmpty()) {
            completed = false;
            usernameFail.setText("*");
        }
        if (password.isEmpty()) {
            completed = false;
            passwordFail.setText("*");
        }
        if (confirm.isEmpty()) {
            completed = false;
            confirmFail.setText("*");
        }
        if (role.isEmpty()) {
            completed = false;
            roleFail.setText("*");
        }

        if (!completed) {
            this.failedLabel.setText("* Please enter missing fields.");
        } else if (!password.equals(confirm)) {
            this.failedLabel.setText("Passwords do not match.\nPlease enter your password again.");
        } else {
            boolean valid;
            try {
                String request = String.format("%s,create,%s,%s,%s,%s;",
                        this.manager.getClientId(), username, password, role, visitor);
                System.out.println(request);        // TODO remove

                String response = new ProxyCommandController().processRequest(request);
                System.out.println(response);       // TODO remove

                HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

                switch (responseObject.get("message")) {
                    case "duplicate-username":
                        this.failedLabel.setText("Username is taken. Please try a new username.");
                        valid = false;
                        break;
                    case "duplicate-visitor":
                        this.failedLabel.setText("Visitor already has an account. Please try logging in.");
                        valid = false;
                        break;
                    case "invalid-visitor":
                        this.failedLabel.setText("No visitor exists. Please register as a visitor first.");
                        valid = false;
                        break;
                    default:
                        valid = true;
                        break;
                }
            } catch (Exception e) {
                valid = false;
            }

            if (valid) {
                manager.display("created_account", "Account Created");
                ((AccountCreatedController)manager.getController()).setUsername(username);
            }
        }
    }

    @FXML public void cancel() {
        manager.display("main_employee", manager.getUser());
    }

    /**
     * Clears all the fields by setting them to empty strings.
     */
    private void clearError() {
        this.visitorFail.setText("");
        this.usernameFail.setText("");
        this.passwordFail.setText("");
        this.confirmFail.setText("");
        this.roleFail.setText("");
    }
}
