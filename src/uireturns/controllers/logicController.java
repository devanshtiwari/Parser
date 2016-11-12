package uireturns.controllers;

import javafx.beans.InvalidationListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class logicController {
    public VBox centerContainer;

    //Controller instance
    private AppController appController;

    public void init(AppController appController) {
        this.appController = appController;
    }

    public void addCsvView(boolean isCSV) {
        csvParse csvParse = new csvParse(isCSV);
        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(csvParse.render());
    }

    public static ComboBox<String> getTagCombobox(){
        ComboBox<String> tags = new ComboBox<>();
        tags.setPromptText("Tags");
        tags.getItems().clear();
        for(tagVM t : tagsController.tags){
            tags.getItems().add(t.tag.getName());
        }
        tagsController.xpathlist.addListener((InvalidationListener) change -> {
            tags.getItems().clear();
            tags.getItems().addAll(tagsController.xpathlist.keySet());
        });
        tagsController.tags.addListener((InvalidationListener) change -> {
            tags.getItems().clear();
            for(tagVM t : tagsController.tags){
                tags.getItems().add(t.tag.getName());
            }
        });
        return tags;
    }
}
