package lbms.controllers.guicontrollers.ReturnControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * PayFineController class for the Library Book Management System.
 * @author Team B
 */
public class PayFineController {

    private SessionManager manager;
    private Stage stage;
    private String visitor;
    private HashMap<String, String> response;
    private ArrayList<HashMap<String, String>> books;

    @FXML private AnchorPane root;
    @FXML private VBox results;
    @FXML private TextField input;
    @FXML private Text title, fine, failedLabel;

    /**
     * Initializes the data for this class.
     */
    @FXML
    protected void initialize() {
        this.root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                pay();
                e.consume();
            }
        });
    }


    /**
     * Pays the fine.
     */
    @FXML
    public void pay() {
        String payment = this.input.getText();

        if (payment.isEmpty()) {
            this.failedLabel.setText("Please enter and amount to pay.");
        } else {
            String request = this.manager.getClientId() + ",pay," + payment + "," + this.visitor + ";";
            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("success")) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/payment_success.fxml"));
                    Parent root = loader.load();
                    ((PaymentSuccessController)loader.getController()).load(this.visitor, payment,
                            responseObject.get("balance"));
                    this.stage.setScene(new Scene(root, 750, 500));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.failedLabel.setText("Invalid amount. Please try again.");
            }
        }
    }

    /**
     * Loads the data for this class and displays it.
     * @param stage: the stage
     * @param manager: the session manager
     * @param visitor: the visitor of the tab
     * @param response: the parse response data
     * @param books: the books with fines
     */
    void load(Stage stage, SessionManager manager, String visitor, HashMap<String, String> response,
              ArrayList<HashMap<String, String>> books) {
        this.stage = stage;
        this.manager = manager;
        this.response = response;
        this.visitor = visitor;
        this.books = books;
        display();
    }

    /**
     * Displays the data for this class.
     */
    private void display() {
        this.title.setText("Visitor " + this.visitor + " has overdue books.\nPlease pay fines to continue.");
        this.fine.setText(this.response.get("fine"));

        List<String> ids = Arrays.asList(this.response.get("ids").split("\\s*,\\s*"));
        for (String id: ids) {
            for (HashMap<String, String> book: this.books) {
                if (book.get("id").equals(id)) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(SessionManager.class.getResource("/fxml/overdue.fxml"));
                        this.results.getChildren().add(loader.load());
                        BorrowedResultController controller = loader.getController();
                        controller.load(this.manager, book);
                    } catch (Exception e) {
                        System.out.println("Error loading book.");
                    }
                    break;
                }
            }
        }
    }
}
