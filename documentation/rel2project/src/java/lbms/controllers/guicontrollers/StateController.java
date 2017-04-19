package lbms.controllers.guicontrollers;

import lbms.views.GUI.SessionManager;

/**
 * StateController interface for the GUI.
 * @author Team B
 */
public interface StateController {

    /**
     * Initialized the manager for the controller.
     * @param manager: the session manager to be set
     */
    void initManager(SessionManager manager);

}
