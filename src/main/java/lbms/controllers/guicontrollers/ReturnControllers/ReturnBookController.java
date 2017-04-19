package lbms.controllers.guicontrollers.ReturnControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.guicontrollers.StateController;
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ReturnBookController class for the library book management system.
 * @author Team B
 */
public class ReturnBookController implements StateController {

    private SessionManager manager;
    private HashMap<CheckBox, String> options = new HashMap<>();
    private ArrayList<HashMap<String, String>> books;
    private String visitor;

    @FXML private VBox results;
    @FXML private TextField visitorIdField;
    @FXML private Text failedLabel, visitorIdFail;

    /**
     * Initializes the state for this instance of the class.
     */
    @FXML
    protected void initialize() {
        this.results.getParent().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                find();
                e.consume();
            }
        });
    }

    /**
     * Initializes the manager for this instance of the class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Finds books that are not returned.
     */
    @FXML
    public void find() {
        this.visitorIdFail.setText("");
        this.failedLabel.setText("");
        this.results.getChildren().clear();

        this.visitor = this.visitorIdField.getText();

        if (this.visitor.isEmpty()) {
            this.visitorIdFail.setText("*");
            this.failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = this.manager.getClientId() + ",borrowed," + this.visitor + ";";
            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("success")) {
                if (Integer.parseInt(responseObject.get("numberOfBooks")) == 0) {
                    this.failedLabel.setText("This visitor has not borrowed any books.");
                } else {
                    this.books = ParseResponseUtility.parseBooks(responseObject.get("books"));

                    for (HashMap<String, String> book: this.books) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(SessionManager.class.getResource("/fxml/borrowed.fxml"));
                            this.results.getChildren().add(loader.load());
                            BorrowedResultController controller = loader.getController();
                            controller.load(this.manager, book);
                            this.options.put(controller.getCheckBox(), book.get("id"));
                        } catch (Exception e) {
                            System.out.println("Error loading book.");
                        }
                    }
                }
            } else {
                this.failedLabel.setText("Invalid visitor ID. Please try again.");
            }

        }
    }

    /**
     * Returns books through the GUI.
     */
    @FXML
    void returnBooks() {
        String request = this.manager.getClientId() + ",return," + this.visitor + ",{";
        if (this.options.isEmpty()) {
            this.failedLabel.setText("This visitor has no borrowed books.");
        } else {
            for (CheckBox box: this.options.keySet()) {
                if (box.isSelected()) {
                    request += this.options.get(box) + ",";
                }
            }
            request = request.substring(0, request.lastIndexOf(",")) + "};";

            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("success")) {
                this.manager.display("return_success", "Book Returned");
            } else if (responseObject.get("message").equals("overdue")){
                Parent root;
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/payment.fxml"));
                    root = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle(visitor + " - Pay Fine");
                    stage.setScene(new Scene(root, 750, 500));

                    ((PayFineController)loader.getController()).load(stage, manager, visitor, responseObject, books);
                    stage.show();
                }
                catch (Exception e) {
                    System.out.println("Error loading FXML file.");
                    System.exit(1);
                }
            } else {
                this.failedLabel.setText("Error");
            }

        }
    }

    /**
     * Cancels anything on this page and goes back a page.
     */
    @FXML
    public void cancel() {
        this.manager.display("main_employee", this.manager.getUser());
    }
}
