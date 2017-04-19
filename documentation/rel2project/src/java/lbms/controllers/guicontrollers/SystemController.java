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
 * SystemController class for the Library Book Management System.
 * @author Team B
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

    /**
     * Initializes the state of this class.
     */
    @FXML
    protected void initialize() {
        this.output.setEditable(false);
        this.output.textProperty().addListener(c -> {
            this.output.setScrollTop(Double.MAX_VALUE);
        });

        this.reportOutput.setEditable(false);
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

    /**
     * Initializes the manager for this instance of the class.
     * @param manager: the session manager to be set
     */
    @Override
    public void initManager(SessionManager manager) {
        this.manager = manager;
    }

    /**
     * Displays the home stage.
     */
    @FXML
    public void home() {
        this.manager.display("main_employee", this.manager.getUser());
    }

    /**
     * Advances the time for the system.
     */
    @FXML
    public void advance() {
        this.inputFail.setText("");
        this.label.setText("");
        this.label.setFill(Color.FIREBRICK);

        String days = this.daysField.getText();
        String hours = this.hoursField.getText();

        if (days.isEmpty() && hours.isEmpty()) {
            this.label.setText("Please enter days or hours to advance.");
        } else {
            if (days.isEmpty()) {
                days = "0";
            }
            if (hours.isEmpty()) {
                hours = "0";
            }

            String request = String.format("%s,advance,%s,%s;", this.manager.getClientId(), days, hours);
            String response = new ProxyCommandController().processRequest(request);

            HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
            switch (responseObject.get("message")) {
                case "invalid-number-of-hours":
                    this.label.setText("Can not advance " + hours + " hours. Please try again.");
                    break;
                case "invalid-number-of-days":
                    this.label.setText("Can not advance " + days + " days. Please try again.");
                    break;
                default:
                    this.label.setText("Success");
                    this.label.setFill(Color.GREEN);
                    break;
            }

            this.daysField.setText("");
            this.hoursField.setText("");
        }
    }

    /**
     * Used for the system report viewing as an employee.
     */
    @FXML
    public void report() {
        this.inputFail.setText("");
        this.label.setText("");

        String request;
        String days = this.reportField.getText();

        if (days.isEmpty()) {
            request = this.manager.getClientId() + ",report;";
        } else {
            request = String.format("%s,report,%s;", this.manager.getClientId(), days);
        }

        String response = new ProxyCommandController().processRequest(request);

        HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);
        if (responseObject.get("message").equals("success")) {
            this.reportOutput.setText("Date: " + responseObject.get("date") + responseObject.get("report"));
        } else {
            this.reportOutput.setText("An error occurred.\nPlease try again later.");
        }
    }

    /**
     * Used to enter commands directly into the Library Book Management System.
     */
    @FXML
    public void command() {
        this.label.setText("");
        String request = this.input.getText();

        if (request.isEmpty()) {
            this.inputFail.setText("No input. Please enter a search.");
        } else {
            this.inputFail.setText("");
            String response = new ProxyCommandController().processRequest(this.manager.getClientId() + ","
                    + request);

            try {
                HashMap<String, String> responseObject = ParseResponseUtility.parseResponse(response);

                if (Long.parseLong(responseObject.get("clientID")) == this.manager.getClientId() &&
                        responseObject.get("command").equals("logout")) {
                    this.manager.display("login", "Login", false);
                } else if (Long.parseLong(responseObject.get("clientID")) == this.manager.getClientId() &&
                        responseObject.get("command").equals("disconnect")) {
                    this.manager.close(true);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                this.output.appendText(request + "\n");
                this.output.appendText(response + "\n");
                this.input.clear();
            }
        }
    }
}
