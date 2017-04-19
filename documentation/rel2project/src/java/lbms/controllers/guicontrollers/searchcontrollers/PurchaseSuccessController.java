package lbms.controllers.guicontrollers.searchcontrollers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * PurchaseSuccessController class for the Library Book Management System.
 * @author Team B
 */
public class PurchaseSuccessController {

    private HashMap<String, String> book;

    @FXML private Text title, quantity;

    /**
     * Initializes the data for this instance of the class.
     */
    @FXML
    protected void initialize() {
        this.title.getParent().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                close();
                e.consume();
            }
        });
    }

    /**
     * Loads the book to be displayed.
     * @param book: hash map of the information for a book
     */
    void load(HashMap<String, String> book) {
        this.book = book;
        display();
    }

    /**
     * Displays the data for a purchase.
     */
    private void display() {
        this.title.setText("Successfully purchased " + this.book.get("title"));

        if (Integer.parseInt(this.book.get("quantity")) == 1) {
            this.quantity.setText("1 copy purchased");
        } else {
            this.quantity.setText(this.book.get("quantity") +  " copies purchased");
        }
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
