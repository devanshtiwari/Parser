package uireturns.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class bottomPaneController {
    private AppController appController;

    @FXML
    public TabPane bottomTab;
    @FXML
    public Tab console;

    public void init(AppController appController) {
        this.appController = appController;
    }
}
