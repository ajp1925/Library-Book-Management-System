package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by Chris on 4/17/17.
 */
public class PurchaseSuccessController {
    private HashMap<String, String> book;

    @FXML
    private AnchorPane root;

    @FXML
    private Text title, quantity;

    @FXML protected void initialize() {
        this.root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                close();
                e.consume();
            }
        });
    }
    void load(HashMap<String, String> book) {
        this.book = book;
        display();
    }

    private void display() {
        title.setText("Successfully purchased " + book.get("title"));

        if (Integer.parseInt(book.get("quantity")) == 1) {
            quantity.setText("1 copy purchased");
        } else {
            quantity.setText(book.get("quantity") +  " copies purchased");
        }
    }

    @FXML public void close() {
        Stage stage = (Stage)title.getScene().getWindow();
        stage.close();
    }
}
