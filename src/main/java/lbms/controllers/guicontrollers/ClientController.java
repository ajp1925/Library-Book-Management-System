package lbms.controllers.guicontrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.CommandController;
import lbms.views.GUI.SessionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClientController class
 */
public class ClientController {

    private final static int MAX_WIDTH = 150;
    public static Boolean stop = false;

    @FXML private BorderPane root;
    @FXML private TabPane tabs;
    @FXML private Text clockText;
    @FXML private HBox windowButtonBox;

    @FXML protected void initialize() {
        createMenuBar();
        //createWindowButtons();

        // init clock
        Runnable task = () -> {
            while (!stop) {
                LocalDateTime date = CommandController.getSystemDateTime();
                clockText.setText(date.format(DateTimeFormatter.ofPattern("HH:mm    MM/dd/yyyy")));
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
        btn.setMinSize(31, 26);
        btn.setMaxSize(31,26);
        btn.setId("addTabButton");
        btn.setText("+");
        btn.setTextFill(Color.BLACK);

        addTab.setClosable(false);
        tabs.getTabs().add(addTab);

        addTab();
    }

    @FXML private void addTab() {
        int num = tabs.getTabs().size();
        Tab tab = new Tab("Login");

        SessionManager manager = new SessionManager(tab);
        manager.display("login");

        tab.setOnCloseRequest((Event event) -> { manager.close(); });
        tabs.getTabs().add(num - 1, tab);

        tabs.getSelectionModel().select(tab);
    }

    private void createMenuBar() {
        // Create Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getStylesheets().add(getClass().getResource("/css/client.css").toExternalForm());

        // Add Menu
        KeyCombination.Modifier key;
        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
            root.getChildren().add(menuBar);
            key = KeyCombination.META_DOWN;
        } else {
            root.setTop(menuBar);
            key = KeyCombination.CONTROL_DOWN;
        }

        // File
        Menu fileMenu = new Menu("File");

        MenuItem newTab = new MenuItem("New Tab");
        newTab.setOnAction((ActionEvent event) -> { addTab(); });
        newTab.setAccelerator(new KeyCodeCombination(KeyCode.T, key));

        MenuItem closeTab = new MenuItem("Close Tab");
        closeTab.setOnAction((ActionEvent event) -> {
            if (tabs.getSelectionModel().getSelectedItem().isClosable()) {
                tabs.getTabs().remove(tabs.getSelectionModel().getSelectedItem());
            }
        });
        closeTab.setAccelerator(new KeyCodeCombination(KeyCode.W, key));


        MenuItem closeWindow = new MenuItem("Close Window");
        closeWindow.setOnAction((ActionEvent event) -> { Platform.exit(); });
        closeWindow.setAccelerator(new KeyCodeCombination(KeyCode.Q, key));

        fileMenu.getItems().addAll(newTab, closeTab, closeWindow);

        // Edit
        Menu editMenu = new Menu("Edit");

        MenuItem undo = new MenuItem("Undo");
        undo.setOnAction((ActionEvent event) -> { /*TODO*/ System.out.println("Undo"); });
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, key));

        MenuItem redo = new MenuItem("Redo");
        redo.setOnAction((ActionEvent event) -> { /*TODO*/ System.out.println("Redo"); });
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Z, key, KeyCombination.SHIFT_DOWN));

        editMenu.getItems().addAll(undo, redo);

        // Menu
        menuBar.getMenus().addAll(fileMenu, editMenu);
    }

    private void createWindowButtons() {
        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SessionManager.class.getResource("/fxml/mac.fxml"));
                windowButtonBox = loader.load();
            } catch (Exception e) {
                System.out.println("Error loading fxml");
                System.exit(1);
            }

            AnchorPane.clearConstraints(windowButtonBox);
            AnchorPane.setLeftAnchor(windowButtonBox, 5.0);
            AnchorPane.setTopAnchor(windowButtonBox, 5.0);

            tabs.setPadding(new Insets(10, 0, 0, 100));
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SessionManager.class.getResource("/fxml/windows.fxml"));
                windowButtonBox = loader.load();
            } catch (Exception e) {
                System.out.println("Error loading fxml");
                System.exit(1);
            }

            AnchorPane.clearConstraints(windowButtonBox);
            AnchorPane.setLeftAnchor(windowButtonBox, 5.0);
            AnchorPane.setTopAnchor(windowButtonBox, 5.0);

            tabs.setPadding(new Insets(10, 100, 0, 0));
        }
    }
}


