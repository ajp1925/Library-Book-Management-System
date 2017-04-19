package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

/**
 * VisitorRegisteredController class for the GUI of the Library Book Management System.
 * @author Team B
 */
public class VisitorRegisteredController implements StateController {

    private SessionManager manager;

    @FXML private Text visitorID;

    /**
     * Initializes the manager for the session manager.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Displays the create account form from the session manager.
     */
    @FXML
    public void yes() {
        this.manager.display("create", "Create Account");
        ((CreateController)this.manager.getController()).setVisitor(this.visitorID.getText());
    }

    /**
     * Tells the session manager that a computer account will not be created.
     */
    @FXML
    public void no() {
        this.manager.display("main_employee", this.manager.getUser());
    }

    /**
     * Setter for the visitor ID.
     * @param visitorId: the visitor's ID
     */
    public void setVisitor(String visitorId) {
        this.visitorID.setText(visitorId);
    }
}
