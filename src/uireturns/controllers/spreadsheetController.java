package uireturns.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static uireturns.controllers.AppController.*;
import static uireturns.controllers.ProjectConfigController.*;
import static uireturns.controllers.bottomPaneController.bottomTab;

/**
 * Created by devanshtiwari on 03-Nov-16.
 */
public class spreadsheetController {
    public TextField ssPath;
    public Button browseSS;
    public FlowPane ssHeadersBox;
    public ComboBox fileColumnComboBox;
    public Button readSS;


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

    public void readSS(ActionEvent actionEvent) {
        Tab tab = new Tab("Internal Report");
        TableView<List<String>> table = new TableView<>();
        reader.setFileNameColumn(reader.getColumnIndex((String) fileColumnComboBox.getValue()));
        reader.read();
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
        bottomTab.getTabs().add(tab);
        bottomTab.getSelectionModel().select(tab);
        reader.consoleOut();
    }

    private void initBindings(){
        ssPath.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue)
                fetchHeaders();
        }));

        BooleanBinding readValid = Bindings.createBooleanBinding(() -> {
            return (fileColumnComboBox.getItems().isEmpty());
        }, fileColumnComboBox.valueProperty());

        BooleanBinding indexValid = Bindings.createBooleanBinding(() -> {
            return !indexing.getValue().equals(true);
        }, indexing);

        readSS.disableProperty().bind(Bindings.or(indexValid, readValid));
    }

    //Fetch headers from given spreadsheet
    private void fetchHeaders() {
        if(!ssPath.getText().isEmpty() ) {
            reader = readerFactory.getReader(ssPath.getText());
            String[] headers = reader.getHeaders();
            ssHeadersBox.getChildren().clear();
            for (String header : headers) {
                Label label = new Label(header);
                ssHeadersBox.getChildren().addAll(label);
            }
            fileColumnComboBox.getItems().clear();
            fileColumnComboBox.getItems().addAll(headers);
        }
    }
}
