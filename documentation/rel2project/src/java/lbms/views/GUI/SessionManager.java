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
    public void close(boolean arg) {
        new ProxyCommandController().processRequest(this.clientId + ",logout;");
        new ProxyCommandController().processRequest(this.clientId + ",disconnect;");

        if (arg) {
            this.tab.getTabPane().getTabs().remove(this.tab);
        }
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

    /**
     * Loads a file for the GUI formatting.
     * @param file: the filename
     */
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

    /**
     * Sets the title of the tab.
     * @param title: the title to be set
     */
    private void setTitle(String title) {
        this.tab.setText("Visitor ID: " + this.visitorID + " - " + title);
    }

    /**
     * Sets the title, includes the visitorID if arg is true
     * @param title: the title for the tab
     * @param arg: boolean for including the visitorID
     */
    private void setTitle(String title, boolean arg) {
        if (arg) {
            this.tab.setText(this.visitorID + " - " + title);
        } else {
            this.tab.setText(title);
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

    /**
     * Setter for the user.
     * @param user: the user to be set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter for the user.
     * @return the user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Getter for the controller.
     * @return the controller
     */
    public StateController getController() {
        return this.controller;
    }
}
