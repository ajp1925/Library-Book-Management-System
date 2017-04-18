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
 * Created by Chris on 4/16/2017.
 */
public class StoreSearchController implements StateController {
    private SessionManager manager;

    @FXML
    private VBox results;
    @FXML private Text noResultsLabel;

    @FXML private TextField titleField, authorField, isbnField;
    @FXML private RadioButton localStore, googleStore;

    @FXML protected void initialize() {
        ToggleGroup group = new ToggleGroup();
        localStore.setToggleGroup(group);
        localStore.setSelected(true);
        localStore.setUserData("local");
        googleStore.setToggleGroup(group);
        googleStore.setUserData("google");

        localStore.setOnAction(e -> service(localStore));
        googleStore.setOnAction(e -> service(googleStore));

        titleField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search("title", titleField.getText(), "", "");
                e.consume();
            }
        });

        authorField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search("author", "", authorField.getText(), "");
                e.consume();
            }
        });

        isbnField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                search("isbn", "", "", isbnField.getText());
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    public void search(String type, String title, String author, String isbn) {
        String request;
        switch (type) {
            case "author":
                request = String.format("%s,search,*,{%s};", manager.getClientId(), author);
                break;
            case "title":
                request = String.format("%s,search,%s,*;", manager.getClientId(), title);
                break;
            case "isbn":
                request = String.format("%s,search,*,*,%s;", manager.getClientId(), isbn);
                break;
            default:
                request = null;
                break;
        }

        System.out.println(request); //TODO
        String response = new ProxyCommandController().processRequest(request);
        System.out.println(response);  //TODO
        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
        display(responseObject);
    }

    private void display(HashMap<String, String> response) {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        results.getChildren().clear();
        noResultsLabel.setText("");

        if (Integer.parseInt(response.get("numberOfBooks")) == 0) {
            noResultsLabel.setText("No Books Found");
        } else {
            ArrayList<HashMap<String, String>> books = ParseResponseUtility.parseBooks(response.get("books"));

            for (HashMap<String, String> book: books) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/book.fxml"));
                    results.getChildren().add(loader.load());
                    ((SearchResultController)loader.getController()).load(manager, book, false);
                } catch (Exception e) {
                    System.out.println("Error loading book.");
                }
            }
        }
    }

    private void service(RadioButton button) {
        String request = manager.getClientId() + ",service," + button.getUserData() + ";";
        System.out.println(request);  //todo remove
        String response = new ProxyCommandController().processRequest(request);
        System.out.println(response); //todo remove

        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

        if (!responseObject.get("message").equals("success")) {
            System.out.println("error occurred");
        }
    }

    @FXML public void titleSearch() {
        search("title", titleField.getText(), "", "");
    }

    @FXML public void authorSearch() {
        search("author", "", authorField.getText(), "");
    }

    @FXML public void isbnSearch() {
        search("isbn", "", "", isbnField.getText());
    }

    @FXML public void home() {
        if (ProxyCommandController.isEmployee(manager.getClientId())) {
            manager.display("main_employee", manager.getUser());
        } else {
            manager.display("main_visitor", manager.getUser());
        }
    }
}
