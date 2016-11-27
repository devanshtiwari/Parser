package uireturns.controllers;

import javafx.beans.InvalidationListener;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

/**
 * This is a controller Class for Logic Box which is in the middle of the whole UI.
 * @author Avinash and Devansh
 * @since 2016-11-040
 */
public class logicController {
    public VBox centerContainer;

    //Controller instance
    private AppController appController;

    public void init(AppController appController) {
        this.appController = appController;
    }

    /**
     * Addition of CSV View if isCSV is true.
     * @param isCSV True if CSV otherwise False
     */
    public void addCsvView(boolean isCSV) {
        csvParse csvParse = new csvParse(isCSV);
        csvParse.outputReportController = appController.outputReportController;
        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(csvParse.render());
    }

    /**
     * Method to fetch ComboBox of Tags Dyanamically from the Tags Pane.
     * @return Returns ComboBox of Tags
     */
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
