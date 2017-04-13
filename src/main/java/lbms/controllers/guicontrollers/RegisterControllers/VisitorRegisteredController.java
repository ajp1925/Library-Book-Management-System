package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 4/13/17.
 */
public class VisitorRegisteredController implements StateController {
    private SessionManager manager;

    @FXML
    private Text visitorID;

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void yes() {
        manager.display("create", "Create Account");
        ((CreateController)manager.getController()).setVisitor(visitorID.getText());
    }

    @FXML public void no() {
        manager.display("main_employee", manager.getUser());
    }

    public void setVisitor(String visitorId) {
        this.visitorID.setText(visitorId);
    }
}
