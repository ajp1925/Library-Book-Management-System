package lbms.controllers.guicontrollers.ReturnControllers;

import javafx.fxml.FXML;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

/**
 * BookReturnedController class for the Library Book Management System.
 * @author Team B
 */
public class BookReturnedController implements StateController {

    private SessionManager manager;

    /**
     * Initializes the manager for this class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Loads the home page for the employee that is logged in.
     */
    @FXML
    public void home() {
        this.manager.display("main_employee", this.manager.getUser());
    }
}
