package ui;

import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Created by devanshtiwari on 28-Oct-16.
 */
public class AppController {
    public AnchorPane baseAnchor;
    public TextField proDir;
    public Button fileSelector;
    public ComboBox parseMethod;
    public ComboBox parser;
    public TextField ssPath;
    public Button browseSS;
    public Button fetchHeaders;
    ReadSpreadSheet reader;



    public void initialize(){
        //Bindings for checking whether Project Directory and Spreadsheet Path is empty or not
        BooleanBinding proDirValid = Bindings.createBooleanBinding(() -> {
            return !proDir.getText().isEmpty();
        }, proDir.textProperty());

        BooleanBinding ssPathValid = Bindings.createBooleanBinding(() -> {
            return !ssPath.getText().isEmpty();
        }, ssPath.textProperty());

        fetchHeaders.disableProperty().bind(ssPathValid.not().or(proDirValid.not()));
    }


    //Select Project Directory from Directory chooser
    public void selectProjectDir(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            System.out.println(selectedDir.getAbsolutePath());
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
        String []headers = reader.getHeaders();
        for(String h: headers) {
            System.out.println(h);
        }
    }
}
