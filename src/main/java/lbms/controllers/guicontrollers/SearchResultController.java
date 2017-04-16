package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.HashMap;

/**
 * Created by Chris on 4/16/17.
 */
public class SearchResultController {
    @FXML private Hyperlink isbn;
    @FXML private Text title;
    @FXML private Text author;
    @FXML private Text publishDate;
    @FXML private Text publisher;
    @FXML private Text pageCount;

    void populate(HashMap<String, String> book) {
         this.isbn.setText(book.get("isbn"));
         this.title.setText(book.get("title"));
         this.author.setText(book.get("authors"));
         this.pageCount.setText(book.get("pageCount"));
         this.publishDate.setText(book.get("publishDate"));
         this.publisher.setText(book.get("publisher"));
    }
}
