package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.ParseResponseUtility;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.GUI.SessionManager;

import java.util.HashMap;

/**
 * Created by Chris on 4/14/17.
 */
public class SystemController implements StateController {
    private SessionManager manager;

    @FXML private AnchorPane root;
    @FXML private Text label;
    @FXML private TextArea output;
    @FXML private TextField input;
    @FXML private Text inputFail;
    @FXML private VBox timeBox;
    @FXML private TextField daysField;
    @FXML private TextField hoursField;
    @FXML private VBox reportBox;
    @FXML private TextField reportField;
    @FXML private TextArea reportOutput;

    @FXML protected void initialize() {
        output.setEditable(false);
        output.textProperty().addListener(c -> {
            output.setScrollTop(Double.MAX_VALUE);
        });

        reportOutput.setEditable(false);

        this.root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                command();
                e.consume();
            }
        });

        this.timeBox.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                advance();
                e.consume();
            }
        });

        this.reportBox.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                report();
                e.consume();
            }
        });
    }

    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    @FXML public void home() {
        manager.display("main_employee", manager.getUser());
    }

    @FXML public void advance() {
        inputFail.setText("");
        label.setText("");
        label.setFill(Color.FIREBRICK);

        String days = daysField.getText();
        String hours = hoursField.getText();

        if (days.isEmpty() && hours.isEmpty()) {
            label.setText("Please enter days or hours to advance.");
        } else {
            if (days.isEmpty()) { days = "0"; }
            if (hours.isEmpty()) { hours = "0"; }

            String request = String.format("%s,advance,%s,%s;", manager.getClientId(), days, hours);
            System.out.println(request); //TODO remove
            String response = new ProxyCommandController().processRequest(request);
            System.out.println(response);   //TODO remove
            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

            switch (responseObject.get("message")) {
                case "invalid-number-of-hours":
                    label.setText("Can not advance " + hours + " hours. Please try again.");
                    break;
                case "invalid-number-of-days":
                    label.setText("Can not advance " + days + " days. Please try again.");
                    break;
                default:
                    label.setText("Success");
                    label.setFill(Color.GREEN);
                    break;
            }
        }
    }

    @FXML public void report() {
        inputFail.setText("");
        label.setText("");

        String request;
        String days = reportField.getText();

        if (days.isEmpty()) {
            request = manager.getClientId() + ",report;";
        } else {
            request = String.format("%s,report,%s;", manager.getClientId(), days);
        }

        System.out.println(request); //TODO remove

        String response = new ProxyCommandController().processRequest(request);
        System.out.println(response);   //TODO remove

        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

        if (responseObject.get("message").equals("success")) {
            reportOutput.setText("Date: " + responseObject.get("date") + responseObject.get("report"));
        } else {
            reportOutput.setText("An error occurred.\nPlease try again later.");
        }
    }

    @FXML public void command() {
        label.setText("");
        String request = input.getText();

        if (request.isEmpty()) {
            inputFail.setText("No input. Please enter a request.");
        } else {
            inputFail.setText("");
            String response = new ProxyCommandController().processRequest(manager.getClientId() + "," + request);
            output.appendText(request + "\n");
            output.appendText(response + "\n");
            input.clear();
        }
    }
}
