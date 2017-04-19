package lbms.controllers.guicontrollers.searchcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.gui.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * BookInfoController class for the Library Book Management System.
 * @author Team B
 */
public class BookInfoController {

    private Stage stage;
    private SessionManager manager;
    private HashMap<String, String> book;
    private boolean state;

    @FXML private Button actionButton;
    @FXML private Text title, author, isbn, publisher, publishDate, pageCount, quantity;
    @FXML private Text quantityLabel, failedLabel, inputLabel;
    @FXML private TextField input;

    /**
     * Initializes the state for this class.
     */
    @FXML
    protected void initialize() {
        this.actionButton.getParent().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.actionButton.fire();
                e.consume();
            }
        });
    }

    /**
     * Loads the book information for this page.
     * @param stage: the stage for the gui
     * @param manager: the session manager
     * @param book: the hash map of books
     * @param state: the state boolean
     */
    void load(Stage stage, SessionManager manager, HashMap<String, String> book, boolean state) {
        this.stage = stage;
        this.manager = manager;
        this.book = book;
        this.state = state;
        display();
    }

    /**
     * Displays the data.
     */
    private void display() {
        this.title.setText(this.book.get("title"));
        this.author.setText(this.book.get("authors"));
        this.isbn.setText(this.book.get("isbn"));
        this.publishDate.setText(this.book.get("publishDate"));
        this.publisher.setText(this.book.get("publisher"));
        this.pageCount.setText(this.book.get("pageCount"));

        if (this.state) {
            boolean status = ProxyCommandController.isEmployee(this.manager.getClientId());

            this.quantityLabel.setText("Quantity");
            this.quantity.setText(this.book.get("quantity"));
            this.actionButton.setText("Borrow");
            this.actionButton.setOnAction(e -> borrow());
            this.inputLabel.setText("Visitor ID");

            if (!status) {
                this.input.setText(this.manager.getVisitor().toString());
                this.input.setDisable(true);
            }

        } else {
            this.quantityLabel.setText("");
            this.actionButton.setText("Purchase");
            this.actionButton.setOnAction(e -> purchase());
            this.inputLabel.setText("Quantity");
        }
    }

    /**
     * Borrows the book being viewed.
     */
    private void borrow() {
        if (this.input.getText().isEmpty()) {
            this.failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = String.format("%s,borrow,{%s},%s;", this.manager.getClientId(), this.book.get("id"),
                    this.input.getText());

            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("success")) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/borrow_success.fxml"));
                    Parent root = loader.load();
                    ((BorrowSuccessController)loader.getController()).load(this.book, this.input.getText(),
                            responseObject.get("dueDate"));
                    this.stage.setScene(new Scene(root, 750, 500));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (responseObject.get("message").equals("no-more-copies")){
                this.failedLabel.setText("There are no more copies of this book. Please try again later.");
            } else if (responseObject.get("message").equals("book-limit-exceeded")){
                this.failedLabel.setText("This visitor has exceeded their book limit. Please return a book then try " +
                        "again");
            } else if (responseObject.get("message").equals("outstanding-fine")){
                this.failedLabel.setText("This visitor has an outstanding fine. Please pay fine then try again.");
            } else if (responseObject.get("message").equals("library-closed")) {
                this.failedLabel.setText("Sorry the library is closed. Please try again later.");
            } else {
                this.failedLabel.setText("Visitor does not exist. Please try again.");
            }
        }
    }

    /**
     * Purchases the book for the library.
     */
    private void purchase() {
        if (this.input.getText().isEmpty()) {
            this.failedLabel.setText("Please enter a quantity.");
        } else {
            String request = String.format("%s,buy,%s,%s;", this.manager.getClientId(), this.input.getText(),
                    this.book.get("id"));

            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("success")) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/purchase_success.fxml"));
                    Parent root = loader.load();
                    ArrayList<HashMap<String, String>> books = ParseResponseUtility.parseBooks(responseObject
                            .get("books"));
                    ((PurchaseSuccessController)loader.getController()).load(books.get(0));

                    this.stage.setScene(new Scene(root, 750, 500));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.failedLabel.setText("Purchase failure. Please try again.");
            }
        }
    }
}