package uireturns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        primaryStage.setTitle("XML Parser");
        primaryStage.setScene(new Scene(root, 969, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
