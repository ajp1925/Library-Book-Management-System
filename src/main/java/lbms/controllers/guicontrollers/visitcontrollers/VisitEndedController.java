package lbms.controllers.guicontrollers.visitcontrollers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.gui.SessionManager;

import java.util.HashMap;

/**
 * VisitEndedController class for the Library Book Management System.
 * @author Team B
 */
public class VisitEndedController implements StateController {

    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private Text visitorID;
    @FXML private Text visitEndTime;
    @FXML private Text visitDuration;

    /**
     * Initializes the state for this class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                home();
                e.consume();
            }
        });
    }

    /**
     * Setter for the session manager.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Goes to the home page for an employee.
     */
    @FXML
    public void home() {
        this.manager.display("main_employee", this.manager.getUser());
    }

    /**
     * Sets the visit.
     * @param response: input from the parse response utility
     */
    @FXML
    public void setVisit(HashMap<String, String> response) {
        this.visitorID.setText(response.get("visitorID"));
        this.visitEndTime.setText(response.get("visitEndTime"));
        this.visitDuration.setText(response.get("visitDuration"));
    }
}
