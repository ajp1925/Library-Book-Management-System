package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;


/**
 * Created by Chris on 4/5/17.
 */
public class RegisterController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private FlowPane centerPane;
    @FXML private VBox centerBox;
    @FXML private Text header;
    @FXML private Button submitButton;
    @FXML private Hyperlink loginLink;
    @FXML private Text failedLabel;

    @FXML private RadioButton newUserRadio;
    @FXML private RadioButton existingUserRadio;

    private UserController userController;
    private CreateController controller;

    private String visitorId = "";

    public void initManager(final SessionManager manager) {
        this.manager = manager;
        manager.getTab().setText("Register User");
    }

    @FXML protected void initialize() {
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                submitButton.fire();
                e.consume();
            }
        });

        loginLink.setOnAction(e -> manager.display("login"));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SessionManager.class.getResource("/fxml/new_user.fxml"));
            GridPane grid = loader.load();
            centerBox.getChildren().set(1, grid);
            userController = loader.getController();
        } catch (Exception e) {
            System.out.println("Error loading fxml");
            System.exit(1);
        }

        ToggleGroup group = new ToggleGroup();
        newUserRadio.setSelected(true);
        newUserRadio.setToggleGroup(group);
        newUserRadio.setUserData("new_user");
        existingUserRadio.setToggleGroup(group);
        existingUserRadio.setUserData("existing_user");

        group.selectedToggleProperty().addListener((observable, old, selected) -> {
            String file = group.getSelectedToggle().getUserData().toString();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SessionManager.class.getResource("/fxml/" + file + ".fxml"));
                GridPane grid = loader.load();
                centerBox.getChildren().set(1, grid);
                userController = loader.getController();

                failedLabel.setText("");
            } catch (Exception e) {
                System.out.println("Error loading fxml");
                System.exit(1);
            }

            if (userController.isNew()) {
                manager.getTab().setText("Register User");
            } else {
                manager.getTab().setText("Existing User");
            }
        });
    }

    @FXML private void register() {
        userController.clearError();
        boolean completed = true;

        String firstName = "";
        String lastName = "";
        String address = "";
        String phoneNumber = "";

        if (userController.isNew()) {
            ArrayList<String> fields = userController.getFields();
            firstName = fields.get(0);
            lastName = fields.get(1);
            address = fields.get(2);
            phoneNumber = fields.get(3);

            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
                completed = false;
            }
        } else {
            String temp;
            temp = userController.getFields().get(0);

            if (temp.isEmpty()) {
                completed = false;
            } else {
                visitorId = temp;
            }
        }

        if (completed) {
            boolean valid = true;

            if (visitorId.isEmpty()) {
                try {
                    String request = String.format("%s,register,%s,%s,%s,%s;",
                            manager.getClientId(), firstName, lastName, address, phoneNumber);
                    System.out.println(request);        // TODO remove

                    String response = new ProxyCommandController().processRequest(request);
                    System.out.println(response);       // TODO remove

                    // parse response
                    String[] fields = response.split(",");
                    if (fields[2].equals("duplicate;")) {
                        valid = false;
                    } else {
                        visitorId = fields[2];
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
                    controller = loader.getController();
                    manager.getTab().setText("Create Account");
                    header.setText("Create Account");

                    centerPane.getChildren().clear();
                    centerPane.getChildren().add(grid);
                } catch (Exception e) {
                    System.out.println("Error loading fxml");
                    System.exit(1);
                }

                submitButton.setText("Submit");
                submitButton.setOnAction(e -> submit());

                failedLabel.setText("");
            } else {
                failedLabel.setText("This visitor already exists.\nPlease try again or register with visitor ID.");
            }
        } else {
            userController.fail();
            failedLabel.setText("* Please enter missing fields.");
        }
    }

    @FXML private void submit() {
        controller.clearError();
        boolean completed = true;

        String username = controller.getUsernameField();
        String password = controller.getPasswordField();
        String confirm = controller.getConfirmField();

        if (username.isEmpty()) {
            completed = false;
            controller.failUsername();
        }
        if (password.isEmpty()) {
            completed = false;
            controller.failPassword();
        }
        if (confirm.isEmpty()) {
            completed = false;
            controller.failConfirm();
        }

        if (!completed) {
            failedLabel.setText("* Please enter missing fields.");
        }
        else if (!password.equals(confirm)) {
            failedLabel.setText("Passwords do not match.\nPlease enter your password again.");
        } else {
            boolean valid;
            try {
                String request = String.format("%s,create,%s,%s,%s,%s;",
                        manager.getClientId(), username, password, "visitor", visitorId);
                System.out.println(request);        // TODO remove

                String response = new ProxyCommandController().processRequest(request);
                System.out.println(response);       // TODO remove

                // parse response
                String[] fields = response.replace(";", "").split(",");
                switch (fields[1]) {
                    case "duplicate-username":
                        failedLabel.setText("Username is taken. Please try a new username.");
                        valid = false;
                        break;
                    case "duplicate-visitor":
                        failedLabel.setText("Visitor already has an account. Please try logging in.");
                        valid = false;
                        break;
                    case "invalid-visitor":
                        failedLabel.setText("No visitor exists. Please register as a visitor first.");
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
                            manager.getClientId(), username, password);
                    System.out.println(request);        // TODO remove

                    String response = new ProxyCommandController().processRequest(request);
                    System.out.println(response);       // TODO remove

                    // parse response
                    String[] fields = response.replace(";", "").split(",");

                    if (fields[2].equals("success")) {
                        manager.setUser(username);

                        if (ProxyCommandController.isEmployee(manager.getClientId())) {
                            manager.display("main_employee");
                        } else {
                            manager.display("main_visitor");
                        }
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    failedLabel.setText("Invalid Username or Password. Please Try Again.");
                }
            }
        }
    }
}
