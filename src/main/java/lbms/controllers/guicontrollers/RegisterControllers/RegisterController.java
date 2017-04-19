package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
 * RegisterController class for the Library Book Management System.
 * @author Team B
 */
public class RegisterController implements StateController {

    private SessionManager manager;
    private String visitorId = "";

    @FXML private AnchorPane root;
    @FXML private Button registerButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneNumberField;
    @FXML private Text firstNameFail;
    @FXML private Text lastNameFail;
    @FXML private Text addressFail;
    @FXML private Text phoneNumberFail;
    @FXML private Text failedLabel;

    /**
     * Initializes the manager.
     * @param manager: the session manager to be set
     */
    public void initManager(final SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Initializes the state of this class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.registerButton.fire();
                e.consume();
            }
        });
    }

    /**
     * Register method registers a visitor.
     */
    @FXML
    private void register() {
        clearError();
        boolean completed = true;

        String firstName = this.firstNameField.getText();
        String lastName = this.lastNameField.getText();
        String address = this.addressField.getText();
        String phoneNumber = this.phoneNumberField.getText();

        if (firstName.isEmpty()) {
            completed = false;
            this.firstNameFail.setText("*");
        }
        if (lastName.isEmpty()) {
            completed = false;
            this.lastNameFail.setText("*");
        }
        if (address.isEmpty()) {
            completed = false;
            this.addressFail.setText("*");
        }
        if (phoneNumber.isEmpty()) {
            completed = false;
            this.phoneNumberFail.setText("*");
        }

        if (completed) {
            boolean valid = true;

            if (this.visitorId.isEmpty()) {
                try {
                    String request = String.format("%s,register,%s,%s,%s,%s;",
                            this.manager.getClientId(), firstName, lastName, address, phoneNumber);

                    String response = new ProxyCommandController().processRequest(request);

                    HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
                    if (responseObject.get("message").equals("duplicate")) {
                        valid = false;
                        this.failedLabel.setText("This visitor already exists.\nPlease try again "
                                + "or register with visitor ID.");
                    } else if (responseObject.get("message").equals("missing-parameters")) {
                        valid = false;
                        this.failedLabel.setText("One of the fields is invalid. Please try again.");
                    } else {
                        this.visitorId = responseObject.get("visitorID");
                    }
                } catch (Exception e) {
                    valid = false;
                }
            }

            if (valid) {
                this.manager.display("registered_visitor", "Visitor Registered");
                ((VisitorRegisteredController)this.manager.getController()).setVisitor(this.visitorId);
            }
        } else {
            this.failedLabel.setText("* Please enter missing fields.");
        }
    }

    /**
     * Tells the session manager to cancel the last action.
     */
    @FXML
    public void cancel() {
        this.manager.display("main_employee", this.manager.getUser());
    }

    /**
     * Clears the error with the form.
     */
    private void clearError() {
        this.firstNameFail.setText("");
        this.lastNameFail.setText("");
        this.addressFail.setText("");
        this.phoneNumberFail.setText("");
    }
}
