package lbms.controllers.guicontrollers.RegisterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Chris on 4/6/17.
 */
public class ExistingUserController implements UserController{

    @FXML private TextField visitorIdField;
    @FXML private Text visitorIdFail;

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public ArrayList<String> getFields() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(visitorIdField.getText());
        return arr;
    }

    @Override
    public void fail() {
        visitorIdFail.setText("*");
    }

    @Override
    public void clearError() {
        visitorIdFail.setText("");
    }
}
