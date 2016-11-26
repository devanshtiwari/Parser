package uireturns.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Param Class handles the paramter in Logic Box Row. It includes all the fields like csvColumns, inputType etc.
 * @author Avinash and Devansh
 * @since 2016-11-14
 */
public class Param {
    private HBox hbox;
    ComboBox<String> inputType;
    TextField inputField;
    ComboBox<String> tags;
    ComboBox<String> csvColumns;
    Button concat;
    Button delete;
    private int insertIndex;
    private boolean includeLabel;
    Param(String param, boolean isCSV,boolean includeLabel){
        //class variables
        this.includeLabel = includeLabel;
        hbox = new HBox();
        hbox.setSpacing(5);
        Label paramName = new Label(param);
        //Input Type
        this.inputType = new ComboBox<>();
        this.inputType.setPromptText("Input Type");
        this.inputType.getItems().addAll("TextField","From File Path","Attribute Value");
        if(isCSV){
            this.inputType.getItems().add("From CSV");
        }
        this.inputType.setOnAction(event -> {
            if(inputType.getValue() != null)
                updateBox();
        });
        //Input Field
        this.inputField = new TextField();
        //tags
        this.tags = logicController.getTagCombobox();
        //CSV columns
        if(isCSV){
            this.csvColumns = spreadsheetController.getHeaderComboBox();
        }
        //Concat Button
        this.concat = new Button("+");
        //Delete Button
        this.delete = new Button("x");
        //Make HBox
        if(includeLabel) {
            hbox.getChildren().addAll(paramName, inputType, delete, concat);
            insertIndex = 2;
        }
        else{
            hbox.getChildren().addAll(inputType, delete, concat);
            insertIndex = 1;
        }
        this.inputType.getSelectionModel().selectFirst();
        this.inputType.fireEvent(new ActionEvent());
    }

    /**
     * This called whenever these is any updation of values in other fields.
     */
    private void updateBox() {
        String inputType = this.inputType.getValue();
        switch (inputType){
            case "TextField":
                hbox.getChildren().removeAll(inputField,csvColumns,tags);
                hbox.getChildren().add(insertIndex,inputField);
                inputField.setPromptText("Enter Value");
                break;
            case "From File Path":
                hbox.getChildren().removeAll(inputField,csvColumns,tags);
                hbox.getChildren().add(insertIndex,inputField);
                inputField.setPromptText("Enter Index");
                break;
            case "Attribute Value":
                hbox.getChildren().removeAll(inputField,csvColumns,tags);
                hbox.getChildren().add(insertIndex,tags);
                hbox.getChildren().add(insertIndex+1,inputField);
                inputField.setPromptText("Enter Attribute Name");
                break;
            case "From CSV":
                hbox.getChildren().removeAll(inputField,csvColumns);
                if(includeLabel)
                    hbox.getChildren().add(2,csvColumns);
                else
                    hbox.getChildren().add(1,csvColumns);
                csvColumns.setPromptText("Select");
                break;
        }
    }

    public HBox render(){
        return hbox;
    }
}
