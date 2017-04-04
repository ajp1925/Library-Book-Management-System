package lbms.controllers.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lbms.models.SystemDateTime;
import lbms.views.GUIView;


/**
 * Created by Chris on 3/30/2017.
 */
public class ClientController {

    private final static int MAX_WIDTH = 150;
    public static Boolean stop = false;

    @FXML private TabPane tabs;
    @FXML private VBox menuPane;
    @FXML private Button clockButton;
    @FXML private Pane menuBackground;
    @FXML private Text clockText;

    @FXML protected void initialize() {
        // init clock
        Runnable task = () -> {
            while (!stop) {
                clockText.setText(SystemDateTime.getInstance(null).toString());
            }
        };
        new Thread(task).start();

        // add new tab button
        Tab addTab = new Tab();
        addTab.setId("addTab");
        addTab.setGraphic(new Button());

        Button btn = ((Button)addTab.getGraphic());
        btn.setOnMouseClicked(event -> addTab());
        btn.setBorder(null);
        btn.setMinSize(30, 26);
        btn.setMaxSize(30,26);
        btn.setId("addTabButton");
        btn.setText("+");

        addTab.setClosable(false);
        tabs.getTabs().add(addTab);

        addTab();
    }

    @FXML private void addTab() {
        int num = tabs.getTabs().size();
        Tab tab = new Tab("Login");

        try {
            tab.setContent(GUIView.loadFXML("/fxml/login.fxml"));
        } catch (Exception e){
            System.out.println("Error loading fxml");
            System.exit(1);
        }

        tabs.getTabs().add(num - 1, tab);
        tabs.getSelectionModel().select(tab);
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


