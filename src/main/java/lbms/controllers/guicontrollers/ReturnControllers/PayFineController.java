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
 * Created by Chris on 4/18/17.
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

    @FXML protected void initialize() {
        this.root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                pay();
                e.consume();
            }
        });
    }


    @FXML public void pay() {
        String payment = input.getText();

        if (payment.isEmpty()) {
            failedLabel.setText("Please enter and amount to pay.");
        } else {
            String request = manager.getClientId() + ",pay," + payment + "," + visitor + ";";
            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            if (responseObject.get("message").equals("success")) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SessionManager.class.getResource("/fxml/payment_success.fxml"));
                    Parent root = loader.load();
                    ((PaymentSuccessController)loader.getController()).load(visitor, payment, responseObject.get("balance"));
                    stage.setScene(new Scene(root, 750, 500));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                failedLabel.setText("Invalid amount. Please try again.");
            }
        }
    }

    void load(Stage stage, SessionManager manager, String visitor, HashMap<String, String> response,
              ArrayList<HashMap<String, String>> books) {
        this.stage = stage;
        this.manager = manager;
        this.response = response;
        this.visitor = visitor;
        this.books = books;
        display();
    }

    private void display() {
        title.setText("Visitor " + visitor + " has overdue books.\nPlease pay fines to continue.");
        fine.setText(response.get("fine"));

        List<String> ids = Arrays.asList(response.get("ids").split("\\s*,\\s*"));
        for (String id: ids) {
            for (HashMap<String, String> book: books) {
                if (book.get("id").equals(id)) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(SessionManager.class.getResource("/fxml/overdue.fxml"));
                        results.getChildren().add(loader.load());
                        BorrowedResultController controller = loader.getController();
                        controller.load(manager, book);
                    } catch (Exception e) {
                        System.out.println("Error loading book.");
                    }
                    break;
                }
            }
        }
    }
}
