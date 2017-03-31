package lbms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lbms.views.GUIView;


/**
 * Created by Chris on 3/30/2017.
 */
public class ClientController {
    private final static int MAX_WIDTH = 150;
    @FXML private TabPane tabs;
    @FXML private VBox menuPane;
    @FXML private Button clockButton;
    @FXML private Pane menuBackground;

    @FXML protected void initialize() {
        Tab tab = new Tab();
        tab.setId("addTab");
        tab.setGraphic(new Button());

        Button btn = ((Button)tab.getGraphic());
        btn.setOnMouseClicked(event -> addTab());
        btn.setBorder(null);
        btn.setMinSize(30, 26);
        btn.setMaxSize(30,26);
        btn.setId("addTabButton");
        btn.setText("+");

        tab.setClosable(false);
        tabs.getTabs().add(tab);

        try {
            tabs.getTabs().get(0).setContent(GUIView.loadFXML("/fxml/login.fxml"));
        } catch (Exception e) {
            System.out.println("Error loading fxml file");
            System.exit(1);
        }
    }

    @FXML private void addTab() {
        int num = tabs.getTabs().size();
        Tab tab = new Tab("Untitled Tab " + (num));
        tabs.getTabs().add(num - 1, tab);
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
            menuBackground.setPrefWidth(0);
            clockButton.setText("");
            clockButton.setPrefWidth(0);
        } else {
            menuPane.setPrefWidth(MAX_WIDTH);
            menuBackground.setPrefWidth(MAX_WIDTH);
            clockButton.setPrefWidth(MAX_WIDTH);
            clockButton.setText("Clock");
        }
    }
}
