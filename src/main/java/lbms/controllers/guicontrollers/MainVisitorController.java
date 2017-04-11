package lbms.controllers.guicontrollers;

import lbms.views.GUI.SessionManager;

/**
 * MainVisitorController class used for controlling visitors.
 * @author Team B
 */
public class MainVisitorController implements StateController {

    private SessionManager manager;

    /**
     * Initializes the manager for the controller.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(final SessionManager manager) {
        this.manager = manager;
        manager.getTab().setText(manager.getUser());
    }
}
