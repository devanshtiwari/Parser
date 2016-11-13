package uireturns.controllers;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uireturns.controllers.AppController.*;

public class projectConfigController {

    public Label proText;
    private AppController appController;

    public TextField proDir;
    public Button fileSelector;
    public ComboBox parseMethod;
    public ComboBox parser;
    //Variables for private use
    private String proDirTextVal = "";
    private indexService indexService = null;
    public void initialize() throws IOException {
        parser.getItems().add("VTD Parser");
        parser.getSelectionModel().selectFirst();

        proDir.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue) {
                if (!proDirTextVal.equals(proDir.getText())) {
                    try {
                        startIndexing();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                proDirTextVal = proDir.getText();
            }
        }));

        List<String> methods = new ArrayList<String>();
        methods.add("Non CSV");
        methods.add("CSV");
        parseMethod.getItems().addAll(methods);
        parseMethod.getSelectionModel().selectFirst();
    }
    //Select Project Directory from Directory chooser
    public void selectProjectDir(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDir = directoryChooser.showDialog(null);
        if (selectedDir != null) {
            proDir.setText(selectedDir.getCanonicalPath());
            if(!proDirTextVal.equals(proDir.getText())) {
                startIndexing();
                proDirTextVal = proDir.getText();
            }
        }
    }

    private void startIndexing() throws IOException {
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

    public void init(AppController appController) {

        this.appController = appController;
    }

    public void parserMethodSelector(ActionEvent actionEvent) throws IOException {
        if(parseMethod.getValue().toString().equals("CSV")){
            appController.logicController.addCsvView(true);
        }
        else {
            appController.logicController.addCsvView(false);
        }
    }


    //Index Service
    private class indexService extends Service<Void> {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        Label status = new Label();
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    bottomPaneController.consoleText("Indexing the Project Directory "+proDir.getText());
                    AppController.fastSearch.init(proDir.getText());
                    return null;
                }

                @Override
                protected void running() {
                    super.running();
                    statusBar.getRightItems().removeAll(status,progressIndicator);
                    status.setText("Indexing");
                    progressIndicator.setPadding(new Insets(3));
                    progressIndicator.setProgress(progressIndicator.INDETERMINATE_PROGRESS);
                    statusBar.getRightItems().addAll(status,progressIndicator);
                }

                @Override
                protected void cancelled() {
                    super.cancelled();
                    statusBar.getRightItems().removeAll(status,progressIndicator);
                    bottomPaneController.consoleText("Indexing Cancelled! ");
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    statusBar.getRightItems().removeAll(status,progressIndicator);
                    indexing.setValue(true);
                    bottomPaneController.consoleText("Indexing Successful! ");
                    Notifications.create().title("Indexed").text("Indexing Successful").showInformation();
                }
                @Override
                protected void failed() {
                    super.failed();
                    super.cancel();
                    statusBar.getRightItems().removeAll(status,progressIndicator);
                    statusBar.setText("OK");
                    bottomPaneController.consoleText("Indexing Failed!");
                    Notifications.create().title("Indexed").text("Indexing Failed.").showError();
                }
            };
        }
    }
}
