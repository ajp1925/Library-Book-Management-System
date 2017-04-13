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
    private Long visitorID;
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
        this.visitorID = null;
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
     * @param title: title for the tab
     */
    public void display(String file, String title) {
        load(file);
        setTitle(title);
    }

    /**
     * Displays the session.
     * @param file: the fxml resource
     * @param title: title for the tab
     * @param flag: flag whether to include visitor ID or not
     */
    public void display(String file, String title, boolean flag) {
        load(file);
        setTitle(title, flag);
    }

    private void load(String file) {
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

    private void setTitle(String title) {
        tab.setText(visitorID + " - " + title);

    }

    private void setTitle(String title, boolean arg) {
        if (arg) {
            tab.setText(visitorID + " - " + title);
        } else {
            tab.setText(title);
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
     * Setter for the user of the session.
     * @param visitorID: the visitorID of visitor connected to the session
     */
    public void setVisitor(Long visitorID) {
        this.visitorID = visitorID;
    }

    /**
     * Getter for the user of the session
     * @return the user
     */
    public Long getVisitor() {
        return this.visitorID;
    }

    public void setUser(String user) { this.user = user; }

    public String getUser() { return user; }

    public StateController getController() { return this.controller; }
}
