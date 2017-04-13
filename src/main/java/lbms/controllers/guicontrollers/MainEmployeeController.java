package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 4/12/17.
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
        manager.display("register", "Register Visitor");
    }

    @FXML public void create() { manager.display("create", "Create Account"); }
}
