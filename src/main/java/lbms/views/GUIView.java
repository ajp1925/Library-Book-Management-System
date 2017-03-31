package lbms.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

        Parent root;
        try {
            root = loadFXML();
        } catch (Exception e) {
            root = new Pane();
        }

        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
    }

    private Parent loadFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/client.fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
