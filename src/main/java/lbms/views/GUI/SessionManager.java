package lbms.views.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;

import java.util.HashMap;

/**
 * SessionManager class for the LBMS.
 * @author Team B
 */
public class SessionManager {

    private Tab tab;
    private Long clientId;
    private StateController controller;
    private String user;

    /**
     * Constructor for a SessionManager object.
     * @param tab: the tab for the session
     */
    public SessionManager(Tab tab) {
        try {
            // parse response
            HashMap<String, String> response = ParseResponseUtility.parseResponse(
                    new ProxyCommandController().processRequest("connect;"));
            this.clientId = Long.parseLong(response.get("clientID"));
        } catch (Exception e) {
            System.out.println("Error connecting to server.");
            System.exit(1);
        }
        this.tab = tab;
        this.user = null;
    }

    /**
     * Closes the session.
     */
    public void close() {
        //TODO logout if logged in
        new ProxyCommandController().processRequest(this.clientId + ",disconnect;");
    }

    /**
     * Displays the session.
     * @param file: the fxml resource
     */
    public void display(String file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SessionManager.class.getResource("/fxml/" + file + ".fxml"));
            this.tab.setContent(loader.load());
            this.controller = loader.getController();
            this.controller.initManager(this);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error loading fxml");
            System.exit(1);
        }
    }

    /**
     * Getter for the client ID.
     * @return the client ID of the session
     */
    public Long getClientId() {
        return this.clientId;
    }

    /**
     * Getter for the tab.
     * @return the tab
     */
    public Tab getTab() {
        return this.tab;
    }

    /**
     * Setter for the user of the session.
     * @param user: the user of the session
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter for the user of the session
     * @return the user
     */
    public String getUser() {
        return this.user;
    }
}
