package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 4/18/17.
 */
public class BookReturnedController implements StateController {
    private SessionManager manager;

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void home() {
        manager.display("main_employee", manager.getUser());
    }
}
