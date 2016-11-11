package uireturns.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class bottomPaneController {
    public TextField consoleText;
    private AppController appController;

    @FXML
    public TabPane bottomTab;
    @FXML
    public Tab console;


    public void init(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){
        consoleText.setText("Welcome to Generic Parser");
        bottomTab.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }
}
