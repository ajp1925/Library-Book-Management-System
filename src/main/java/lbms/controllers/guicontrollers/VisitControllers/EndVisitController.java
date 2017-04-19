package lbms.controllers.guicontrollers.VisitControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * EndVisitController class for the Library Book Management System.
 * @author Team b
 */
public class EndVisitController implements StateController {

    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField visitorIdField;
    @FXML private Text visitorIdFail;
    @FXML private Text failedLabel;

    /**
     * Initializes the state for this class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                end();
                e.consume();
            }
        });
    }

    /**
     * Setter for the session manager.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Ends the visit to the library.
     */
    @FXML
    public void end() {
        this.visitorIdFail.setText("");
        this.failedLabel.setText("");

        String visitorId = this.visitorIdField.getText();

        if (visitorId.isEmpty()) {
            this.visitorIdFail.setText("*");
            this.failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = String.format("%s,depart,%s;", this.manager.getClientId(), visitorId);
            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("invalid-id")) {
                failedLabel.setText("Visitor is currently not in the library.");
            } else {
                manager.display("visit_ended", "Visit Ended");
                ((VisitEndedController) manager.getController()).setVisit(responseObject);
            }
        }
    }

    /**
     * Cancels the request and loads the employee main page.
     */
    @FXML
    public void cancel() {
        this.manager.display("main_employee", this.manager.getUser());
    }
}