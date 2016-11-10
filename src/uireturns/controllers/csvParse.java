package uireturns.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by avinaana on 11/10/2016.
 */
public class csvParse {
    VBox vBox;
    VBox logicContainer;
    TextField extnsInput;
    TextField rootsInput;
    //Util Variables
    String[] extns;
    String[] roots;
    ArrayList<File> validFiles;
    List<logicBox> logicBoxes = new ArrayList<>();
    csvParse(){
        vBox = new VBox();
        vBox.setSpacing(10);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //Extensions
        Label extns = new Label("Extensions");
        extnsInput = new TextField();
        extnsInput.setPromptText("Enter Extensions Names (Comma separated)");
        GridPane.setHgrow(extnsInput,Priority.ALWAYS);
        //Roots
        Label roots = new Label("Root Elements");
        rootsInput = new TextField();
        rootsInput.setPromptText("Enter Root Element Names (Comma separated)");
        GridPane.setHgrow(rootsInput,Priority.ALWAYS);
        //Buttons
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        Button addLogicMain = new Button("Add Logic");
        Button run = new Button("Run");
        buttonBox.getChildren().addAll(addLogicMain, run);
        addLogicMain.setOnAction(event -> addLogicBox());
        //Run button Event
        run.setOnAction(event -> runIt());
        //Add all to gridPane
        gridPane.add(extns,0,0);
        gridPane.add(extnsInput,1,0);
        gridPane.add(roots,0,1);
        gridPane.add(rootsInput,1,1);
        gridPane.add(buttonBox,0,2,1,GridPane.REMAINING);
        //Logic Box
        logicContainer = new VBox();

        //add To vbox
        vBox.getChildren().addAll(gridPane, logicContainer);
    }

    VBox render(){
        return vBox;
    }

    private void addLogicBox() {
        logicBox newLogicBox = new logicBox();
        newLogicBox.box.setPadding(new Insets(0));
        newLogicBox.deleteLogic.setOnAction(event -> {
            logicContainer.getChildren().remove(newLogicBox.render());
            logicBoxes.remove(newLogicBox);
        });
        logicBoxes.add(newLogicBox);
        logicContainer.getChildren().add(newLogicBox.render());
    }

    private void runIt(){
        extns = extnsInput.getText().split(",");
        roots = rootsInput.getText().split(",");
        validFiles = AppController.fastSearch.ExSearch(extns);
        for(File f : validFiles){
            System.out.println(f.getName());
        }
    }
}
