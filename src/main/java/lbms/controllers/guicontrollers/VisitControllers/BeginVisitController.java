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
 * BeginVisitController class for the Library Book Management System.
 * @author Team B
 */
public class BeginVisitController implements StateController {

    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private TextField visitorIdField;
    @FXML private Text visitorIdFail;
    @FXML private Text failedLabel;

    /**
     * Initializes the state for this instance of the class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                begin();
                e.consume();
            }
        });
    }

    /**
     * Sets the session manager for this class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Begins the visit.
     */
    @FXML
    public void begin() {
        this.visitorIdFail.setText("");
        this.failedLabel.setText("");

        String visitorId = this.visitorIdField.getText();

        if (visitorId.isEmpty()) {
             this.visitorIdFail.setText("*");
             this.failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = String.format("%s,arrive,%s;", this.manager.getClientId(), visitorId);
            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            switch (responseObject.get("message")) {
                case "invalid-id":
                    this.failedLabel.setText("Visitor does not exist.");
                    break;
                case "library-closed":
                    this.failedLabel.setText("Sorry the library is closed, please try again later.");
                    break;
                case "duplicate":
                    this.failedLabel.setText("Visitor is already in the library.");
                default:
                    this.manager.display("visit_begun", "Visit Begun");
                    ((VisitBegunController)this.manager.getController()).setVisit(responseObject);
                    break;
            }
        }
    }

    /**
     * Cancels the request and loads the main employee page.
     */
    @FXML
    public void cancel() {
        this.manager.display("main_employee", this.manager.getUser());
    }
}
