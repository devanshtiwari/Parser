package uireturns.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by devanshtiwari on 03-Nov-16.
 */
public class bottomPaneController {
    private AppController appController;

    @FXML
    public TabPane bottomTab;
    @FXML
    public Tab console;

    public void initialize()
    {
        bottomTab.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    public void init(AppController appController) {
        this.appController = appController;
    }
}
