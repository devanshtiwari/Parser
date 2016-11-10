package uireturns.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;


public class tagsController {

    static int i=0;
    //FXML Variables
    public Button addTag ;
    public VBox tagContainer;

    //Internal Variables
    private AppController appController;

    static ObservableList<tagVM> tags = FXCollections.observableArrayList();
    public void initialize(){
        tagContainer.setSpacing(10);
        tagContainer.setPadding(new Insets(10,0,20,0));
    }
    public void init(AppController appController) {
        this.appController = appController;
    }

    public void addTag(ActionEvent actionEvent) {
        tagVM temp = new tagVM();
        tags.add(temp);
        temp.remove.setOnAction(event -> {
            System.out.println("Removing "+ temp.name.getText());
            tags.remove(temp);
            tagContainer.getChildren().remove(temp.render());
        });
        tagContainer.getChildren().add(1,temp.render());
    }
}

