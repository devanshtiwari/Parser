package uireturns.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

public class outputReportController {
    public TextField opReport;
    public TextField opReportPath;
    public Button opReportBrowse;
    private AppController appController;


    public void init(AppController appController) {
        this.appController = appController;
    }

    public void opReportBrowse(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            opReportPath.setText(selectedDir.getCanonicalPath());
        }
    }
}
