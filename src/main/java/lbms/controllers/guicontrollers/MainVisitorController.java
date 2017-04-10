package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import lbms.views.GUI.SessionManager;

/**
 * Created by Chris on 4/9/2017.
 */
public class MainVisitorController implements StateController{
    private SessionManager manager;

    @Override
    public void initManager(final SessionManager manager) {
        this.manager = manager;
        manager.getTab().setText(manager.getUser());
    }
}
