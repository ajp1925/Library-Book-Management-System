package lbms.controllers.guicontrollers;

import lbms.views.gui.SessionManager;

/**
 * StateController interface for the gui.
 * @author Team B
 */
public interface StateController {

    /**
     * Initialized the manager for the controller.
     * @param manager: the session manager to be set
     */
    void initManager(SessionManager manager);

}
