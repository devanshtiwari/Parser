package uireturns.controllers;

import com.report.Report;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * outputReportController is the controller for Accordion 2 at left for output report. It controls the read/write of the Report and
 * it can be accesses from main logic box while its creation.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class outputReportController {

    //FXML Variables
    public TextField opReport;
    public TextField opReportPath;
    public Button opReportBrowse;
    public TextField Column1;
    public Button addColumn;
    public GridPane gpane;

    //Main App Controller
    private AppController appController;
    static int count =3;

    //CheckComboBox
    ObservableList<String> cols=FXCollections.observableArrayList(new String[]{Report.FILE_NAME,Report.FILE_PATH});
    CheckComboBox<String> defaultcol = new CheckComboBox(cols);

    //List Available for insertion in Report

    ObservableList<Column> columnList =  FXCollections.observableList(new ArrayList<>());

    //To be used Outside Variables
    static ObservableList<String>  inputColumn = FXCollections.observableArrayList();
    static ObservableList<String> reportColumn = FXCollections.observableArrayList();

    public void init(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){
        defaultcol.setPadding(new Insets(8,8,8,8));
        gpane.add(defaultcol,1,2);
        gpane.setHgrow(defaultcol,Priority.ALWAYS);
        defaultcol.getCheckModel().getCheckedItems().addListener((InvalidationListener) observable -> {
            inputColumn.clear();
            inputColumn.addAll(defaultcol.getCheckModel().getCheckedItems());
        });

        columnList.addListener((InvalidationListener) observable -> {
            reportColumn.clear();
            for(int i=0;i<columnList.size();i++)
                if(!columnList.get(i).name.getText().isEmpty())
                    reportColumn.add(columnList.get(i).name.getText());
        });

    }

    /**
     * Browse option in the File System to select output directory.
     * @param actionEvent Selection of Directory of Report
     * @throws IOException I/O Exception
     */
    public void opReportBrowse(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            opReportPath.setText(selectedDir.getCanonicalPath());
        }
    }

    /**
     * Add Column Action in Output Report Box.
     * @param actionEvent Add Button Action
     */
    public void addColumn(ActionEvent actionEvent) {
        Column temp = new Column();
        columnList.add(temp);
        addColumn.setDisable(true);
    }

    /**
     * This Class is for handling Column Addition in a dynamic Fashion to allow deletion and addition with proper bindings.
     * @author Avinash and Devansh
     * @since 2016-11-14
     */
    class Column
    {
        HBox hbox = new HBox();
        TextField name = new TextField();
        Button addNew = new Button("+");
        Button remove = new Button("-");
        int seq;
        IntegerProperty disable=new SimpleIntegerProperty(0);

        Column(){
            setSeq();
            count++;
            name.setPromptText("Column Name");
            hbox.setPadding(new Insets(4,8,4,0));
            hbox.setHgrow(name, Priority.ALWAYS);
            hbox.setHgrow(addNew,Priority.ALWAYS);
            addNew.setWrapText(true);
            remove.setWrapText(true);
            hbox.getChildren().addAll(name,addNew,remove);
            gpane.add(hbox,1,count);
            addNew.setOnAction(event -> {
                addNew();
            });

            remove.setOnAction(event -> {
                remove();
            });

            name.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != oldValue) {
                    reportColumn.clear();
                    for (int i = 0; i < columnList.size(); i++)
                        reportColumn.add(columnList.get(i).name.getText());
                }
            });

            BooleanBinding addValid = Bindings.createBooleanBinding(()->{
                return !name.getText().equals("") && disable.getValue()==0;
            },name.textProperty(),disable);

            addNew.disableProperty().bind(addValid.not());
        }

        /**
         * Called when Remove button is clicked(-)
         */
        private void remove() {
            if(columnList.contains(this))
            {
                gpane.getChildren().remove(hbox);
                columnList.remove(this);
                gpane.getRowConstraints();
                count--;
            }
            else
                gpane.getChildren().remove((hbox));
            if(columnList.size()==0)
                addColumn.setDisable(false);
            setSeq();
            if(seq!=0) {
                if (columnList.get(seq - 1).seq == columnList.size() - 1) ;
                columnList.get(seq - 1).disable.setValue(0);
            }
        }

        /**
         * Setting up sequence whenever there is change in the list, either deletion or addition.
         */
        private void setSeq() {
            for(int i=0;i<columnList.size();i++)
                columnList.get(i).seq=i;
        }

        /**
         * Called when Add button is clicked (+)
         */
        private void addNew() {
            Column temp = new Column();
            columnList.add(temp);
            setSeq();
            disable.setValue(1);
        }
    }

}
