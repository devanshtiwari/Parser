package uireturns.controllers;

import javafx.beans.InvalidationListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * Report Box is referenced in logicParser to generate the report.
 * @author Avinash and Devansh
 * @since 2016-11-14
 *
 */
public class ReportBox {
    GridPane gridPane;
    ComboBox<String> reportColumns;
    ParamBox paramBox;
    Button remove;
    ReportBox(boolean isCSV){
        gridPane = new GridPane();
        reportColumns = new ComboBox<>();
        reportColumns.setPromptText("Column");
        GridPane.setHalignment(reportColumns, HPos.LEFT);
        GridPane.setValignment(reportColumns, VPos.TOP);
        reportColumns.getItems().addAll(outputReportController.reportColumn);
        outputReportController.reportColumn.addListener((InvalidationListener) change -> {
            reportColumns.getItems().clear();
            reportColumns.getItems().addAll(outputReportController.reportColumn);
        });
        //Remove Button
        remove = new Button("Remove");
        paramBox = new ParamBox("Report Value",isCSV,false);
        gridPane.setHgap(10);
        gridPane.add(reportColumns,0,0);
        gridPane.add(paramBox.render(),1,0);
        gridPane.add(remove,2,0);
    }

    public GridPane render(){
        return gridPane;
    }
}
