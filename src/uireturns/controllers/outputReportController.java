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
    static int count_2=3;


    //CheckComboBox
    ObservableList<String> cols=FXCollections.observableArrayList(new String[]{Report.FILE_NAME,Report.FILE_PATH});
    CheckComboBox<String> defaultcol = new CheckComboBox(cols);

    //List Available for insertion in Report
    static ArrayList<String>  inputColumn = new ArrayList<>();

    ArrayList<Column> columnList =  new ArrayList<>();

    public void init(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){
        defaultcol.setPadding(new Insets(8,8,8,8));
        gpane.add(defaultcol,1,2);
        defaultcol.getCheckModel().getCheckedItems().addListener((InvalidationListener) observable -> {
            inputColumn.clear();
            inputColumn.addAll(defaultcol.getCheckModel().getCheckedItems());
            System.out.println(inputColumn);
        });
    }

    public void opReportBrowse(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            opReportPath.setText(selectedDir.getCanonicalPath());
        }
    }

    public void addColumn(ActionEvent actionEvent) {
        Column temp = new Column();
        columnList.add(temp);
        addColumn.setDisable(true);
    }


    class Column
    {
        HBox hbox = new HBox();
        TextField name = new TextField();
        Button addNew = new Button("+");
        Button remove = new Button("-");
        int seq=count+1;
        IntegerProperty disable=new SimpleIntegerProperty(0);

        Column(){
            count++;
            name.setPromptText("Column Name");
            hbox.setPadding(new Insets(4,8,4,8));
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

            BooleanBinding addValid = Bindings.createBooleanBinding(()->{
                return !name.getText().equals("") && disable.getValue()==0;
            },name.textProperty(),disable);

            addNew.disableProperty().bind(addValid.not());

    }

        private void remove() {
            if(columnList.contains(this))
            {
                gpane.getChildren().remove(hbox);
                columnList.remove(this);
                gpane.getChildren().remove(seq,seq);
                gpane.getRowConstraints();
                count--;
            }
            else
                gpane.getChildren().remove((hbox));
            if(columnList.size()==0)
                addColumn.setDisable(false);
            //System.out.println(columnList.get(seq-5).seq );
            System.out.println("Column Size "+columnList.size());
            System.out.println("seq"+seq);
            if(columnList.get(seq-5).seq == columnList.size()-5);
                columnList.get(seq-5).disable.setValue(0);
        }

        private void addNew() {
            Column temp = new Column();
            columnList.add(temp);
            disable.setValue(1);
        }
    }
}
