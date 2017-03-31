package lbms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;


/**
 * Created by Chris on 3/30/2017.
 */
public class ClientController {
    private final static int MAX_WIDTH = 150;
    @FXML private TabPane tabs;
    @FXML private VBox menuPane;
    @FXML private Button clockButton;

    @FXML private void addTab() {
        int num = tabs.getTabs().size();
        Tab tab = new Tab("Untitled Tab " + (num + 1));
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
    }

    @FXML private void removeTab() {
        if (tabs.getTabs().size() != 0)
            tabs.getTabs().remove(tabs.getSelectionModel().getSelectedIndex());
    }

    @FXML private void toggleMenu() {
        double width = menuPane.getWidth();

        if (width != 0) {
            menuPane.setPrefWidth(0);
            clockButton.setText("");
            clockButton.setPrefWidth(0);
        } else {
            menuPane.setPrefWidth(MAX_WIDTH);
            clockButton.setPrefWidth(MAX_WIDTH);
            clockButton.setText("Clock");
        }
    }
}
