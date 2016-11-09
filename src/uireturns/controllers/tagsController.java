package uireturns.controllers;

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

    List<tagVM> tags = new ArrayList<>();
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
        temp.remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Removing "+ temp.name.getText());
                tags.remove(temp);
                tagContainer.getChildren().remove(temp.render());
            }
        });
        tagContainer.getChildren().add(1,temp.render());
    }
}

