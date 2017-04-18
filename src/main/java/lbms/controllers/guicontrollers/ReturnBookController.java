package lbms.controllers.guicontrollers;

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
import lbms.views.GUI.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chris on 4/17/17.
 */
public class ReturnBookController implements StateController{
    private SessionManager manager;
    private HashMap<CheckBox, String> options = new HashMap<>();
    private String visitor;

    @FXML private VBox results;
    @FXML private TextField visitorIdField;
    @FXML private Text failedLabel, visitorIdFail;

    @FXML protected void initialize() {
        this.results.getParent().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                find();
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void find() {
        visitorIdFail.setText("");
        failedLabel.setText("");

        visitor = visitorIdField.getText();

        if (visitor.isEmpty()) {
            visitorIdFail.setText("*");
            failedLabel.setText("Please enter a visitor ID.");
        } else {
            String request = manager.getClientId() + ",borrowed," + visitor + ";";
            System.out.println(request);    // todo remove
            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response); // todo remove
            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            if (responseObject.get("message").equals("success")) {
                if (Integer.parseInt(responseObject.get("numberOfBooks")) == 0) {
                    failedLabel.setText("This visitor has not borrowed any books.");
                } else {
                    ArrayList<HashMap<String, String>> books = ParseResponseUtility.parseBooks(responseObject.get("books"));

                    for (HashMap<String, String> book: books) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(SessionManager.class.getResource("/fxml/borrowed.fxml"));
                            results.getChildren().add(loader.load());
                            BorrowedResultController controller = loader.getController();
                            controller.load(manager, book);
                            options.put(controller.getCheckBox(), book.get("id"));
                        } catch (Exception e) {
                            System.out.println("Error loading book.");
                        }
                    }
                }
            } else {
                failedLabel.setText("Invalid visitor ID. Please try again.");
            }

        }
    }

    @FXML void returnBooks() {
        String request = manager.getClientId() + ",return," + visitor + ",{";
        if (options.isEmpty()) {
            failedLabel.setText("This visitor has no borrowed books.");
        } else{
            for (CheckBox box : options.keySet()) {
                if (box.isSelected()) {
                    request += options.get(box) + ",";
                }
            }
            request = request.substring(0, request.lastIndexOf(",")) + "};";

            System.out.println(request); // todo remove
            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response); //todo remove

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            if (responseObject.get("message").equals("success")) {
                manager.display("return_success", "Book Returned");
            } else if (responseObject.get("message").equals("overdue")){
//                Parent root;
//                try {
//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(SessionManager.class.getResource(""));
//                    root = loader.load();
//                    Stage stage = new Stage();
//                    stage.setTitle(visitor + " - Pay Fine");
//                    stage.setScene(new Scene(root, 750, 500));
//                    stage.show();
//                }
//                catch (Exception e) {
//                    System.out.println("Error loading FXML file.");
//                    System.exit(1);
//                }
                System.out.println("pay");
            } else {
                failedLabel.setText("Error");
            }

        }
    }

    @FXML
    public void cancel() {
        manager.display("main_employee", this.manager.getUser());
    }
}
