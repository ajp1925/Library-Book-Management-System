package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;


/**
 * RegisterController class for the Library Book Management System.
 * @author Team B
 */
public class RegisterController implements StateController {

    private SessionManager manager;
    private UserController userController;
    private CreateController controller;
    private String visitorId = "";

    @FXML private AnchorPane root;
    @FXML private FlowPane centerPane;
    @FXML private VBox centerBox;
    @FXML private Text header;
    @FXML private Button submitButton;
    @FXML private Hyperlink loginLink;
    @FXML private Text failedLabel;
    @FXML private RadioButton newUserRadio;
    @FXML private RadioButton existingUserRadio;

    /**
     * Initializes the manager.
     * @param manager: the session manager to be set
     */
    public void initManager(final SessionManager manager) {
        this.manager = manager;
        manager.getTab().setText("Register User");
    }

    /**
     * Initializes the state of this class.
     */
    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.submitButton.fire();
                e.consume();
            }
        });

        this.loginLink.setOnAction(e -> this.manager.display("login"));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SessionManager.class.getResource("/fxml/new_user.fxml"));
            GridPane grid = loader.load();
            this.centerBox.getChildren().set(1, grid);
            this.userController = loader.getController();
        } catch (Exception e) {
            System.out.println("Error loading fxml");
            System.exit(1);
        }

        ToggleGroup group = new ToggleGroup();
        this.newUserRadio.setSelected(true);
        this.newUserRadio.setToggleGroup(group);
        this.newUserRadio.setUserData("new_user");
        this.existingUserRadio.setToggleGroup(group);
        this.existingUserRadio.setUserData("existing_user");

        group.selectedToggleProperty().addListener((observable, old, selected) -> {
            String file = group.getSelectedToggle().getUserData().toString();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SessionManager.class.getResource("/fxml/" + file + ".fxml"));
                GridPane grid = loader.load();
                this.centerBox.getChildren().set(1, grid);
                this.userController = loader.getController();

                this.failedLabel.setText("");
            } catch (Exception e) {
                System.out.println("Error loading fxml");
                System.exit(1);
            }

            if (this.userController.isNew()) {
                this.manager.getTab().setText("Register User");
            } else {
                this.manager.getTab().setText("Existing User");
            }
        });
    }

    /**
     * Register method registers a visitor.
     */
    @FXML private void register() {
        this.userController.clearError();
        boolean completed = true;

        String firstName = "";
        String lastName = "";
        String address = "";
        String phoneNumber = "";

        if (this.userController.isNew()) {
            ArrayList<String> fields = this.userController.getFields();
            firstName = fields.get(0);
            lastName = fields.get(1);
            address = fields.get(2);
            phoneNumber = fields.get(3);

            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
                completed = false;
            }
        } else {
            String temp;
            temp = this.userController.getFields().get(0);

            if (temp.isEmpty()) {
                completed = false;
            } else {
                this.visitorId = temp;
            }
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

                    String[] fields = response.split(",");
                    // TODO HashMap<String, String> responseMap = ParseResponseUtility.parseResponse(response);

                    if (fields[2].equals("duplicate;")) {
                        valid = false;
                    } else {
                        this.visitorId = fields[2];
                    }
                } catch (Exception e) {
                    valid = false;
                }
            }

            if (valid) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/create.fxml"));
                    GridPane grid = loader.load();
                    this.controller = loader.getController();
                    this.manager.getTab().setText("Create Account");
                    this.header.setText("Create Account");

                    this.centerPane.getChildren().clear();
                    this.centerPane.getChildren().add(grid);
                } catch (Exception e) {
                    System.out.println("Error loading fxml");
                    System.exit(1);
                }

                this.submitButton.setText("Submit");
                this.submitButton.setOnAction(e -> submit());

                this.failedLabel.setText("");
            } else {
                this.failedLabel.setText("This visitor already exists.\nPlease try again or register with visitor ID.");
            }
        } else {
            this.userController.fail();
            this.failedLabel.setText("* Please enter missing fields.");
        }
    }

    /**
     * Submit function used when the submit button is pressed.
     */
    @FXML private void submit() {
        this.controller.clearError();
        boolean completed = true;

        String username = this.controller.getUsernameField();
        String password = this.controller.getPasswordField();
        String confirm = this.controller.getConfirmField();

        if (username.isEmpty()) {
            completed = false;
            this.controller.failUsername();
        }
        if (password.isEmpty()) {
            completed = false;
            this.controller.failPassword();
        }
        if (confirm.isEmpty()) {
            completed = false;
            this.controller.failConfirm();
        }

        if (!completed) {
            this.failedLabel.setText("* Please enter missing fields.");
        } else if (!password.equals(confirm)) {
            this.failedLabel.setText("Passwords do not match.\nPlease enter your password again.");
        } else {
            boolean valid;
            try {
                String request = String.format("%s,create,%s,%s,%s,%s;",
                        this.manager.getClientId(), username, password, "visitor", this.visitorId);
                System.out.println(request);        // TODO remove

                String response = new ProxyCommandController().processRequest(request);
                System.out.println(response);       // TODO remove

                String[] fields = response.replace(";", "").split(",");
                //TODO HashMap<String, String> responseMap = ParseResponseUtility.parseResponse(response);

                switch (fields[1]) {
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
                try {
                    String request = String.format("%s,login,%s,%s;",
                            this.manager.getClientId(), username, password);
                    System.out.println(request);        // TODO remove

                    String response = new ProxyCommandController().processRequest(request);
                    System.out.println(response);       // TODO remove

                    String[] fields = response.replace(";", "").split(",");
                    //TODO HashMap<String, String> responseMap = ParseResponseUtility.parseResponse(response);

                    if (fields[2].equals("success")) {
                        this.manager.setUser(username);

                        if (ProxyCommandController.isEmployee(this.manager.getClientId())) {
                            this.manager.display("main_employee");
                        } else {
                            this.manager.display("main_visitor");
                        }
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    this.failedLabel.setText("Invalid Username or Password. Please Try Again.");
                }
            }
        }
    }
}
