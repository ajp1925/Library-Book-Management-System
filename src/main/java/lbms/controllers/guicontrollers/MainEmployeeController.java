package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.SearchControllers.LibrarySearchController;
import lbms.controllers.guicontrollers.SearchControllers.StoreSearchController;
import lbms.views.GUI.SessionManager;

/**
 * MainEmployeeController class for the GUI of the Library Book Management System.
 * @author Team B
 */
public class MainEmployeeController implements StateController {

    private SessionManager manager;

    @FXML private TabPane storeSearchBox;
    @FXML private TabPane searchBox;
    @FXML private Tab searchByAuthor;
    @FXML private Tab searchByTitle;
    @FXML private Tab searchByISBN;
    @FXML private Tab storeByAuthor;
    @FXML private Tab storeByTitle;
    @FXML private Tab storeByISBN;
    @FXML private TextField storeTitleField;
    @FXML private TextField storeAuthorField;
    @FXML private TextField storeISBNField;
    @FXML private TextField searchTitleField;
    @FXML private TextField searchAuthorField;
    @FXML private TextField searchISBNField;
    @FXML private Text failedLabel;

    /**
     * Initializes the state of the instance of this class.
     */
    @FXML
    protected void initialize() {
        this.storeSearchBox.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                searchStore();
                e.consume();
            }
        });

        this.searchBox.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search();
                e.consume();
            }
        });

        this.searchByTitle.setUserData("title");
        this.searchByAuthor.setUserData("author");
        this.searchByISBN.setUserData("isbn");
        this.storeByTitle.setUserData("title");
        this.storeByAuthor.setUserData("author");
        this.storeByISBN.setUserData("isbn");
    }

    /**
     * Initializes the manager for this class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Used for setting up the search bars in the GUI.
     */
    @FXML
    public void search() {
        String author = this.searchAuthorField.getText();
        String title = this.searchTitleField.getText();
        String isbn = this.searchISBNField.getText();
        String type = this.searchBox.getSelectionModel().getSelectedItem().getUserData().toString();

        this.manager.display("search_library", "Library Search");
        ((LibrarySearchController)this.manager.getController()).search(type, title, author, isbn);
    }

    /**
     * Sets up the search bar for the store search.
     */
    @FXML
    public void searchStore() {
        String author = this.storeAuthorField.getText();
        String title = this.storeTitleField.getText();
        String isbn = this.storeISBNField.getText();
        String type = this.storeSearchBox.getSelectionModel().getSelectedItem().getUserData().toString();

        this.manager.display("search_store", "Store Search");
        ((StoreSearchController)this.manager.getController()).search(type, title, author, isbn);
    }

    /**
     * Method for beginning a visit as an employee.
     */
    @FXML
    public void beginVisit() {
        if (ProxyCommandController.isOpen()) {
            this.manager.display("begin_visit", "Begin Visit");
        } else {
            this.failedLabel.setText("Sorry the library is closed, please try again later.");
        }
    }

    /**
     * Ends a visit for an employee.
     */
    @FXML
    public void endVisit() {
        if (ProxyCommandController.isOpen()) {
            this.manager.display("end_visit", "End Visit");
        } else {
            this.failedLabel.setText("Sorry the library is closed, please try again later.");
        }
    }

    /**
     * Returns a book.
     */
    @FXML
    public void returnBook() {
        this.manager.display("return", "Return Book");
    }

    /**
     * Method for an employee registering a visitor.
     */
    @FXML
    public void register() {
        this.manager.display("register", "Register Visitor");
    }

    /**
     * Method for creating an account as an employee.
     */
    @FXML
    public void create() {
        this.manager.display("create", "Create Account");
    }

    /**
     * Method for going to the system settings as an employee.
     */
    @FXML
    public void settings() {
        this.manager.display("settings", "System Settings");
    }

    /**
     * Logs out an employee.
     */
    @FXML
    public void logout() {
        new ProxyCommandController().processRequest(manager.getClientId() + ",logout;");
        this.manager.display("login", "Login", false);
    }
}
