package uireturns.controllers;

import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class logicBox {
    VBox box;
    HBox logic;
    ComboBox<String> logicType;
    ComboBox<String> conditions;
    ComboBox tags;
    ComboBox<String> methods;
    Button addLogic;
    Button deleteLogic;
    VBox paramBox;
    List<logicBox> childrens = new ArrayList<>();

    logicBox(){
        box = new VBox();
        box.setSpacing(7);
        box.setPadding(new Insets(0,0,0,20));
        //logicContainer first row
        logic = new HBox();
        logic.setSpacing(5);
        //Param Box
        paramBox = new VBox();
        paramBox.setSpacing(7);
        //logic Type e.g. Condition/Action/Search/Report
        logicType = new ComboBox<>();
        logicType.getItems().addAll("Condition","Action","Search","Report");
        logicType.setPromptText("Type");
        //Condition Combobox e.g. IF/ELSE IF/ELSE
        conditions = new ComboBox<>();
        conditions.setPromptText("Condition");
        conditions.getItems().addAll("IF","ELSE IF","ELSE");
        //Tags
        tags = new ComboBox<>();
        tags.setPromptText("Tags");
        tags.getItems().clear();
        for(tagVM t : tagsController.tags){
            tags.getItems().add(t.tag.getName());
        }
        tagsController.xpathlist.addListener((InvalidationListener) change -> {
            tags.getItems().clear();
            tags.getItems().addAll(tagsController.xpathlist.keySet());
        });
        tagsController.tags.addListener((InvalidationListener) change -> {
            tags.getItems().clear();
            for(tagVM t : tagsController.tags){
                tags.getItems().add(t.tag.getName());
            }
        });
        //Methods
        methods = new ComboBox<>();
        methods.setPromptText("Methods");
        //Buttons
        addLogic = new Button("+");
        deleteLogic = new Button("x");
        //Action settings
        logicType.setOnAction(event -> {
            box.getChildren().remove(paramBox);
            for(logicBox l: childrens)
                box.getChildren().remove(l.render());
            if(logicType.getValue() != null)
                updateLogicRow();
        });
        methods.setOnAction(event -> {
            if(methods.getValue() != null)
               updateParamInputs();
        });
        //Button Actions
        addLogic.setOnAction(event -> {
            addNewLogicBox();
        });
        //Vbox addition
        logic.getChildren().addAll(logicType,tags,methods,deleteLogic);
        box.getChildren().addAll(logic);
    }

    private void addNewLogicBox() {
        logicBox newLogicBox = new logicBox();
        newLogicBox.deleteLogic.setOnAction(event -> {
            box.getChildren().remove(newLogicBox.render());
            childrens.remove(newLogicBox);
        });
        box.getChildren().add(newLogicBox.render());
        childrens.add(newLogicBox);
    }

    private void updateLogicRow() {
        String type = logicType.getValue();

        switch (type) {
            case "Condition":
                logic.getChildren().add(1, conditions);
                logic.getChildren().remove(addLogic);
                logic.getChildren().add(4, addLogic);
                methods.getItems().clear();
                methods.getItems().addAll("hasAttr", "checkAttrVal");
                break;
            case "Action":
                logic.getChildren().removeAll(conditions, addLogic);
                methods.getItems().clear();
                methods.getItems().addAll("updateAttr", "insertAttrAtFront", "insertAttrAtEnd", "removeAttr", "removeElement");
                break;
            case "Search":
                logic.getChildren().removeAll(conditions, addLogic);
                logic.getChildren().add(3,addLogic);
                methods.getItems().clear();
                methods.getItems().addAll("searchForTag");
                break;
            case "Report":
                logic.getChildren().removeAll(conditions, addLogic);
                methods.getItems().clear();
                methods.getItems().addAll("addToReport");
                break;
        }
    }
    private void updateParamInputs() {
        String method = methods.getValue();
        switch (method){
            case "hasAttr":
                addParam(new String[]{"Attribute Name"});
                break;
            case "checkAttrVal":
                addParam(new String[]{"Attribute Name","Attribute Value"});
                break;
            case "updateAttr":
                addParam(new String[]{"Attribute Name","Update Value"});
                break;
            case "insertAttrAtFront":
                addParam(new String[]{});
                break;
            case "insertAttrAtEnd":
                addParam(new String[]{"Attribute Name","Attribute Value"});
                break;
            case "removeAttr":
                addParam(new String[]{});
                break;
            case "removeElement":
                addParam(new String[]{});
                break;
            case "searchForTag":
                addParam(new String[]{});
                break;
            case "addToReport":
                addParam(new String[]{});
                break;
        }
    }

    private void addParam(String[] params) {
        paramBox.getChildren().clear();
        for(String param: params){
            HBox hbox = new HBox();
            TextField input = new TextField();
            input.setPromptText(param);
            hbox.getChildren().addAll(input);
            paramBox.getChildren().add(hbox);
        }
        box.getChildren().remove(paramBox);
        if(params.length !=0)
            box.getChildren().add(1,paramBox);
    }

    public Node render() {
        return box;
    }
}
