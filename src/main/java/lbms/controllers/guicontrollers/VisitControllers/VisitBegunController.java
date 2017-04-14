package lbms.controllers.guicontrollers.VisitControllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * Created by Chris on 4/14/17.
 */
public class VisitBegunController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private Text visitorID;
    @FXML private Text visitDate;
    @FXML private Text visitStartTime;

    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                home();
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void home() {
        manager.display("main_employee", this.manager.getUser());
    }

    @FXML public void setVisit(HashMap<String, String> response) {
        visitorID.setText(response.get("visitorID"));
        visitDate.setText(response.get("visitDate"));
        visitStartTime.setText(response.get("visitStartTime"));
    }
}
