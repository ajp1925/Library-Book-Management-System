package lbms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by Chris on 3/31/17.
 */
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text loginFailedLabel;

    @FXML private void login() {
        // TODO edit request string
        String request = usernameField.getText() + passwordField.getText();
        String response = CommandController.processRequest(true, request);

        // TODO parse response

        if (true) {
//            try {
//                ClientController.change(GUIView.loadFXML("/fxml/main/fxml"));
//            } catch (Exception e) {
//                System.out.printf("Error loading fxml file");
//                System.exit(1);
//            }

            loginFailedLabel.setFill(Color.GREEN);
            loginFailedLabel.setText("Success");
        } else {
            loginFailedLabel.setFill(Color.FIREBRICK);
            loginFailedLabel.setText("Invalid Username or Password. Please Try Again.");
        }
    }

}
