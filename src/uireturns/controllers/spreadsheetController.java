package uireturns.controllers;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static uireturns.controllers.AppController.*;


public class spreadsheetController {
    AppController appController;

    public TextField ssPath;
    public Button browseSS;
    public FlowPane ssHeadersBox;

    //Util variables
    private readService readService = null;

    public void initialize(){
        initBindings();
    }


    //Select spreadsheet from file Chooser
    public void ssSelector(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select SpreadSheet");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files","*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            ssPath.setText(selectedFile.getCanonicalPath());
            fetchHeaders();
        }
    }

    private void readSS() {
        if(readService != null)
            readService.cancel();

        readService = new readService();
        readService.stateProperty().addListener((obs, oldState, newState) -> System.out.println(newState));
        readService.restart();
        //Status
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setPadding(new Insets(2));
        statusBar.setText("Processing...");
        statusBar.getRightItems().clear();
        statusBar.getRightItems().addAll(new Label("Reading SpreadSheet"),progressIndicator);

    }
    private void generateInternalRepotTable(){
        Tab tab = new Tab("Internal Report");
        TableView<List<String>> table = new TableView<>();

        LinkedHashMap<String, List<String>> report = reader.getReport();
        LinkedHashMap<String, Integer> columns = reader.getColumns();

        TableColumn<List<String>,Number> SNCol = new TableColumn<>("#");
        SNCol.setSortable(false);
        SNCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>((table.getItems().indexOf(column.getValue()))+1));
        table.getColumns().add(SNCol);
        for (String s : columns.keySet()) {
            TableColumn<List<String>,String> col = new TableColumn<>(s);
            col.setCellValueFactory(data -> {
                List<String> rowValues = data.getValue();
                String cellValue = rowValues.get(reader.getColumnIndex(s));
                return new ReadOnlyStringWrapper(cellValue);
            });
            table.getColumns().add(col);
        }
        for(String key: report.keySet()){
            table.getItems().add(report.get(key));
        }
        tab.setContent(table);
        appController.bottomPaneController.bottomTab.getTabs().add(tab);
        appController.bottomPaneController.bottomTab.getSelectionModel().select(tab);
        reader.consoleOut();
    }

    private void initBindings(){
        ssPath.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue)
                fetchHeaders();
        }));
    }

    //Fetch headers from given spreadsheet
    private void fetchHeaders() {
        if(!ssPath.getText().isEmpty()) {
            reader = readerFactory.getReader(ssPath.getText());
            String[] headers = reader.getHeaders();
            ssHeadersBox.getChildren().clear();
            for (String header : headers) {
                Label label = new Label(header);
                ssHeadersBox.getChildren().addAll(label);
            }
            readSS();
        }
    }

    public void init(AppController appController) {
        this.appController = appController;
    }

    //Read SpreadSheet Service
   private class readService extends Service<Void>{

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Inside Read SS Service!!");
                    reader.read();
                    System.out.println("Reading done!");
                    return null;
                }

                @Override
                protected void cancelled() {
                    super.cancelled();
                    statusBar.getRightItems().clear();
                    statusBar.setText("OK");
                    statusBar.getRightItems().add(new Label("Reading cancelled."));
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    generateInternalRepotTable();
                    statusBar.getRightItems().clear();
                    statusBar.setText("OK");
                    statusBar.getRightItems().addAll(new Label("Reading completed."));
                }
            };
        }
    }
}
