package lbms.controllers.guicontrollers.ReturnControllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Chris on 4/18/17.
 */
public class PaymentSuccessController {
    @FXML private AnchorPane root;
    @FXML private Text title, visitor, paid, balance;

    @FXML protected void initialize() {
        this.root.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                close();
                e.consume();
            }
        });
    }

    void load(String visitor, String payment, String balance) {
        this.visitor.setText(visitor);
        this.paid.setText("$" +payment);
        this.balance.setText("$" +balance);
    }

    @FXML
    public void close() {
        Stage stage = (Stage)title.getScene().getWindow();
        stage.close();
    }
}
