package ui;

import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import com.filemanager.ssIterator;
import com.xpathgenerator.Tag;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Table;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by devanshtiwari on 28-Oct-16.
 */
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
    ReadSpreadSheet reader;
    String[] headers;



    public void initialize(){
        //Bindings for checking whether Project Directory and Spreadsheet Path is empty or not
        BooleanBinding proDirValid = Bindings.createBooleanBinding(() -> {
            return !proDir.getText().isEmpty();
        }, proDir.textProperty());

        BooleanBinding ssPathValid = Bindings.createBooleanBinding(() -> {
            return !ssPath.getText().isEmpty();
        }, ssPath.textProperty());

        BooleanBinding readValid = Bindings.createBooleanBinding(() -> {
            return !fileColumnComboBox.getItems().isEmpty();
        }, fileColumnComboBox.valueProperty());

        fetchHeaders.disableProperty().bind(ssPathValid.not().or(proDirValid.not()));
        readSS.disableProperty().bind(readValid.not());
    }


    //Select Project Directory from Directory chooser
    public void selectProjectDir(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            proDir.setText(selectedDir.getCanonicalPath());
        } else {
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
        reader = new ReaderFactory().getReader(ssPath.getText(),proDir.getText());
        headers = reader.getHeaders();
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
        final int[] c = {0};
        for (String s : columns.keySet()) {
            TableColumn<List<String>,String> col = new TableColumn<>(s);
                col.setCellValueFactory(data -> {
                    List<String> rowValues = data.getValue();
                    String cellValue = rowValues.get(reader.getColumnIndex(s));
                    System.out.println("Data"+(++c[0])+" = " + cellValue);
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
}
