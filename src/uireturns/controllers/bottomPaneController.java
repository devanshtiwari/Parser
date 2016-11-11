package uireturns.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class bottomPaneController {
    public static TextArea consoleText = new TextArea() ;
    public AnchorPane consoletabPane;
    private AppController appController;

    @FXML
    public TabPane bottomTab;
    @FXML
    public Tab console;


    public void init(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){
        consoleText.setEditable(false);
        consoletabPane.getChildren().add(consoleText);
        AnchorPane.setTopAnchor(consoleText,0.0);
        AnchorPane.setBottomAnchor(consoleText,0.0);
        AnchorPane.setLeftAnchor(consoleText,0.0);
        AnchorPane.setRightAnchor(consoleText,0.0);
        consoleText.setText("Welcome to Generic Parser");
        bottomTab.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }
}
