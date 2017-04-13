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
    @FXML protected void initialize() {
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
    @FXML private void register() {
        clearError();
        boolean completed = true;

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (firstName.isEmpty()) {
            completed = false;
            firstNameFail.setText("*");
        }
        if (lastName.isEmpty()) {
            completed = false;
            lastNameFail.setText("*");
        }
        if (address.isEmpty()) {
            completed = false;
            addressFail.setText("*");
        }
        if (phoneNumber.isEmpty()) {
            completed = false;
            phoneNumberFail.setText("*");
        }

        if (completed) {
            boolean valid = true;

            if (this.visitorId.isEmpty()) {
                try {
                    String request = String.format("%s,register,%s,%s,%s,%s;",
                            this.manager.getClientId(), firstName, lastName, address, phoneNumber);
                    System.out.println(request);        // TODO remove

                    String response = new ProxyCommandController().processRequest(request);
                    System.out.println(response);       // TODO remove

                    HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

                    if (responseObject.get("message").equals("duplicate")) {
                        valid = false;
                        this.failedLabel.setText("This visitor already exists.\nPlease try again or register with visitor ID.");
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
                manager.display("registered_visitor", "Visitor Registered");
                ((VisitorRegisteredController)manager.getController()).setVisitor(visitorId);
            }
        } else {
            this.failedLabel.setText("* Please enter missing fields.");
        }
    }

    @FXML public void cancel() {
        manager.display("main_employee", manager.getUser());
    }

    private void clearError() {
        this.firstNameFail.setText("");
        this.lastNameFail.setText("");
        this.addressFail.setText("");
        this.phoneNumberFail.setText("");
    }
}
