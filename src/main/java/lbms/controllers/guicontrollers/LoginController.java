package lbms.controllers.guicontrollers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import lbms.views.GUIView;

/**
 * Created by Chris on 3/31/17.
 */
public class LoginController {
    @FXML private AnchorPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text loginFailedLabel;
    @FXML private Button loginButton;

    @FXML protected void initialize() {
        CommandController.processRequest(true, "connect;");

        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                e.consume();
            }
        });
    }

    @FXML private void execute() {
        // TODO edit request string
        String request = usernameField.getText() + passwordField.getText();
        String response = CommandController.processRequest(true, request);

        // TODO parse response

        if (true) {
//            try {
//                ClientController.change();
//            } catch (Exception e) {
//                System.out.printf("Error loading fxml file");
//                System.exit(1);
//            }
            //GUIView.update();

            //parent.getTabs().getSelectionModel().getSelectedItem().setContent(GUIView);
            loginFailedLabel.setFill(Color.GREEN);
            loginFailedLabel.setText("Success");
        } else {
            loginFailedLabel.setFill(Color.FIREBRICK);
            loginFailedLabel.setText("Invalid Username or Password. Please Try Again.");
        }
    }
}
