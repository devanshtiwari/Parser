package uireturns.controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This controller is for Menu Bar, which can make the menus on the top functional.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class MenuBarController {

    public MenuBar menuBar;
    public Menu file;
    public MenuItem fileclose;
    public Menu edit;
    public MenuItem delete;
    public Menu about;
    public MenuItem aboutParser;
    private AppController appController;
    public void init(AppController appController) {
        this.appController = appController;
        about.getText();

    }

    public void aboutParser(ActionEvent actionEvent) throws IOException {


        Stage stage;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(menuBar.getScene().getWindow());
        stage.setTitle("About Generic Parser");
        HBox dialogHbox = new HBox(20);
        dialogHbox.setPadding(new Insets(10));
        Label label = new Label("Welcome!\n This is a Generic Parser which will help you in Parsing all type of XML Files, be it jsff, xml, xliff etc. Moreover, It consists of Logic Box, the middle one which helps you to create your own Logic. Output Report is also generated on the basis of logic Box at designated place." +
                "" +
                "\nDesign & Developed by Avinash and Devansh");
        label.setWrapText(true);
        dialogHbox.getChildren().add(label);
        Scene dialogScene = new Scene(dialogHbox, 300, 200);
        stage.setScene(dialogScene);
        stage.showAndWait();
    }
}
