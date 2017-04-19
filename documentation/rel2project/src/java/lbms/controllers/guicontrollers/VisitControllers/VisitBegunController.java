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
 * VisitBegunController class for the Library Book Management System.
 * @author Team B
 */
public class VisitBegunController implements StateController {

    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private Text visitorID;
    @FXML private Text visitDate;
    @FXML private Text visitStartTime;

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
     * Setter for the session manager for this class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Goes back to the home page.
     */
    @FXML
    public void home() {
        this.manager.display("main_employee", this.manager.getUser());
    }

    /**
     * Sets the visit in the library book management system.
     * @param response: input from the parse response utility
     */
    @FXML
    public void setVisit(HashMap<String, String> response) {
        this.visitorID.setText(response.get("visitorID"));
        this.visitDate.setText(response.get("visitDate"));
        this.visitStartTime.setText(response.get("visitStartTime"));
    }
}
