package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Chris on 4/6/17.
 */
public class NewUserController implements UserController{
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneNumberField;
    @FXML private Text firstNameFail;
    @FXML private Text lastNameFail;
    @FXML private Text addressFail;
    @FXML private Text phoneNumberFail;

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public ArrayList<String> getFields() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(firstNameField.getText());
        arr.add(lastNameField.getText());
        arr.add(addressField.getText());
        arr.add(phoneNumberField.getText());
        return arr;
    }

    @Override
    public void fail() {
        if (firstNameField.getText().isEmpty()) {
            firstNameFail.setText("*");
        }
        if (lastNameField.getText().isEmpty()) {
            lastNameFail.setText("*");
        }
        if (addressField.getText().isEmpty()) {
            addressFail.setText("*");
        }
        if (phoneNumberField.getText().isEmpty()) {
            phoneNumberFail.setText("*");
        }
    }


    public void clearError() {
        firstNameFail.setText("");
        lastNameFail.setText("");
        addressFail.setText("");
        phoneNumberFail.setText("");
    }

}
