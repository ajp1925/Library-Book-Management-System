package lbms.controllers.guicontrollers.ReturnControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * Created by Chris on 4/17/17.
 */
public class BorrowedResultController {
    private SessionManager manager;

    private HashMap<String, String> book;

    @FXML private CheckBox checkBox;
    @FXML private Text isbn, title, date;

    public void load(SessionManager manager, HashMap<String, String> book) {
        this.manager = manager;
        this.book = book;
        populate();
    }

    private void populate() {
        this.isbn.setText(book.get("isbn"));
        this.title.setText(book.get("title"));
        this.date.setText(book.get("dateBorrowed"));
    }

    CheckBox getCheckBox() { return checkBox; }

}
