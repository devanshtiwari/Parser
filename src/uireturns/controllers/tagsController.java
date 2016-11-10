package uireturns.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class tagsController {

    static int i=0;
    //FXML Variables
    public Button addTag;
    public VBox tagContainer;
    static IntegerProperty size = new SimpleIntegerProperty(0);

    //Internal Variables
    private AppController appController;

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

    private class BindingService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Background");
                    while(tags.isEmpty())
                    {
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
                    System.out.println(" Binding Started");
                    return null;
                }
            };
        }
    }


}

