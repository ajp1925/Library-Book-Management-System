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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIView.class.getResource("/fxml/client.fxml"));
            root = loader.load();
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

    public static void main(String[] args) {
        launch();
    }
}
