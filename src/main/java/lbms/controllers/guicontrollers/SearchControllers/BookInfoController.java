package lbms.controllers.guicontrollers.SearchControllers;

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
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chris on 4/17/17.
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

    @FXML protected void initialize() {
        this.actionButton.getParent().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                actionButton.fire();
                e.consume();
            }
        });
    }

    void load(Stage stage, SessionManager manager, HashMap<String, String> book, boolean state) {
        this.stage = stage;
        this.manager = manager;
        this.book = book;
        this.state = state;
        display();
    }

    private void display() {
        this.title.setText(book.get("title"));
        this.author.setText(book.get("authors"));
        this.isbn.setText(book.get("isbn"));
        this.publishDate.setText(book.get("publishDate"));
        this.publisher.setText(book.get("publisher"));
        this.pageCount.setText(book.get("pageCount"));

        if (state) {
            boolean status = ProxyCommandController.isEmployee(manager.getClientId());

            quantityLabel.setText("Quantity");
            this.quantity.setText(book.get("quantity"));
            actionButton.setText("Borrow");
            actionButton.setOnAction(e -> borrow());

            inputLabel.setText("Visitor ID");

            if (!status){
                input.setText(manager.getVisitor().toString());
                input.setDisable(true);
            }

        } else {
            quantityLabel.setText("");
            actionButton.setText("Purchase");
            actionButton.setOnAction(e -> purchase());
            inputLabel.setText("Quantity");
        }
    }

    private void borrow() {
        if (input.getText().isEmpty()) {
            failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = String.format("%s,borrow,{%s},%s;", manager.getClientId(), book.get("id"), input.getText());
            System.out.println(request); // todo remove
            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response);   // todo remove
            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            if (responseObject.get("message").equals("success")) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/borrow_success.fxml"));
                    Parent root = loader.load();
                    ((BorrowSuccessController)loader.getController()).load(book, input.getText(), responseObject.get("dueDate"));

                    stage.setScene(new Scene(root, 750, 500));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (responseObject.get("message").equals("no-more-copies")){
                failedLabel.setText("There are no more copies of this book. Please try again later.");
            } else if (responseObject.get("message").equals("book-limit-exceeded")){
                failedLabel.setText("This visitor has exceeded their book limit. Please return a book then try again");
            } else if (responseObject.get("message").equals("outstanding-fine")){
                failedLabel.setText("This visitor has an outstanding fine. Please pay fine then try again.");
            } else if (responseObject.get("message").equals("library-closed")) {
                failedLabel.setText("Sorry the library is closed. Please try again later.");
            } else {
                failedLabel.setText("Visitor does not exist. Please try again.");
            }
        }
    }

    private void purchase() {
        if (input.getText().isEmpty()) {
            failedLabel.setText("Please enter a quantity.");
        } else {
            String request = String.format("%s,buy,%s,%s;", manager.getClientId(), input.getText(), book.get("id"));
            System.out.println(request); //todo remove
            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response); // todo remove
            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            if (responseObject.get("message").equals("success")) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/purchase_success.fxml"));
                    Parent root = loader.load();
                    ArrayList<HashMap<String, String>> books = ParseResponseUtility.parseBooks(responseObject.get("books"));
                    ((PurchaseSuccessController)loader.getController()).load(books.get(0));

                    stage.setScene(new Scene(root, 750, 500));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                failedLabel.setText("Purchase failure. Please try again.");
            }
        }
    }
}
