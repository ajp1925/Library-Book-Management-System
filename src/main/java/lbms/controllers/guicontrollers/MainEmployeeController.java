package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

/**
 * MainEmployeeController class for the GUI of the Library Book Management System.
 * @author Team B TODO comment all these methods
 */
public class MainEmployeeController implements StateController {

    private SessionManager manager;

    @FXML private TabPane storeSearchBox;
    @FXML private TabPane searchBox;
    @FXML private Tab searchByAuthor;
    @FXML private Tab searchByTitle;
    @FXML private Tab searchByISBN;
    @FXML private TextField searchTitleField;
    @FXML private TextField searchAuthorField;
    @FXML private TextField searchISBNField;


    @FXML private Text failedLabel;

    @FXML protected void initialize() {
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

        searchByAuthor.setUserData("author");
        searchByTitle.setUserData("title");
        searchByISBN.setUserData("isbn");
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void search() {
        String author = searchAuthorField.getText();
        String title = searchTitleField.getText();
        String isbn = searchISBNField.getText();
        String type = searchBox.getSelectionModel().getSelectedItem().getUserData().toString();

        manager.display("search_library", "Library Search");
        ((LibrarySearchController)manager.getController()).search(type, title, author, isbn);
    }

    @FXML public void searchStore() {
        System.out.println("STORE");
        //TODO
    }

    @FXML public void beginVisit() {
        if (ProxyCommandController.isOpen()) {
            this.manager.display("begin_visit", "Begin Visit");
        } else {
            failedLabel.setText("Sorry the library is closed, please try again later.");
        }
    }

    @FXML public void endVisit() {
        if (ProxyCommandController.isOpen()) {
            this.manager.display("end_visit", "End Visit");
        } else {
            failedLabel.setText("Sorry the library is closed, please try again later.");
        }
    }

    @FXML public void returnBook() {
        //TODO
    }

    @FXML public void register() {
        this.manager.display("register", "Register Visitor");
    }

    @FXML public void create() {
        this.manager.display("create", "Create Account");
    }

    @FXML public void settings() { this.manager.display("settings", "System Settings");}

    @FXML public void logout() {
        new ProxyCommandController().processRequest(manager.getClientId() + ",logout;");
        this.manager.display("login", "Login", false);
    }
}
