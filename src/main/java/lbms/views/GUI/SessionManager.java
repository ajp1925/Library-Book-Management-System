package lbms.views.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import lbms.controllers.CommandController;
import lbms.controllers.guicontrollers.StateController;

/**
 * Created by Chris on 4/4/17.
 */
public class SessionManager {
    private Tab tab;
    private String clientId;
    private StateController controller;

    public SessionManager(Tab tab) {
        clientId = CommandController.processRequest(true, "connect;");
        this.tab = tab;
    }

    public void close() {
        // TODO
        // CommandController.processRequest(true, clientId + ",disconnect;");
    }

    public void display(String file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SessionManager.class.getResource("/fxml/" + file + ".fxml"));
            tab.setContent(loader.load());
            controller = loader.getController();
            controller.initManager(this);
        } catch (Exception e) {
            System.out.println("Error loading fxml");
            System.exit(1);
        }
    }

    public String getClientId() {
        return clientId;
    }
}
