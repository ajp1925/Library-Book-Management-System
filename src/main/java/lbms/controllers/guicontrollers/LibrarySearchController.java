package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chris on 4/16/17.
 */
public class LibrarySearchController implements StateController {
    private SessionManager manager;

    @FXML private VBox results;
    @FXML private Text noResultsLabel;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    void populate(HashMap<String, String> response) {
        if (Integer.parseInt(response.get("numberOfBooks")) == 0) {
            noResultsLabel.setText("No Books Found");
        } else {
            ArrayList<HashMap<String, String>> books = ParseResponseUtility.parseBooks(response.get("books"));

            for (HashMap<String, String> book: books) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/book.fxml"));
                    results.getChildren().add(loader.load());
                    ((SearchResultController)loader.getController()).populate(book);
                } catch (Exception e) {
                    System.out.println("Error loading book.");
                }
            }
        }
    }

    @FXML public void home() { manager.display("main_employee", manager.getUser()); }
}
