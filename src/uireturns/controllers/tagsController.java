package uireturns.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static uireturns.controllers.AppController.tag;

public class tagsController {
    public Button addTag ;
    public Text details = new Text();

    public GridPane tagGrid;
    public Button removeTag;
    AppController appController;
    static int i=0;
    Stack<tagcontainer> tagcontainerList = new Stack<>();

    public void init(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){
        tagcontainer temp = new tagcontainer();
        tagcontainerList.add(temp);
        removeTag.setDisable(true);
    }

    public void addTag(ActionEvent actionEvent) {

        tagcontainer temp = new tagcontainer();
        tagcontainerList.add(temp);
        removeTag.setDisable(false);
    }

    public void removeTag(ActionEvent actionEvent) {

        tagcontainer temp = tagcontainerList.pop();
        temp.remove();
    }

    class tagcontainer{
        int seq=i/4  + 1;
        HBox hbox = new HBox();
        Label label;
        public  Label tagType[] =new Label[3];
        public Label attrlabel = new Label("Attribute ID/Value");
        public TextField tagTypeText[] = new TextField[3];
        public ComboBox attrID = new ComboBox();
        public Button btnattr = new Button("+");
        public Button removeattr = new Button("-");

        public ComboBox attrVal = new ComboBox();

        tagcontainer()
        {
            attrID.setEditable(true);
            attrVal.setEditable(true);

            btnattr.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addAttribute();
                }
            });


            label = new Label("Tag "+seq);
            for(int i=0;i<3;i++){
                tagType[i] = new Label();
                tagTypeText[i] = new TextField();
            }
            tagType[0].setText("GrandParent");
            tagType[1].setText("Parent");
            tagType[2].setText("Name");

            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.getStyleClass().add("tagname-hbox");
            hbox.getChildren().add(label);

            tagGrid.setHgap(15);
            tagGrid.setVgap(5);
            tagGrid.addRow(++i);
            tagGrid.add(hbox,0,i,GridPane.REMAINING,1);

            tagGrid.addRow(++i);
            tagGrid.add(tagType[0],0,i);
            tagGrid.add(tagType[1],1,i);
            tagGrid.add(tagType[2],2,i);

            tagGrid.addRow(++i);
            tagGrid.add(tagTypeText[0],0,i);
            tagGrid.add(tagTypeText[1],1,i);
            tagGrid.add(tagTypeText[2],2,i);

            tagGrid.addRow(++i);
            tagGrid.add(attrlabel,0,i);
            tagGrid.add(attrID,1,i);
            tagGrid.add(attrVal,2,i);
            tagGrid.add(btnattr,3,i);
            tagGrid.add(removeattr,4,i);

            addTag.disableProperty().bind(tagTypeText[2].textProperty().isEmpty());

            //BooleanBinding attrValChanged = Bindings.createBooleanBinding(() -> )
            btnattr.disableProperty().bind(attrVal.valueProperty().isNull().or(attrID.valueProperty().isNull()));
        }

        void addAttribute() {
            if(!attrID.getValue().toString().isEmpty() && !attrVal.getValue().toString().isEmpty()) {
                attrID.getItems().add(attrID.getValue().toString());
                attrVal.getItems().add(attrVal.getValue().toString());
            }
        }




        public void remove() {
            tagGrid.getChildren().remove(hbox);
            tagGrid.getChildren().remove(attrlabel);
            tagGrid.getChildren().remove(tagTypeText[0]);
            tagGrid.getChildren().remove(tagTypeText[1]);
            tagGrid.getChildren().remove(tagTypeText[2]);
            tagGrid.getChildren().remove(tagType[0]);
            tagGrid.getChildren().remove(tagType[1]);
            tagGrid.getChildren().remove(tagType[2]);
            tagGrid.getChildren().remove(attrID);
            tagGrid.getChildren().remove(attrVal);
            i=i-4;
            //Binding
            addTag.disableProperty().bind(tagcontainerList.peek().tagTypeText[2].textProperty().isEmpty());

            if(i==4)
                removeTag.setDisable(true);
        }
    }
}

