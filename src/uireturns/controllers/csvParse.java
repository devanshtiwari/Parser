package uireturns.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static uireturns.controllers.AppController.statusBar;

public class csvParse {
    VBox vBox;
    VBox logicContainer;
    TextField extnsInput;
    TextField rootsInput;

    Button run;
    Button cancel;
    //Util Variables
    String[] extns = new String[]{};
    String[] roots = new String[]{};
    ArrayList<File> validFiles;
    private parseService parseService = null;
    static List<LogicBox> logicBoxes = new ArrayList<>();
    IntegerProperty statusValue = new SimpleIntegerProperty(0);
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
        run = new Button("Run");
        cancel = new Button("Cancel");
        buttonBox.getChildren().addAll(addLogicMain, run,cancel);
        addLogicMain.setOnAction(event -> addLogicBox());
        cancel.setDisable(true);
        //Run button Event
        run.setOnAction(event -> {
            if(parseService != null)
                parseService.cancel();

            parseService = new parseService();
            parseService.stateProperty().addListener((obs, oldState, newState) -> System.out.println(newState));
            parseService.restart();
            ProgressIndicator progressIndicatorParser = new ProgressIndicator();
            progressIndicatorParser.setPadding(new Insets(3));
            progressIndicatorParser.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            ProgressBar progress = new ProgressBar();
            progress.setProgress(0);
            progress.progressProperty().unbind();
            progress.progressProperty().bind(parseService.progressProperty());
            statusBar.getRightItems().clear();
            statusBar.getRightItems().addAll(new Label("Parsing"),progress);
            System.gc();
        });

        cancel.setOnAction(event -> {
            parseService.cancel();
        });

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
        LogicBox newLogicBox = new LogicBox();
        newLogicBox.box.setPadding(new Insets(0));
        newLogicBox.deleteLogic.setOnAction(event -> {
            logicContainer.getChildren().remove(newLogicBox.render());
            logicBoxes.remove(newLogicBox);
        });
        logicBoxes.add(newLogicBox);
        logicContainer.getChildren().add(newLogicBox.render());
    }

    //Parsing Service
    private class parseService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    cancel.setDisable(false);
                    run.setDisable(true);
                    bottomPaneController.consoleText.appendText("\nParsing all the Files\n");
                    if(extnsInput.getText().length() != 0)
                        extns = extnsInput.getText().split(",");
                    if(rootsInput.getText().length() != 0)
                        roots = rootsInput.getText().split(",");
                    validFiles = AppController.fastSearch.ExSearch(extns);
                    LogicParser logicParser = new LogicParser();
                    int i=0;
                    int size= validFiles.size();
                    for(File f : validFiles){
                        if(isCancelled())
                            break;
                        logicParser.parseXML(f,roots);
                        updateProgress(i++,size);
                    }
                    logicParser.getOpReport().consoleReport();
                    return null;
                }

                @Override
                protected void cancelled() {
                    super.cancelled();
                    super.cancel();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().add(new Label("Parsing Cancelled"));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    bottomPaneController.consoleText.appendText("Parsing Cancelled\n");
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().addAll(new Label("Parsing Done"));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    bottomPaneController.consoleText.appendText("Parsing Successful!\n");
                }

                @Override
                protected void failed() {
                    super.failed();
                    super.cancel();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().addAll(new Label("Parsing Failed."));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    bottomPaneController.consoleText.appendText("Parsing Failed\n");
                }
            };
        }
    }
}
