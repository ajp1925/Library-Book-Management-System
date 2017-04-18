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
 * Created by Chris on 4/14/17.
 */
public class EndVisitController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField visitorIdField;
    @FXML private Text visitorIdFail;
    @FXML private Text failedLabel;

    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                end();
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void end() {
        visitorIdFail.setText("");
        failedLabel.setText("");

        String visitorId = visitorIdField.getText();

        if (visitorId.isEmpty()) {
            visitorIdFail.setText("*");
            failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = String.format("%s,depart,%s;", manager.getClientId(), visitorId);
            System.out.println(request); //TODO remove

            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response); //TODO remove

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            if (responseObject.get("message").equals("invalid-id")) {
                failedLabel.setText("Visitor is currently not in the library.");
            } else {
                manager.display("visit_ended", "Visit Ended");
                ((VisitEndedController) manager.getController()).setVisit(responseObject);
            }
        }
    }

    @FXML public void cancel() {
        manager.display("main_employee", this.manager.getUser());
    }
}