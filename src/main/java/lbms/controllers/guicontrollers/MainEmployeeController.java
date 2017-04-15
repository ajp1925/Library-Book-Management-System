package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

/**
 * MainEmployeeController class for the GUI of the Library Book Management System.
 * @author Team B TODO comment all these methods
 */
public class MainEmployeeController implements StateController {

    private SessionManager manager;

    @FXML private Text failedLabel;

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void search() {

    }

    @FXML public void searchStore() {

    }

    @FXML public void beginVisit() {
        if (ProxyCommandController.isOpen()) {
            this.manager.display("begin_visit", "Begin Visit");
        } else {
            failedLabel.setText("Sorry the library is closed, please try again later.");
        }
    }

    @FXML public void endVisit() {
        if (ProxyCommandController.isOpen()) {
            this.manager.display("end_visit", "End Visit");
        } else {
            failedLabel.setText("Sorry the library is closed, please try again later.");
        }
    }

    @FXML public void returnBook() {

    }

    @FXML public void register() {
        this.manager.display("register", "Register Visitor");
    }

    @FXML public void create() {
        this.manager.display("create", "Create Account");
    }

    @FXML public void settings() { this.manager.display("settings", "System Settings");}

    @FXML public void logout() {
        new ProxyCommandController().processRequest(manager.getClientId() + ",logout;");
        this.manager.display("login", "Login", false);
    }
}
