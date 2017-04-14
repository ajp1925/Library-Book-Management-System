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
 * AccountCreatedController for the GUI part of the Library Book Management System.
 * @author Team B
 */
public class AccountCreatedController implements StateController {

    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private Text username;

    /**
     * Initializes the controller.
     */
    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                home();
                e.consume();
            }
        });
    }

    /**
     * Initializes the manager for this class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Tells the manager to display the home stage.
     */
    @FXML public void home() {
        this.manager.display("main_employee", this.manager.getUser());
    }

    /**
     * Sets the username for this class.
     * @param username: the username to be set
     */
    public void setUsername(String username) {
        this.username.setText(username);
    }
}
