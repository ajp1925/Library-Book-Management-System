package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * StoreSearchController class for the Library Book Management System.
 * @author Team B
 */
public class StoreSearchController implements StateController {

    private SessionManager manager;

    @FXML private VBox results;
    @FXML private Text noResultsLabel;
    @FXML private TextField titleField, authorField, isbnField;
    @FXML private RadioButton localStore, googleStore;

    /**
     * Initializes the data for this class.
     */
    @FXML
    protected void initialize() {
        ToggleGroup group = new ToggleGroup();

        localStore.setToggleGroup(group);
        localStore.setUserData("local");
        localStore.setOnAction(e -> service(localStore));

        googleStore.setToggleGroup(group);
        googleStore.setUserData("google");
        googleStore.setOnAction(e -> service(googleStore));

        titleField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search("title", this.titleField.getText(), "", "");
                e.consume();
            }
        });
        this.authorField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search("author", "", this.authorField.getText(), "");
                e.consume();
            }
        });
        this.isbnField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search("isbn", "", "", this.isbnField.getText());
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

        if (ProxyCommandController.getStore(manager.getClientId()).equals("local")) {
            localStore.setSelected(true);
        } else {
            googleStore.setSelected(true);
        }
    }

    /**
     * Creates a string request, processes it, and displays the result.
     * @param type: the type of search
     * @param title: the title for the search
     * @param author: the author for the search
     * @param isbn: the isbn for the search
     */
    public void search(String type, String title, String author, String isbn) {
        String request;
        switch (type) {
            case "author":
                request = String.format("%s,search,*,{%s};", this.manager.getClientId(), author);
                break;
            case "title":
                request = String.format("%s,search,%s,*;", this.manager.getClientId(), title);
                break;
            case "isbn":
                request = String.format("%s,search,*,*,%s;", this.manager.getClientId(), isbn);
                break;
            default:
                request = null;
                break;
        }

        String response = new ProxyCommandController().processRequest(request);
        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
        display(responseObject);
    }

    /**
     * Displays the data in the GUI.
     * @param response: the response hash map of data
     */
    private void display(HashMap<String, String> response) {
        this.titleField.clear();
        this.authorField.clear();
        this.isbnField.clear();
        this.results.getChildren().clear();
        this.noResultsLabel.setText("");

        if (Integer.parseInt(response.get("numberOfBooks")) == 0) {
            this.noResultsLabel.setText("No Books Found");
        } else {
            ArrayList<HashMap<String, String>> books = ParseResponseUtility.parseBooks(response.get("books"));
            for (HashMap<String, String> book: books) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/book.fxml"));
                    this.results.getChildren().add(loader.load());
                    ((SearchResultController)loader.getController()).load(this.manager, book, false);
                } catch (Exception e) {
                    System.out.println("Error loading book.");
                }
            }
        }
    }

    private void service(RadioButton button) {
        String request = manager.getClientId() + ",service," + button.getUserData() + ";";
        String response = new ProxyCommandController().processRequest(request);

        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
        if (!responseObject.get("message").equals("success")) {
            System.out.println("error occurred");
        }
    }

    @FXML public void titleSearch() {
        search("title", titleField.getText(), "", "");
    }

    /**
     * Searches based on the author.
     */
    @FXML
    public void authorSearch() {
        search("author", "", this.authorField.getText(), "");
    }

    /**
     * Searches based on ISBN.
     */
    @FXML
    public void isbnSearch() {
        search("isbn", "", "", this.isbnField.getText());
    }

    /**
     * Goes to the home page for the visitor / employee.
     */
    @FXML
    public void home() {
        if (ProxyCommandController.isEmployee(this.manager.getClientId())) {
            this.manager.display("main_employee", this.manager.getUser());
        } else {
            this.manager.display("main_visitor", this.manager.getUser());
        }
    }
}
