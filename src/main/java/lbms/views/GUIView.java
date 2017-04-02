package lbms.views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lbms.LBMS;
import lbms.controllers.ClientController;

import java.io.IOException;

/**
 * Created by Chris on 3/30/17.
 */
public class GUIView extends Application implements View {
    public GUIView() {}     // MUST BE PUBLIC OTHERWISE CLIENT BREAKS

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Parent root = null;
        try {
            root = loadFXML("/fxml/client.fxml");
        } catch (Exception e) {
            System.out.println("Error loading fxml file");
            System.exit(1);
        }

        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.show();
    }

    @Override
    public void stop(){
        ClientController.stop = true;
        Platform.exit();
    }

    public static Parent loadFXML(String file) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIView.class.getResource(file));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
