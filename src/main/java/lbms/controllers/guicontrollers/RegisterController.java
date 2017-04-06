package lbms.controllers.guicontrollers;

import javafx.event.ActionEvent;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 4/5/17.
 */
public class RegisterController implements StateController {
    private SessionManager manager;


    public void initManager(final SessionManager manager) {
        this.manager = manager;
    }
}
