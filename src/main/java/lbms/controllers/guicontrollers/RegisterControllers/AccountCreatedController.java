package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 4/13/17.
 */
public class AccountCreatedController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private Text username;
    @FXML private Button finishButton;

    @FXML protected void initialize() {
        finishButton.defaultButtonProperty().bind(finishButton.focusedProperty());

        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.finishButton.fire();
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void home() {
        manager.display("main_employee", manager.getUser());
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }
}
