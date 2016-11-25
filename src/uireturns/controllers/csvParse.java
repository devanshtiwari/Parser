package uireturns.controllers;

import com.FastSearch.FastSearch;
import com.filemanager.ssIterator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static uireturns.controllers.AppController.*;
public class csvParse {
    private VBox vBox;
    private VBox logicContainer;
    private TextField extnsInput;
    private TextField rootsInput;
    private TextField pathCheckInput;
    private ComboBox<String> csvColumns;
    private ComboBox<String> pathCheckType;
    private Button run;
    private Button cancel;

    //Util Variables
    private String[] extns = new String[]{};
    private String[] roots = new String[]{};
    private ArrayList<File> validFiles;
    private nonCsvParseService nonCsvParseService = null;
    private csvParseService csvParseService = null;
    static List<LogicBox> logicBoxes = new ArrayList<>();
    public boolean isCSV;
    static ssIterator iter;
    IntegerProperty countfile = new SimpleIntegerProperty(0);
    outputReportController outputReportController;
    csvParse(boolean isCSV){
        this.isCSV = isCSV;
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
        //pathCheck
        Label pathCheck = new Label("Check in Path");
        pathCheckInput = new TextField();
        pathCheckType = new ComboBox<>();
        pathCheckType.getItems().addAll("By String","By Keywords");
        pathCheckType.setOnAction(event -> {
            String type = pathCheckType.getValue();
            if(type.equals("By String")){
                pathCheckInput.setPromptText("Enter a substring of path");
            }
            else if(type.equals("By Keywords")){
                pathCheckInput.setPromptText("Enter Keywords of path (Comma Seperated)");
            }
        });
        pathCheckType.getSelectionModel().selectFirst();
        pathCheckType.fireEvent(new ActionEvent());
        HBox pathCheckHBox = new HBox();
        pathCheckHBox.setSpacing(8);
        HBox.setHgrow(pathCheckInput,Priority.ALWAYS);
        pathCheckHBox.getChildren().addAll(pathCheckType,pathCheckInput);
        //CSV columns
        Label fileCol = new Label("File Column");
        if(isCSV){
            csvColumns = spreadsheetController.getHeaderComboBox();
            csvColumns.setPromptText("Select Column");
        }
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
            if(isCSV)
                startCsvParseService();
            else
                startNoncsvParseService();

        });

        cancel.setOnAction(event -> {
            if(isCSV)
                csvParseService.cancel();
            else
                nonCsvParseService.cancel();
        });

        //Add all to gridPane
        gridPane.add(extns,0,0);
        gridPane.add(extnsInput,1,0);
        gridPane.add(roots,0,1);
        gridPane.add(rootsInput,1,1);
        gridPane.add(pathCheck,0,2);
        gridPane.add(pathCheckHBox,1,2);
        if(isCSV){
            gridPane.add(fileCol,0,3);
            gridPane.add(csvColumns,1,3);
        }
        gridPane.add(buttonBox,0,4,GridPane.REMAINING,1);
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
        LogicBox newLogicBox = new LogicBox(isCSV);
        newLogicBox.box.setPadding(new Insets(0));
        newLogicBox.deleteLogic.setOnAction(event -> {
            logicContainer.getChildren().remove(newLogicBox.render());
            logicBoxes.remove(newLogicBox);
        });
        logicBoxes.add(newLogicBox);
        logicContainer.getChildren().add(newLogicBox.render());
    }

    private void startNoncsvParseService(){
        if(nonCsvParseService != null)
            nonCsvParseService.cancel();

        nonCsvParseService = new nonCsvParseService();
        nonCsvParseService.stateProperty().addListener((obs, oldState, newState) -> System.out.println(newState));
        nonCsvParseService.restart();
        ProgressIndicator progressIndicatorParser = new ProgressIndicator();
        progressIndicatorParser.setPadding(new Insets(3));
        progressIndicatorParser.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        ProgressBar progress = new ProgressBar();
        progress.setProgress(0);
        progress.progressProperty().unbind();
        progress.progressProperty().bind(nonCsvParseService.progressProperty());
        statusBar.getRightItems().clear();
        statusBar.getRightItems().addAll(new Label("Parsing"),progress);
        System.gc();
    }
    private void startCsvParseService(){
        if(csvParseService != null)
            csvParseService.cancel();

        csvParseService = new csvParseService();
        csvParseService.stateProperty().addListener((obs, oldState, newState) -> System.out.println(newState));
        csvParseService.restart();
        ProgressIndicator progressIndicatorParser = new ProgressIndicator();
        progressIndicatorParser.setPadding(new Insets(3));
        progressIndicatorParser.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        ProgressBar progress = new ProgressBar();
        progress.setProgress(0);
        progress.progressProperty().unbind();
        progress.progressProperty().bind(csvParseService.progressProperty());
        statusBar.getRightItems().clear();
        statusBar.getRightItems().addAll(new Label("Parsing"),progress);
        System.gc();
    }

    //Parsing Service
    private class nonCsvParseService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                LogicParser logicParser;

                @Override
                protected Void call() throws Exception {
                    countfile.setValue(0);
                    cancel.setDisable(false);
                    run.setDisable(true);
                    bottomPaneController.consoleText("Parsing all the Files");
                    if(extnsInput.getText().length() != 0)
                        extns = extnsInput.getText().split(",");
                    if(rootsInput.getText().length() != 0)
                        roots = rootsInput.getText().split(",");
                    validFiles = AppController.fastSearch.ExSearch(extns);
                    logicParser = new LogicParser();
                    int i=0;
                    int size= validFiles.size();
                    for(File f : validFiles){
                        countfile.setValue(countfile.getValue() +1);
                        if(isCancelled())
                            break;
                        if(checkInPath(f)) {
                            logicParser.parseXML(f, roots);
                        }
                        updateProgress(i++, size);
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
                    bottomPaneController.consoleText("Parsing Cancelled");
                    Notifications.create().title("Parsed Files").text("Parsing Cancelled").showWarning();
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().addAll(new Label("Parsing Done"));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    if(!outputReportController.opReportPath.getText().isEmpty() && !outputReportController.opReport.getText().isEmpty()){
                        logicParser.getOpReport().saveCSV(outputReportController.opReportPath.getText(),outputReportController.opReport.getText());
                        Notifications.create().title("CSV Notification").text("CSV Report Saved Successfully").showInformation();
                    }
                    else
                    {
                        bottomPaneController.consoleText("File Save Unsuccessful: Path or Name Empty");
                    }
                    bottomPaneController.consoleText("Parsing Successful!");
                    bottomPaneController.consoleText("Successfully Parsed "+countfile.getValue()+" Files");
                    Notifications.create().title("Parsed Files").text("Parsed "+countfile.getValue()+ " Files").showInformation();
                }

                @Override
                protected void failed() {
                    super.failed();
                    super.cancel();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().addAll(new Label("Parsing Failed."));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    bottomPaneController.consoleText("Parsing Failed");
                    Notifications.create().title("Parsed Files").text("Parsing Failed").showError();
                }
            };
        }
    }
    private class csvParseService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                LogicParser logicParser;
                @Override
                protected Void call() throws Exception {
                    countfile.setValue(0);
                    cancel.setDisable(false);
                    run.setDisable(true);
                    bottomPaneController.consoleText("Parsing all the Files");
                    if(extnsInput.getText().length() != 0)
                        extns = extnsInput.getText().split(",");
                    if(rootsInput.getText().length() != 0)
                        roots = rootsInput.getText().split(",");
                    //File Column
                    String fileColumn = csvColumns.getValue();
                    logicParser = new LogicParser();
                    int i=0;
                    int size= reader.getReport().size();
                    iter = reader.getIterator();
                    fastSearch.setExtensions(extns);
                    while (iter.hasNext()){
                        if(isCancelled())
                            break;
                        iter.next();
                        ArrayList<File> files = fastSearch.Fsearch(iter.getValue(fileColumn),true);
                        for(File f: files) {
                            countfile.setValue(countfile.getValue()+1);
                            if (checkInPath(f)) {
                                logicParser.parseXML(f, roots);
                            }
                        }
                        updateProgress(i++, size);
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
                    bottomPaneController.consoleText("Parsing Cancelled");
                    Notifications.create().title("Parsed Files").text("Parsing Cancelled").showWarning();
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().addAll(new Label("Parsing Done"));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    if(!outputReportController.opReportPath.getText().isEmpty() && !outputReportController.opReport.getText().isEmpty()){
                        logicParser.getOpReport().saveCSV(outputReportController.opReportPath.getText(),outputReportController.opReport.getText());
                        Notifications.create().title("CSV Notification").text("CSV Report Saved Successfully").showInformation();
                    }
                    else
                    {
                        bottomPaneController.consoleText("Output Report File Save Unsuccessful: Path or Name Empty");
                    }
                    bottomPaneController.consoleText("Parsing Successful!");
                    bottomPaneController.consoleText("Successfully Parsed "+countfile.getValue()+" Files");
                    Notifications.create().title("Parsed Files").text("Parsed "+countfile.getValue()+ " Files").showInformation();
                }

                @Override
                protected void failed() {
                    super.failed();
                    super.cancel();
                    statusBar.getRightItems().clear();
                    statusBar.getRightItems().addAll(new Label("Parsing Failed."));
                    run.setDisable(false);
                    cancel.setDisable(true);
                    bottomPaneController.consoleText("Parsing Failed");
                    Notifications.create().title("Parsed Files").text("Parsing Failed").showError();
                }
            };
        }
    }

    private boolean checkInPath(File file) {
        String type = pathCheckType.getValue();
        if(pathCheckInput.getText().length() != 0) {
            if (type.equals("By String")) {
                return FastSearch.pathCheckBySubstring(pathCheckInput.getText(), file);
            } else if (type.equals("By Keywords")) {
                String[] pathChecks = pathCheckInput.getText().split(",");
                return FastSearch.pathCheckByKeywords(pathChecks,file);
            }
        }
        return true;
    }
}
