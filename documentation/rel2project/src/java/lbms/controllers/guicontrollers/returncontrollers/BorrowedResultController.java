package lbms.controllers.guicontrollers.returncontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import lbms.views.gui.SessionManager;

import java.util.HashMap;

/**
 * BorrowedResultController class for the Library Book Management System.
 * @author Team B
 */
public class BorrowedResultController {

    private SessionManager manager;
    private HashMap<String, String> book;

    @FXML private CheckBox checkBox;
    @FXML private Text isbn, title, date;

    /**
     * Loads the data for this class.
     * @param manager: the session manager
     * @param book: the hash map of books that are borrowed
     */
    public void load(SessionManager manager, HashMap<String, String> book) {
        this.manager = manager;
        this.book = book;
        populate();
    }

    /**
     * Populates the list of borrowed books.
     */
    private void populate() {
        this.isbn.setText(this.book.get("isbn"));
        this.title.setText(this.book.get("title"));
        this.date.setText(this.book.get("dateBorrowed"));
    }

    /**
     * Getter for the check box.
     * @return the check box
     */
    CheckBox getCheckBox() {
        return this.checkBox;
    }
}
