package lbms.views;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lbms.LBMS;

import java.io.IOException;

/**
 * Created by Chris on 3/30/17.
 */
public class GUIView extends Application implements View {

    public GUIView() {}

    @Override
    public void run() {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Pane root;
        try {
            root = loadFXML();
        } catch (Exception e) {
            System.out.println(e);
            root = new Pane();
        }

//        Text text = new Text("Hello");
//
//        text.setWrappingWidth(385);
//        text.setLayoutX(15);
//        text.setLayoutY(20);
//
//
//        menu.getChildren().addAll(new Button("Something"), new Button("Something else"), new Button("Something different"));
//
//        //menu.getStylesheets().add(LBMS.class.getResource("lbms/views/menustyle.css").toExternalForm());
//        menu.setTranslateX(-190);
//        TranslateTransition menuTranslation = new TranslateTransition(Duration.millis(500), menu);
//
//        menuTranslation.setFromX(-190);
//        menuTranslation.setToX(0);
//
//        menu.setOnMouseEntered(evt -> {
//            menuTranslation.setRate(1);
//            menuTranslation.play();
//        });
//        menu.setOnMouseExited(evt -> {
//            menuTranslation.setRate(-1);
//            menuTranslation.play();
//        });
//
//        root.getChildren().addAll(text, menu);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane loadFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIView.class.getResource("gui/client.fxml"));
        Pane fxmlPane = loader.load();

        return fxmlPane;
    }
}
