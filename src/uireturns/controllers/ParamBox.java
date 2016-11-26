package uireturns.controllers;

import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

/**
 * ParamBox manages the list of Params which can be operated upon to create a single paramBox.
 * @author Avinash and Devansh
 * @since 2016-11-14
 */

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

    /**
     * Parameters can be concatenated using this function.
     * @param paramName
     * @param isCSV
     * @param includeLabel
     */
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
