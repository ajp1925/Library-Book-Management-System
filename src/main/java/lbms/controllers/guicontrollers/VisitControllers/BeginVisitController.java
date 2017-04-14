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
public class BeginVisitController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField visitorIdField;
    @FXML private Text visitorIdFail;
    @FXML private Text failedLabel;

    @FXML protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                begin();
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void begin() {
        visitorIdFail.setText("");
        failedLabel.setText("");

        String visitorId = visitorIdField.getText();

        if (visitorId.isEmpty()) {
             visitorIdFail.setText("*");
             failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = String.format("%s,arrive,%s;", manager.getClientId(), visitorId);
            System.out.println(request); //TODO remove

            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response); //TODO remove

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            switch (responseObject.get("message")) {
                case "invalid-id":
                    failedLabel.setText("Visitor does not exist.");
                    break;
                case "library-closed":
                    failedLabel.setText("Sorry the library is closed, please try again later.");
                    break;
                case "duplicate":
                    failedLabel.setText("Visitor is already in the library.");
                default:
                    manager.display("visit_begun", "Visit Begun");
                    ((VisitBegunController)manager.getController()).setVisit(responseObject);
                    break;
            }
        }
    }

    @FXML public void cancel() {
        manager.display("main_employee", this.manager.getUser());
    }
}
