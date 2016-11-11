package uireturns.controllers;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uireturns.controllers.AppController.indexing;
import static uireturns.controllers.AppController.statusBar;

/**
 * Created by avinaana on 11/10/2016.
 */
public class csvParse {
    VBox vBox;
    VBox logicContainer;
    TextField extnsInput;
    TextField rootsInput;
    //Util Variables
    String[] extns = new String[]{};
    String[] roots = new String[]{};
    ArrayList<File> validFiles;
    private parseService parseService = null;
    static List<LogicBox> logicBoxes = new ArrayList<>();
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
        run.setOnAction(event -> {
//            if(parseService != null)
//                parseService.cancel();
//
//            parseService = new parseService();
//            parseService.stateProperty().addListener((obs, oldState, newState) -> System.out.println(newState));
//            parseService.restart();
//            ProgressIndicator progressIndicator = new ProgressIndicator();
//            progressIndicator.setPadding(new Insets(3));
//            progressIndicator.setProgress(progressIndicator.INDETERMINATE_PROGRESS);
//            statusBar.getRightItems().clear();
//            statusBar.getRightItems().addAll(new Label("Parsing"),progressIndicator);
            runIt();

        });
        //Add all to gridPane
        gridPane.add(extns,0,0);
        gridPane.add(extnsInput,1,0);
        gridPane.add(roots,0,1);
        gridPane.add(rootsInput,1,1);
        gridPane.add(buttonBox,0,2,1,GridPane.REMAINING);
        //Logic Box
        logicContainer = new VBox();
        logicContainer.setSpacing(10);

        //add To vbox
        vBox.getChildren().addAll(gridPane, logicContainer);
    }

    VBox render(){
        return vBox;
    }

    private void addLogicBox() {
        LogicBox newLogicBox = new LogicBox();
        newLogicBox.box.setPadding(new Insets(0));
        newLogicBox.deleteLogic.setOnAction(event -> {
            logicContainer.getChildren().remove(newLogicBox.render());
            logicBoxes.remove(newLogicBox);
        });
        logicBoxes.add(newLogicBox);
        logicContainer.getChildren().add(newLogicBox.render());
    }

    private void runIt(){
        if(extnsInput.getText().length() != 0)
            extns = extnsInput.getText().split(",");
        if(rootsInput.getText().length() != 0)
            roots = rootsInput.getText().split(",");
        validFiles = AppController.fastSearch.ExSearch(extns);
        LogicParser logicParser = new LogicParser();
        for(File f : validFiles){
            try {
                System.out.println(f.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            logicParser.parseXML(f,roots);
        }
        logicParser.getOpReport().consoleReport();
    }

    //Parsing Service
    private class parseService extends Service<Void>{

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    runIt();
                    return null;
                }
            };
        }
        @Override
        protected void cancelled() {
            super.cancelled();
            statusBar.getRightItems().clear();
            statusBar.getRightItems().add(new Label("Parsing Cancelled"));
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            statusBar.getRightItems().clear();
            statusBar.getRightItems().addAll(new Label("Parsing Done"));
        }
        @Override
        protected void failed() {
            super.failed();
            super.cancel();
            statusBar.getRightItems().clear();
            statusBar.setText("OK");
            statusBar.getRightItems().addAll(new Label("Parsing Failed."));
        }
    }
}
