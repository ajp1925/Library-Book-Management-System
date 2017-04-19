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
import lbms.controllers.guicontrollers.SearchControllers.LibrarySearchController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * MainVisitorController class used for controlling visitors.
 * @author Team B
 */
public class MainVisitorController implements StateController {

    private final static String BEGIN_VISIT_ID = "begin-visit-button";
    private final static String END_VISIT_ID = "end-visit-button";

    private SessionManager manager;

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
     * Initializes the state in this class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search();
                e.consume();
            }
        });

        this.searchByAuthor.setUserData("author");
        this.searchByTitle.setUserData("title");
        this.searchByISBN.setUserData("isbn");
    }

    /**
     * Initializes the manager for the controller.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(final SessionManager manager) {
        this.manager = manager;

        if (ProxyCommandController.inLibrary(manager.getClientId())) {
            this.visitButton.setText("End Visit");
            this.visitButton.setOnAction(e -> endVisit());
            this.visitButton.setId(END_VISIT_ID);
        } else {
            this.visitButton.setText("Begin Visit");
            this.visitButton.setOnAction(e -> beginVisit());
            this.visitButton.setId(BEGIN_VISIT_ID);
        }
    }

    /**
     * Method used for setting up the search bars in the GUI.
     */
    public void search() {
        String author = this.searchAuthorField.getText();
        String title = this.searchTitleField.getText();
        String isbn = this.searchISBNField.getText();
        String type = this.searchTabPane.getSelectionModel().getSelectedItem().getUserData().toString();

        this.manager.display("search_library", "Library Search");
        ((LibrarySearchController)this.manager.getController()).search(type, title, author, isbn);
    }

    /**
     * Begins a visit for the visitor.
     */
    private void beginVisit() {
        String request = String.format("%s,arrive;", this.manager.getClientId());
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
                this.visitButton.setText("End Visit");
                this.visitButton.setOnAction(e -> endVisit());
                this.visitButton.setId(END_VISIT_ID);
                break;
        }
    }

    /**
     * Ends a visit for the visitor.
     */
    private void endVisit() {
        String request = String.format("%s,depart;", this.manager.getClientId());
        String response = new ProxyCommandController().processRequest(request);

        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
        switch (responseObject.get("message")) {
            case "invalid-id":
                this.failedLabel.setText("Visitor is currently not in the library.");
            default:
                this.visitButton.setText("Begin Visit");
                this.visitButton.setOnAction(e -> beginVisit());
                this.visitButton.setId(BEGIN_VISIT_ID);
                break;
        }
    }

    /**
     * Logs out a visitor / employee.
     */
    @FXML
    public void logout() {
        new ProxyCommandController().processRequest(this.manager.getClientId() + ",logout;");
        this.manager.display("login", "Login", false);
    }
}
