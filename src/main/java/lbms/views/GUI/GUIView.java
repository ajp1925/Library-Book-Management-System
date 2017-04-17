package lbms.views.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lbms.controllers.guicontrollers.ClientController;
import lbms.views.View;

/**
 * GUIView class used for the graphical user interface of the LBMS.
 * @author Team B
 */
public class GUIView extends Application implements View {

    /**
     * Constructor for a GUIView.
     */
    public GUIView() {}

    /**
     * Launches the GUI window.
     */
    @Override
    public void run() {
        launch();
    }

    /**
     * Starts the interface setup.
     * @param primaryStage: the first stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIView.class.getResource("/fxml/client.fxml"));
            root = loader.load();
        } catch (Exception e) {
            System.out.println("Error loading fxml file");
            System.exit(1);
        }

        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac")) {
            primaryStage.setScene(new Scene(root, 1280, 800));
        } else {
            primaryStage.setScene(new Scene(root, 1280, 850));
        }

        primaryStage.show();
    }

    /**
     * Used to stop the GUIView.
     */
    @Override
    public void stop(){
        ClientController.stop();
        Platform.exit();
    }

}
