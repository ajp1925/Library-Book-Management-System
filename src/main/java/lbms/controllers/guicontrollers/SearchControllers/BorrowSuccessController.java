package lbms.controllers.guicontrollers.SearchControllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by Chris on 4/17/17.
 */
public class BorrowSuccessController {
    private HashMap<String, String> book;
    private String visitor, dueDate;

    @FXML private Text title, date;

    void load(HashMap<String, String> book, String visitor, String dueDate) {
        this.book = book;
        this.visitor = visitor;
        this.dueDate = dueDate;
        display();
    }

    private void display() {
        title.setText("Visitor " + visitor + " has borrowed " + book.get("title"));
        date.setText("Due Date: " + dueDate);
    }

    @FXML public void close() {
        Stage stage = (Stage)title.getScene().getWindow();
        stage.close();
    }
}