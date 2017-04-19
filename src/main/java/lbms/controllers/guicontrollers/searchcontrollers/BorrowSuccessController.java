package lbms.controllers.guicontrollers.searchcontrollers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * BorrowSuccessController class for the Library Book Management System.
 * @author Team B
 */
public class BorrowSuccessController {

    private HashMap<String, String> book;
    private String visitor, dueDate;

    @FXML private AnchorPane root;
    @FXML private Text title, date;

    /**
     * Initializes the state for this class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                close();
                e.consume();
            }
        });
    }

    /**
     * Loads any necessary data.
     * @param book: hash map of date for the book to be loaded
     * @param visitor: the visitor borrowing books
     * @param dueDate: the date the books will be due
     */
    void load(HashMap<String, String> book, String visitor, String dueDate) {
        this.book = book;
        this.visitor = visitor;
        this.dueDate = dueDate;
        display();
    }

    /**
     * Displays the book.
     */
    private void display() {
        this.title.setText("Visitor " + this.visitor + " has borrowed " + this.book.get("title"));
        this.date.setText("Due Date: " + this.dueDate);
    }

    /**
     * Closes the stage.
     */
    @FXML
    public void close() {
        Stage stage = (Stage)this.title.getScene().getWindow();
        stage.close();
    }
}