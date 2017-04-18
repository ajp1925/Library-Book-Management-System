package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lbms.models.Session;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * Created by Chris on 4/16/17.
 */
public class SearchResultController {
    private SessionManager manager;
    private boolean state;

    private HashMap<String, String> book;

    @FXML private Hyperlink isbn;
    @FXML private Text title;
    @FXML private Text author;
    @FXML private Text publishDate;
    @FXML private Text publisher;
    @FXML private Text pageCount;

    void load(SessionManager manager, HashMap<String, String> book, boolean arg) {
        this.manager = manager;
        this.state = arg;
        this.book = book;
        populate();
    }

    private void populate() {
         this.isbn.setText(book.get("isbn"));
         this.title.setText(book.get("title"));
         this.author.setText(book.get("authors"));
         this.pageCount.setText(book.get("pageCount"));
         this.publishDate.setText(book.get("publishDate"));
         this.publisher.setText(book.get("publisher"));
    }

    @FXML public void select() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SessionManager.class.getResource("/fxml/book_info.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(manager.getVisitor() + " - " + book.get("title"));
            stage.setScene(new Scene(root, 750, 500));

            ((BookInfoController)loader.getController()).load(stage, manager, book, state);

            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
