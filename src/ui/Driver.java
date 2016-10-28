package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by devanshtiwari on 28-Oct-16.
 */
public class Driver extends Application{

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
        primaryStage.setTitle("XML Parser");
        primaryStage.setScene(new Scene(root, 969, 719));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
