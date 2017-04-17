package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by Chris on 4/17/17.
 */
public class PurchaseSuccessController {
    private HashMap<String, String> book;

    @FXML
    private Text title, quantity;

    void load(HashMap<String, String> book) {
        this.book = book;
        display();
    }

    private void display() {
        title.setText("Successfully purchased " + book.get("title"));
        quantity.setText(book.get("quantity") +  " copies purchased");
    }

    @FXML public void close() {
        Stage stage = (Stage)title.getScene().getWindow();
        stage.close();
    }
}
