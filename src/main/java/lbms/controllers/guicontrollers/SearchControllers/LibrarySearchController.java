package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
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
 * LibrarySearchController class for the library book management system.
 * @author Team B
 */
public class LibrarySearchController implements StateController {

    private SessionManager manager;

    @FXML private VBox results;
    @FXML private Text noResultsLabel;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;

    /**
     * Initializes the data for this class.
     */
    @FXML
    protected void initialize() {
        this.titleField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
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
    }

    /**
     * Searches the library.
     * @param type: the type of search
     * @param title: the title being searched
     * @param author: the author for the search
     * @param isbn: the isbn of the search
     */
    public void search(String type, String title, String author, String isbn) {
        String request;
        switch (type) {
            case "author":
                request = String.format("%s,info,*,{%s};", this.manager.getClientId(), author);
                break;
            case "title":
                request = String.format("%s,info,%s,*;", this.manager.getClientId(), title);
                break;
            case "isbn":
                request = String.format("%s,info,*,*,%s;", this.manager.getClientId(), isbn);
                break;
            default:
                request = null;
                break;
        }

        System.out.println(request);    //todo remove
        String response = new ProxyCommandController().processRequest(request);
        System.out.println(response); // todo remove
        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
        display(responseObject);
    }

    /**
     * Displays the library search data.
     * @param response: the hash map responses
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
                    ((SearchResultController)loader.getController()).load(this.manager, book, true);
                } catch (Exception e) {
                    System.out.println("Error loading book.");
                }
            }
        }
    }

    /**
     * The title for the search.
     */
    @FXML
    public void titleSearch() {
        search("title", this.titleField.getText(), "", "");
    }

    /**
     * The author for the search.
     */
    @FXML
    public void authorSearch() {
        search("author", "", this.authorField.getText(), "");
    }

    /**
     * The isbn for the search.
     */
    @FXML
    public void isbnSearch() {
        search("isbn", "", "", this.isbnField.getText());
    }

    /**
     * Goes back to the home page for that person.
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
