package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * MainVisitorController class used for controlling visitors.
 * @author Team B
 */
public class MainVisitorController implements StateController {

    private SessionManager manager;

    private final static String BEGIN_VISIT_ID = "begin-visit-button";
    private final static String END_VISIT_ID = "end-visit-button";

    @FXML private AnchorPane root;
    @FXML private TabPane searchTabPane;
    @FXML private Tab searchByAuthor;
    @FXML private Tab searchByTitle;
    @FXML private Tab searchByISBN;
    @FXML private TextField searchTitleField;
    @FXML private TextField searchAuthorField;
    @FXML private TextField searchISBNField;
    @FXML private Button visitButton;
    @FXML private Text failedLabel;

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

        searchByAuthor.setUserData("author");
        searchByTitle.setUserData("title");
        searchByISBN.setUserData("isbn");
    }

    /**
     * Initializes the manager for the controller.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(final SessionManager manager) {
        this.manager = manager;

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
        String author = searchAuthorField.getText();
        String title = searchTitleField.getText();
        String isbn = searchISBNField.getText();
        String type = searchTabPane.getSelectionModel().getSelectedItem().getUserData().toString();

        manager.display("search_library", "Library Search");
        ((LibrarySearchController)manager.getController()).search(type, title, author, isbn);
    }

    /**
     * Begins a visit for the visitor.
     */
    private void beginVisit() {
        String request = String.format("%s,arrive;", manager.getClientId());
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

        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

        switch (responseObject.get("message")) {
            case "invalid-id":
                failedLabel.setText("Visitor is currently not in the library.");
            default:
                visitButton.setText("Begin Visit");
                visitButton.setOnAction(e -> beginVisit());
                visitButton.setId(BEGIN_VISIT_ID);
                break;
        }
    }

    @FXML public void logout() {
        new ProxyCommandController().processRequest(manager.getClientId() + ",logout;");
        this.manager.display("login", "Login", false);
    }
}
