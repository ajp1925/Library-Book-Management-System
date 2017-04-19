package lbms.controllers.guicontrollers.registercontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.gui.SessionManager;

import java.util.HashMap;

/**
 * CreateController class for the Library Book Management System.
 * @author Team B
 */
public class CreateController implements StateController {

    private SessionManager manager;
    private ToggleGroup group;

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

    /**
     * Initializes the controller.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.createButton.fire();
                e.consume();
            }
        });

        this.group = new ToggleGroup();
        this.visitorButton.setToggleGroup(this.group);
        this.visitorButton.setUserData("visitor");
        this.employeeButton.setToggleGroup(this.group);
        this.employeeButton.setUserData("employee");
        this.visitorButton.setSelected(true);
    }

    /**
     * Sets the session manager for this class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Setter for the text of the visitor.
     * @param visitorID: the id of the visitor
     */
    public void setVisitor(String visitorID) {
        this.visitorField.setText(visitorID);
    }

    /**
     * Submit function used when the submit button is pressed.
     */
    @FXML
    private void create() {
        clearError();
        boolean completed = true;

        String visitor = this.visitorField.getText();
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        String confirm = this.confirmField.getText();
        String role = this.group.getSelectedToggle().getUserData().toString();

        if (visitor.isEmpty()) {
            completed = false;
            this.visitorFail.setText("*");
        }
        if (username.isEmpty()) {
            completed = false;
            this.usernameFail.setText("*");
        }
        if (password.isEmpty()) {
            completed = false;
            this.passwordFail.setText("*");
        }
        if (confirm.isEmpty()) {
            completed = false;
            this.confirmFail.setText("*");
        }
        if (role.isEmpty()) {
            completed = false;
            this.roleFail.setText("*");
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

                String response = new ProxyCommandController().processRequest(request);

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
                this.manager.display("created_account", "Account Created");
                ((AccountCreatedController)this.manager.getController()).setUsername(username);
            }
        }
    }

    /**
     * Tells the manager to cancel the last action.
     */
    @FXML
    public void cancel() {
        this.manager.display("main_employee", this.manager.getUser());
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
