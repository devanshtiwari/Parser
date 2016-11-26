package uireturns.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * This controller is for Tags, which takes input for the Xpath and then used in the parsing of the file.
 * @author Avinash and Devansh
 * @since 2016-11-14
 */

public class tagsController {

    static int i=0;
    //FXML Variables
    public Button addTag;
    public VBox tagContainer;
    static IntegerProperty size = new SimpleIntegerProperty(0);

    //Internal Variables
    private AppController appController;

    static ObservableMap<String,String> xpathlist = FXCollections.observableHashMap();
    static ObservableList<tagVM> tags = FXCollections.observableArrayList(new ArrayList<tagVM>());
    public void initialize(){
        BindingService b = new BindingService();
        tagContainer.setSpacing(10);
        tagContainer.setPadding(new Insets(10,0,20,0));
        tags.addListener((ListChangeListener)(c -> {
            size.setValue(tags.size());
            b.restart();
        }) );

    }
    public void init(AppController appController) {
        this.appController = appController;
    }

    /**
     * Add Tag Button triggers this method which adds another tagVM class(a container) into the UI.
     * @param actionEvent
     */
    public void addTag(ActionEvent actionEvent) {
        tagVM temp = new tagVM();
        tags.add(temp);
        temp.remove.setOnAction(event -> {
            System.out.println("Removing "+ temp.name.getText());
            tags.remove(temp);
            tagContainer.getChildren().remove(temp.render());
        });
        tagContainer.getChildren().add(1,temp.render());
    }


    /**
     * This Class implements a very important feature for the dynamic updataion of Tags. It keeps running in the background and checks
     * for changes in Text Fields and perform Actions accordingly.
     */
    private class BindingService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while(tags.isEmpty())
                    {
                        //Sleep is performed to make sure it doesn't hang, otherwise it performs the action extremely fast it hangs
                        Thread.sleep(100);
                    }
                    BooleanBinding addValid = Bindings.createBooleanBinding(() -> {
                        if (size.getValue() != 0 && tags != null) {
                            if (!tags.get(size.getValue() - 1).xpath.getText().equals("//"))
                                return false;
                            return true;
                        }
                        return false;
                    }, size,tags.get(size.getValue()-1).xpath.textProperty());
                    addTag.disableProperty().bind(addValid);
                    return null;
                }
            };
        }
    }
}