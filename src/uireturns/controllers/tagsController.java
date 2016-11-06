package uireturns.controllers;

import javafx.beans.binding.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Stack;


public class tagsController {

    static int i=0;
    //FXML Variables
    public Button addTag ;
    public GridPane tagGrid;
    public Button removeTag;

    //Internal Variables
    private AppController appController;
    private Stack<tagcontainer> tagcontainerList = new Stack<>();

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

        //Settiing up Sequence Value
        int seq= i/4  + 1;

        //Components to be used in the Container
        HBox hbox = new HBox();
        Label tagname;
        public  Label tagType[] =new Label[3];
        public Label attrlabel = new Label("Attribute ID/Value");
        public TextField tagTypeText[] = new TextField[3];
        public ComboBox attrID = new ComboBox();
        public Button btnattr = new Button("+");
        public Button removeattr = new Button("-");
        public TextField attrVal = new TextField();

        //Internal Variables
        HashMap<String, String> attrs = new HashMap<String, String>();

        //Constructor
        tagcontainer()
        {
            attrID.setEditable(true);
            attrID.setPromptText("Attribute ID");
            attrVal.setPromptText("Attribute Value");

            //Add Attribute Button Action Listener
            btnattr.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addAttribute();
                }
            });

            //Remove Attribute Button Action Listener
            removeattr.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removeattr();
                }
            });

            tagname = new Label("Tag "+seq);

            for(int i=0;i<3;i++){
                tagType[i] = new Label();
                tagTypeText[i] = new TextField();
            }

            tagType[0].setText("GrandParent");
            tagTypeText[0].setPromptText("GrandParent");
            tagType[1].setText("Parent");
            tagTypeText[1].setPromptText("Parent");
            tagType[2].setText("Name");
            tagTypeText[2].setPromptText("Name");

            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.getStyleClass().add("tagname-hbox");
            hbox.getChildren().add(tagname);

            tagGrid.setHgap(15);
            tagGrid.setVgap(5);

            //Adding Row One
            tagGrid.addRow(++i);
            tagGrid.add(hbox,0,i,GridPane.REMAINING,1);

            //Addomg Row Two
            tagGrid.addRow(++i);
            tagGrid.add(tagType[0],0,i);
            tagGrid.add(tagType[1],1,i);
            tagGrid.add(tagType[2],2,i);

            //Adding Row Three
            tagGrid.addRow(++i);
            tagGrid.add(tagTypeText[0],0,i);
            tagGrid.add(tagTypeText[1],1,i);
            tagGrid.add(tagTypeText[2],2,i);

            //Adding Row 4
            tagGrid.addRow(++i);
            tagGrid.add(attrlabel,0,i);
            tagGrid.add(attrID,1,i);
            tagGrid.add(attrVal,2,i);
            tagGrid.add(btnattr,3,i);
            tagGrid.add(removeattr,4,i);

            //Boolean Binding
            BooleanBinding removeValid = Bindings.createBooleanBinding(() -> {
                if(attrs.size()!=0&&attrID!=null ){
                    if (attrs.containsKey(attrID.getValue().toString()))
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            }, attrID.valueProperty(),btnattr.focusedProperty());


            //Change Listener for attribute ID
            attrID.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(oldValue !=newValue)
                {
                    if(attrs!=null && newValue!=null)
                        attrVal.setText(attrs.get(newValue.toString()));
                }
            } );

            //Button Disable Property
            btnattr.disableProperty().bind(attrVal.textProperty().isNull().or(attrID.valueProperty().isNull()));
            removeattr.disableProperty().bind(removeValid.not());
            addTag.disableProperty().bind(tagTypeText[2].textProperty().isEmpty());
        }

        //Listener to Attribute + Button
        void addAttribute() {
            if(!attrID.getValue().toString().isEmpty() && !attrVal.getText().toString().isEmpty()&& !attrs.containsKey(attrID.getValue().toString())) {
                attrID.getItems().add(attrID.getValue().toString());
                attrs.put(attrID.getValue().toString(),attrVal.getText().toString());
            }
        }

        //Listener to Attribute - Button
        void removeattr(){

            attrs.remove(attrID.getValue().toString());
            attrID.getItems().remove(attrID.getValue().toString());
            attrID.setValue("");
            attrVal.setText("");
        }

        //Remove Function to remove All Components
        public void remove() {
            tagGrid.getChildren().remove(hbox);
            tagGrid.getChildren().remove(attrlabel);
            for(int i =0;i<3;i++){
                tagGrid.getChildren().remove(tagType[i]);
                tagGrid.getChildren().remove(tagTypeText[i]);
            }
            tagGrid.getChildren().remove(attrID);
            tagGrid.getChildren().remove(attrVal);
            tagGrid.getChildren().remove(btnattr);
            tagGrid.getChildren().remove(removeattr);

            //Updating the Value of i
            i=i-4;

            //Updating the Add Tag Binding to upper component
            addTag.disableProperty().bind(tagcontainerList.peek().tagTypeText[2].textProperty().isEmpty());

            //Checking for last component
            if(i==4)
                removeTag.setDisable(true);
        }
    }
}

