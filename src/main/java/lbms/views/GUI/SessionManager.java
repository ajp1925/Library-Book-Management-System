package lbms.views.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;

/**
 * Created by Chris on 4/4/17.
 */
public class SessionManager {
    private Tab tab;
    private Long clientId;
    private StateController controller;

    public SessionManager(Tab tab) {
        try {
            String[] fields = new ProxyCommandController().processRequest("connect;").replace(";", "").split(",", 2);
            clientId = Long.parseLong(fields[1]);
        } catch (Exception e) {
            System.out.println("Error connecting to server.");
            System.exit(1);
        }
        this.tab = tab;
    }

    public void close() {
        new ProxyCommandController().processRequest(clientId + ",disconnect;");
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

    public Long getClientId() {
        return clientId;
    }
}
