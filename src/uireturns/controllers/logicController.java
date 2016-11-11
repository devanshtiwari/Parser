package uireturns.controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class logicController {
    public VBox centerContainer;

    //Controller instance
    private AppController appController;
    csvParse csvParse = new csvParse();
    nonCsvParse nonCsvParse = new nonCsvParse();

    public void init(AppController appController) {
        this.appController = appController;
    }

    public void addCsvView() {
        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(csvParse.render());
    }

    public void addNonCsvView() {
        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(new Label("NON CSV VIEW"));
    }
}
