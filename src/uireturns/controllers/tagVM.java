package uireturns.controllers;

import com.xpathgenerator.Attribute;
import com.xpathgenerator.Tag;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * tagVM is a container which contains Parent, Name, GrandParent and all the Tag Components. Object of tagVM is added whenever add Tag
 * Button is clicked
 * @author Avinash and Devansh
 * @since 2016-11-14
 */
public class tagVM {
    Tag tag;
    private VBox vBox;
    Button remove;
    Label name;
    Label xpath;
    ObservableList<HBox> data = FXCollections.observableArrayList();
    static String val = "";
    tagVM(){
        tag = new Tag();
        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefSize(150,200);
        addLabel();
        addXpathLabel();
        HBox box = addDefault();
        addAttr(box);
        xpath.textProperty().addListener((observable, oldValue, newValue) -> {
                tagsController.xpathlist.clear();
                for (int i = 0; i < tagsController.tags.size(); i++) {
                    tagsController.xpathlist.put(tagsController.tags.get(i).tag.getName(),tagsController.tags.get(i).tag.getXpath());
                }
        });

    }

    /**
     * Add Label adds dynamically "New Tag" Label and all other fields.
     */
    private void addLabel(){
        HBox hbox = new HBox();
        name = new Label("New Tag");
        remove = new Button("Remove");
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(name,remove);
        vBox.getChildren().add(hbox);
    }

    /**
     * Mini Header of each Tag
     */
    private void addXpathLabel(){
        HBox hbox= new HBox();
        xpath = new Label(tag.getXpath());
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5, 10, 5, 10));
        hbox.setStyle("-fx-background-color: #989898");
        xpath.setWrapText(true);
        hbox.setPrefSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        xpath.setPrefWidth(Region.USE_COMPUTED_SIZE);
        hbox.getChildren().addAll(xpath);
        vBox.getChildren().add(hbox);
    }

    /**
     * Updates the xpath mini Header of each tag when there is change in text
     */
    private void updateXpathLabel(){
        xpath.setText(tag.getXpath());
    }

    /**
     * Addition of Attribute dynamically
     * @param n Node in which change is made
     */
    private void addAttr(Node n){
        HBox hbox = new HBox();
        int index = vBox.getChildren().indexOf(n) + 1;
        //Name Field
        TextField attrName = new TextField();
        attrName.setPromptText("Attribute");
        HBox.setHgrow(attrName,Priority.ALWAYS);
        attrName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
               val = attrName.getText();
            }
            else {
                if(!attrName.getText().equals("") && !attrName.getText().equals(val) ){
                    if(tag.hasAttribute(val)) {
                        tag.updateAttrName(val, attrName.getText());
                    }
                    else{
                        tag.addAttribute(attrName.getText());
                    }
                }
                else if(attrName.getText().equals("") && !attrName.getText().equals(val)){
                    if(tag.hasAttribute(val)){
                        tag.removeAttr(val);
                    }
                }
            }
            updateXpathLabel();
        });
        //Value Field
        TextField attrVal = new TextField();
        attrVal.setPromptText("Value");
        HBox.setHgrow(attrVal,Priority.ALWAYS);
        attrVal.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                if(tag.hasAttribute(attrName.getText()))
                    tag.updateAttrValue(attrName.getText(),attrVal.getText());
            }
            updateXpathLabel();
        });
        //Condition Field
        ComboBox<String> options = new ComboBox<>();
        options.getItems().addAll("OR","AND");
        options.setValue("OR");
        options.setOnAction(event -> {
            if(tag.hasAttribute(attrName.getText())) {
                if (options.getValue().equals("OR"))
                    tag.updateAttrCondition(attrName.getText(), Attribute.Condition.OR);
                else if(options.getValue().equals("AND"))
                    tag.updateAttrCondition(attrName.getText(), Attribute.Condition.AND);
            }
            updateXpathLabel();
        });

        //Binding
        BooleanBinding validAttr = Bindings.createBooleanBinding(() -> {
            return attrName.getText().equals("");
        },attrName.textProperty());
        attrVal.disableProperty().bind(validAttr);
        options.disableProperty().bind(validAttr);

        //Buttons
        Button addNew = new Button("+");
        Button delete = new Button("x");
        addNew.setWrapText(true);
        delete.setWrapText(true);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(0, 10, 0, 10));
        hbox.setSpacing(5);
        hbox.getChildren().addAll(attrName,attrVal,options,addNew,delete);
        addNew.setOnAction(event -> addAttr(hbox));
        //Button Bindings
        BooleanBinding validDelete = Bindings.createBooleanBinding(() -> {
            return data.size() == 1;
        },data);
        delete.disableProperty().bind(validDelete);
        addNew.disableProperty().bind(validAttr);
        delete.setOnAction(event -> {
            data.remove(hbox);
            tag.removeAttr(attrName.getText());
            vBox.getChildren().remove(hbox);
            updateXpathLabel();
        });
        data.add(hbox);
        vBox.getChildren().add(index,hbox);
    }

    /**
     * By Default, single tag is added.
     * @return
     */
    HBox addDefault(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(0, 10, 0, 10));
        hBox.setSpacing(5);
        TextField tagName = new TextField();
        tagName.setPromptText("Name");
        HBox.setHgrow(tagName, Priority.ALWAYS);
        tagName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            tag.setName(tagName.getText());
            updateXpathLabel();
            name.setText(tag.getName());
        }));
        TextField tagParent = new TextField();
        tagParent.setPromptText("Parent");
        HBox.setHgrow(tagParent, Priority.ALWAYS);
        tagParent.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            tag.setParent(tagParent.getText());
            updateXpathLabel();
        }));
        TextField tagGrandParent = new TextField();
        tagGrandParent.setPromptText("Grand Parent");
        HBox.setHgrow(tagGrandParent, Priority.ALWAYS);
        tagGrandParent.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            tag.setGrandParent(tagGrandParent.getText());
            updateXpathLabel();
        }));

        hBox.getChildren().addAll(tagName, tagParent, tagGrandParent);
        vBox.getChildren().add(hBox);
        return hBox;
    }

    VBox render(){
        return vBox;
    }
}
