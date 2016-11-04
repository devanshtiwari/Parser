package uireturns.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static uireturns.controllers.AppController.tag;

public class tagsController {
    public Button addTag ;
    public Text details = new Text();

    public GridPane tagGrid;
    AppController appController;
    static int i=0;
    ArrayList<tagcontainer> tagcontainerList = new ArrayList<>();

    public void init(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){

    }



    public void addTag(ActionEvent actionEvent) {
        tagcontainer temp = new tagcontainer();
        tagcontainerList.add(temp);

    }

    class tagcontainer{
        HBox hbox = new HBox();
        Label label = new Label("Tag");
        public ComboBox tagType = new ComboBox();
        public TextField tagTypeText = new TextField();

        tagcontainer()
        {
            String []tagTypes = new String[] {"Name","Parent","Grand Parent","Attributes"};
            tagType.getItems().addAll(tagTypes);
            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.getBorder();
            hbox.getStyleClass().add("tagname-hbox");
            hbox.getChildren().add(label);
            tagGrid.addRow(++i);
            tagGrid.add(hbox,0,i,GridPane.REMAINING,1);
            tagGrid.addRow(++i);
            tagGrid.add(tagType,0,i);
            tagGrid.add(tagTypeText,1,i);
        }
    }
}

