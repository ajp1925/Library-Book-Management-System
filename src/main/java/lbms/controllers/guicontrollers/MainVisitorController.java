package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

/**
 * MainVisitorController class used for controlling visitors.
 * @author Team B
 */
public class MainVisitorController implements StateController {

    private SessionManager manager;

    private final static int TITLE_INDEX = 0;
    private final static int AUTHOR_INDEX = 1;
    private final static int ISBN_INDEX = 2;
    private final static String BEGIN_VISIT_ID = "begin-visit-button";
    private final static String END_VISIT_ID = "end-visit-button";

    @FXML private AnchorPane root;
    @FXML private TabPane searchTabPane;
    @FXML private TextField searchTitleField;
    @FXML private TextField searchAuthorField;
    @FXML private TextField searchISBNField;
    @FXML private Button visitButton;

    /**
     * Initializes the visitor controller.
     */
    @FXML protected void initialize() {
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search();
                e.consume();
            }
        });
    }

    /**
     * Initializes the manager for the controller.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(final SessionManager manager) {
        this.manager = manager;
        manager.getTab().setText(manager.getUser());

        if (ProxyCommandController.inLibrary(manager.getClientId())) {
            visitButton.setText("End Visit");
            visitButton.setOnAction(e -> endVisit());
            visitButton.setId(END_VISIT_ID);
        } else {
            visitButton.setText("Begin Visit");
            visitButton.setOnAction(e -> beginVisit());
            visitButton.setId(BEGIN_VISIT_ID);
        }
    }

    /**
     * Search pane method.
     */
    public void search() {
        String search = null;
        switch (searchTabPane.getSelectionModel().getSelectedIndex()) {
            case TITLE_INDEX:
                search = searchTitleField.getText();
                break;
            case AUTHOR_INDEX:
                search = searchAuthorField.getText();
                break;
            case ISBN_INDEX:
                search = searchISBNField.getText();
                break;
            default:
                break;
        }
        System.out.println(search);
    }

    /**
     * Begins a visit for the visitor.
     */
    private void beginVisit() {
        String request = String.format("%s,arrive;", manager.getClientId());
        System.out.println(request); //TODO remove

        String response = new ProxyCommandController().processRequest(request);
        System.out.println(response); //TODO remove

        String[] fields = response.replace(";","").split(",");
        //TODO HashMap<String, String> responseMap = ParseResponseUtility.parseResponse(response);

        switch (fields[2]) {
            case "duplicate":
                break;
            case "invalid-id":
                break;
            default:
                visitButton.setText("End Visit");
                visitButton.setOnAction(e -> endVisit());
                visitButton.setId(END_VISIT_ID);
                break;
        }
    }

    /**
     * Ends a visit for the visitor.
     */
    private void endVisit() {
        String request = String.format("%s,depart;", manager.getClientId());
        System.out.println(request); //TODO remove

        String response = new ProxyCommandController().processRequest(request);
        System.out.println(response); //TODO remove

        String[] fields = response.replace(";","").split(",");
        //TODO HashMap<String, String> responseMap = ParseResponseUtility.parseResponse(response);

        switch (fields[2]) {
            case "invalid-id":
                break;
            default:
                visitButton.setText("Begin Visit");
                visitButton.setOnAction(e -> beginVisit());
                visitButton.setId(BEGIN_VISIT_ID);
                break;
        }
    }
}
