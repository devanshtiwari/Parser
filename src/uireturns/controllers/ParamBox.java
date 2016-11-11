package uireturns.controllers;

import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class ParamBox {
    List<Param> concats;
    private FlowPane box;
    ParamBox(String paramName, Boolean isCSV, Boolean includeLabel){
        concats = new ArrayList<>();
        box = new FlowPane();
        box.setHgap(8);
        box.setVgap(8);
        concatParam(paramName,isCSV,includeLabel);
    }

    private void concatParam(String paramName, Boolean isCSV,Boolean includeLabel){
        Param param = new Param(paramName, isCSV,includeLabel);
        box.getChildren().add(param.render());
        concats.add(param);
        //add Action
        param.concat.setOnAction(event -> {
            concatParam(paramName,isCSV,false);
        });
        //delete Action
        param.delete.setOnAction(event -> {
            box.getChildren().remove(param.render());
            concats.remove(param);
        });
    }
    public FlowPane render(){
        return box;
    }
}
