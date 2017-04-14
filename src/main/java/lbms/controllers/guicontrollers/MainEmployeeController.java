package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

/**
 * MainEmployeeController class for the GUI of the Library Book Management System.
 * @author Team B TODO comment all these methods
 */
public class MainEmployeeController implements StateController {

    private SessionManager manager;

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void search() {

    }

    @FXML public void searchStore() {

    }

    @FXML public void beginVisit() {

    }

    @FXML public void endVisit() {

    }

    @FXML public void register() {
        this.manager.display("register", "Register Visitor");
    }

    @FXML public void create() {
        this.manager.display("create", "Create Account");
    }

    @FXML public void logout() {
        new ProxyCommandController().processRequest(manager.getClientId() + ",logout;");
        this.manager.display("login", "Login");
    }
}
