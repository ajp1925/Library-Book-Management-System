package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * SearchResultController class for the Library Book Management System.
 * @author Team B
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

    /**
     * Loads the data into this class.
     * @param manager: the session manager for the class
     * @param book: the book data
     * @param arg: the state for being borrowed of purchased
     */
    void load(SessionManager manager, HashMap<String, String> book, boolean arg) {
        this.manager = manager;
        this.state = arg;
        this.book = book;
        populate();
    }

    /**
     * Populates the GUI parts with the data.
     */
    private void populate() {
         this.isbn.setText(this.book.get("isbn"));
         this.title.setText(this.book.get("title"));
         this.author.setText(this.book.get("authors"));
         this.pageCount.setText(this.book.get("pageCount"));
         this.publishDate.setText(this.book.get("publishDate"));
         this.publisher.setText(this.book.get("publisher"));
    }

    /**
     * Selects the fxml file.
     */
    @FXML public void select() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SessionManager.class.getResource("/fxml/book_info.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(this.manager.getVisitor() + " - " + this.book.get("title"));
            stage.setScene(new Scene(root, 750, 500));

            ((BookInfoController)loader.getController()).load(stage, this.manager, this.book, this.state);

            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading FXML file.");
            System.exit(1);
        }
    }
}
