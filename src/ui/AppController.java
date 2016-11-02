package ui;

import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class AppController {
    public TextField proDir;
    public Button fileSelector;
    public ComboBox parseMethod;
    public ComboBox parser;
    public TextField ssPath;
    public Button browseSS;
    public FlowPane ssHeadersBox;
    public Button fetchHeaders;
    public ComboBox fileColumnComboBox;
    public Button readSS;
    public TabPane bottomTab;
    public Tab console;
    private ReaderFactory readerFactory = null;
    private ReadSpreadSheet reader = null;
    private Boolean indexing = false;
    private String proDirTextVal = "";
    private indexService indexService = null;

    public void initialize(){
        proDir.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue)
                startIndexing();
        });

        //Bindings for checking whether Project Directory and Spreadsheet Path is empty or not
        BooleanBinding proDirValid = Bindings.createBooleanBinding(() -> {
            return !proDir.getText().isEmpty();
        }, proDir.textProperty());

        BooleanBinding ssPathValid = Bindings.createBooleanBinding(() -> {
            return !ssPath.getText().isEmpty();
        }, ssPath.textProperty());

        BooleanBinding readValid = Bindings.createBooleanBinding(() -> {
            return !fileColumnComboBox.getItems().isEmpty() || indexing;
        }, fileColumnComboBox.valueProperty());

        fetchHeaders.disableProperty().bind(ssPathValid.not().or(proDirValid.not()));
        readSS.disableProperty().bind(readValid.not());

    }

    private void startIndexing(){
        if(!proDirTextVal.equals(proDir.getText())) {
            if(indexService != null){
                indexService.cancel();
            }
            indexService = new indexService();
            indexService.stateProperty().addListener((obs, oldState, newState) -> System.out.println(newState));
            indexService.restart();
            proDirTextVal = proDir.getText();
        }
    }

    //Select Project Directory from Directory chooser
    public void selectProjectDir(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            proDir.setText(selectedDir.getCanonicalPath());
            startIndexing();
        }
    }

    //Select spreadsheet from file Chooser
    public void ssSelector(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select SpreadSheet");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files","*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            ssPath.setText(selectedFile.getCanonicalPath());
        }
    }
    //Fetch headers from given spreadsheet
    public void fetchHeaders(ActionEvent actionEvent) {
        if(!ssPath.getText().isEmpty()&& !fileSelector.getText().isEmpty())
            reader = readerFactory.getReader(ssPath.getText());
        String[] headers = reader.getHeaders();
        ssHeadersBox.getChildren().clear();
        for(String header: headers) {
            Label label = new Label(header);
            ssHeadersBox.getChildren().addAll(label);
        }
        fileColumnComboBox.getItems().clear();
        fileColumnComboBox.getItems().addAll(headers);
    }

    public void readSS(ActionEvent actionEvent) {
        Tab tab = new Tab("Internal Report");
        TableView<List<String>> table = new TableView<>();
        reader.setFileNameColumn(reader.getColumnIndex((String) fileColumnComboBox.getValue()));
        reader.read();
        LinkedHashMap<String, List<String>> report = reader.getReport();
        LinkedHashMap<String, Integer> columns = reader.getColumns();
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
        bottomTab.getTabs().add(tab);
        bottomTab.getSelectionModel().select(tab);
        reader.consoleOut();
    }
    private class indexService extends Service<Void>{
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Inside background thread!");
                    readerFactory = new ReaderFactory(proDir.getText());
                    System.out.println("after index");
                    return null;
                }
            };
        }
    }
}

