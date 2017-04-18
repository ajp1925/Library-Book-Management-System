package lbms.controllers.guicontrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lbms.controllers.commandproxy.CommandController;
import lbms.views.GUI.SessionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClientController class for the Library Book Management System.
 * @author Team B
 */
public class ClientController {

    private static Boolean stop = false;

    @FXML private BorderPane root;
    @FXML private TabPane tabs;
    @FXML private Text clockText;

    /**
     * Initializes the state for this instance of the class.
     */
    @FXML
    protected void initialize() {
        createMenuBar();
        //createWindowButtons(); // TODO uncomment this?
        this.clockText.setFont(Font.font(null,FontWeight.BOLD, 13));

        Runnable task = () -> {
            while (!stop) {
                LocalDateTime date = CommandController.getSystemDateTime();
                this.clockText.setText(date.format(DateTimeFormatter.ofPattern("HH:mm    MM/dd/yyyy")));
            }
        };
        new Thread(task).start();

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
        this.tabs.getTabs().add(addTab);
        addTab();
    }

    /**
     * Adds a tab to the window.
     */
    @FXML
    private void addTab() {
        int num = this.tabs.getTabs().size();
        Tab tab = new Tab("Login");

        SessionManager manager = new SessionManager(tab);
        manager.display("login", "Login", false);

        tab.setOnCloseRequest((Event event) -> { manager.close(false); });
        this.tabs.getTabs().add(num - 1, tab);
        this.tabs.getSelectionModel().select(tab);
    }

    /**
     * Stops the GUI.
     */
    public static void stop() {
        stop = true;
    }

    /**
     * Creates the menu.
     */
    private void createMenuBar() {
        // Create Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getStylesheets().add(getClass().getResource("/css/client.css").toExternalForm());

        // Add Menu
        KeyCombination.Modifier key;
        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
            this.root.getChildren().add(menuBar);
            key = KeyCombination.META_DOWN;
        } else {
            this.root.setTop(menuBar);
            key = KeyCombination.CONTROL_DOWN;
        }

        // File
        Menu fileMenu = new Menu("File");
        MenuItem newTab = new MenuItem("New Tab");
        newTab.setOnAction((ActionEvent event) -> { addTab(); });
        newTab.setAccelerator(new KeyCodeCombination(KeyCode.T, key));
        MenuItem closeTab = new MenuItem("Close Tab");

        closeTab.setOnAction((ActionEvent event) -> {
            if (this.tabs.getSelectionModel().getSelectedItem().isClosable()) {
                this.tabs.getTabs().remove(this.tabs.getSelectionModel().getSelectedItem());
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
        undo.setOnAction((ActionEvent event) -> {
            // TODO
            System.out.println("Undo");
        });
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, key));

        MenuItem redo = new MenuItem("Redo");
        redo.setOnAction((ActionEvent event) -> {
            // TODO
            System.out.println("Redo");
        });
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Z, key, KeyCombination.SHIFT_DOWN));

        editMenu.getItems().addAll(undo, redo);
        menuBar.getMenus().addAll(fileMenu, editMenu);
    }
}