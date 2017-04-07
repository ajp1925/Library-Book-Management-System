package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lbms.controllers.CommandController;
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
    @FXML private Button submitButton;
    @FXML private Hyperlink loginLink;
    @FXML private Text failedLabel;

    @FXML private RadioButton newUserRadio;
    @FXML private RadioButton existingUserRadio;

    private UserController userController;
    private Register2Controller controller;

    private String visitorId = "";

    public void initManager(final SessionManager manager) {
        this.manager = manager;
    }

    @FXML protected void initialize() {
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                submitButton.fire();
                e.consume();
            }
        });

        loginLink.setOnAction((ActionEvent event) -> {
            manager.display("login");
        });

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
        });
    }

    @FXML private void next() {
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
                String response = CommandController.processRequest(
                        String.format("%s,register,%s,%s,%s,%s;",
                                manager.getClientId(), firstName, lastName, address, phoneNumber));

                String[] fields = response.replace(";", "").split(",");
                if(fields[1].equals("duplicate;")) {
                    valid = false;
                } else {
                    visitorId = fields[1];
                }
            }

            if (valid) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/register_2.fxml"));
                    GridPane grid = loader.load();
                    controller = loader.getController();

                    centerPane.getChildren().clear();
                    centerPane.getChildren().add(grid);
                } catch (Exception e) {
                    System.out.println("Error loading fxml");
                    System.exit(1);
                }

                submitButton.setText("Submit");
                submitButton.setOnAction((ActionEvent event) -> {
                    submit();
                });

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
        String role = controller.getSelected();

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
        if (role.isEmpty()) {
            completed = false;
            controller.failRole();
        }

        if (!completed) {
            failedLabel.setText("* Please enter missing fields.");
        }
        else if (!password.equals(confirm)) {
            failedLabel.setText("Passwords do not match.\nPlease enter your password again.");
        } else {
            boolean valid;
            String response = CommandController.processRequest(
                    String.format("%s,create,%s,%s,%s,%s;",
                            manager.getClientId(), username, password, role, visitorId));

            String[] fields = response.replace(";", "").split(",");
            switch (fields[2]) {
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

            if (valid) {
                response = CommandController.processRequest(
                        String.format("%s,login,%s,%s;",
                                manager.getClientId(), username, password));

                fields = response.replace(",", "").split(",");

                if (fields[2].equals("success")) {
                    System.out.println("SUCCESS");
                    manager.display("");    //TODO enter main view file
                } else {
                    failedLabel.setText("Invalid Username or Password. Please Try Again.");
                }
            }
        }
    }
}
