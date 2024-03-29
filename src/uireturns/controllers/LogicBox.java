package uireturns.controllers;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic Box is the class containing all the components of Logic Box, ie. Conditions, Tag, Methods, Report Generation.
 * @author Avinash and Devansh
 * @since 2016-11-14
 */
public class LogicBox {
    VBox box;
    private HBox logic;
    ComboBox<String> logicType;
    ComboBox<String> conditions;
    ComboBox<String> tags;
    ComboBox<String> methods;
    private Button insertInReport;
    private Button addLogic;
    Button deleteLogic;
    private VBox paramsContainer;
    List<LogicBox> childrens = new ArrayList<>();
    List<ParamBox> paramList;
    List<ReportBox> reportValList;
    LogicBox parent = null;
    private boolean isCSV;

    /**
     * LogicBox Constructor takes parameter isCSV and and then adds up common components , uniquely for Csv and nonCsv.
     * @param isCSV
     */
    LogicBox(boolean isCSV){
        this.isCSV = isCSV;
        box = new VBox();
        box.setSpacing(7);
        box.setPadding(new Insets(0,0,0,20));
        //logicContainer first row
        logic = new HBox();
        logic.setSpacing(5);
        //Param Box
        paramsContainer = new VBox();
        paramsContainer.setSpacing(7);
        //logic Type e.g. Condition/Action/Search/Report
        logicType = new ComboBox<>();
        logicType.getItems().addAll("Condition","Action","Search","Report");
        logicType.setPromptText("Type");
        //Condition Combobox e.g. IF/ELSE IF/ELSE
        conditions = new ComboBox<>();
        conditions.setPromptText("Condition");
        conditions.getItems().addAll("IF","ELSE IF","ELSE");
        //Tags
        tags = logicController.getTagCombobox();
        //Methods
        methods = new ComboBox<>();
        methods.setPromptText("Methods");
        //Buttons
        addLogic = new Button("+");
        deleteLogic = new Button("x");
        //Action settings
        logicType.setOnAction(event -> {
            box.getChildren().remove(paramsContainer);
            for(LogicBox l: childrens)
                box.getChildren().remove(l.render());
            if(logicType.getValue() != null)
                updateLogicRow();
        });
        methods.setOnAction(event -> {
            if(methods.getValue() != null)
               updateParamInputs();
        });
        conditions.setOnAction(event -> {
            if(conditions.getValue() != null)
                updateLogicRowForConditions();
        });
        //Button Actions
        addLogic.setOnAction(event -> {
            addNewLogicBox();
        });
        //insertInReport
        insertInReport = new Button("Insert");
        insertInReport.setOnAction(event -> {
            addReportVal();
        });
        //Vbox addition
        logic.getChildren().addAll(logicType,deleteLogic);
        box.getChildren().addAll(logic);
    }

    /**
     * Updates Logic Row on selection of Condition on IF, ELSE IF or ELSE.
     */
    private void updateLogicRowForConditions() {
        String condition = conditions.getValue();
        switch (condition){
            case "IF":
            case "ELSE IF":
                logic.getChildren().removeAll(methods,tags);
                logic.getChildren().add(2,tags);
                logic.getChildren().add(3,methods);
                methods.getItems().clear();
                methods.getItems().addAll("hasAttr", "checkAttrVal");
                break;
            case "ELSE":
                logic.getChildren().removeAll(methods,tags);


        }
    }

    /**
     * A new logic Box is nested inside one whenever this is called.
     */
    private void addNewLogicBox() {
        LogicBox newLogicBox = new LogicBox(isCSV);
        newLogicBox.deleteLogic.setOnAction(event -> {
            box.getChildren().remove(newLogicBox.render());
            childrens.remove(newLogicBox);
        });
        box.getChildren().add(newLogicBox.render());
        newLogicBox.parent = this;
        childrens.add(newLogicBox);
    }

    /**
     * The conditions, actions variables gets updated whenever there is change in those values.
     */
    private void updateLogicRow() {
        String type = logicType.getValue();

        switch (type) {
            case "Condition":
                logic.getChildren().add(1, conditions);
                logic.getChildren().removeAll(addLogic,methods,insertInReport,tags);
                logic.getChildren().add(2, addLogic);
                if(conditions.getValue() != null)
                    updateLogicRowForConditions();
                break;
            case "Action":
                logic.getChildren().removeAll(conditions, addLogic,methods,insertInReport,tags);
                logic.getChildren().add(1,tags);
                logic.getChildren().add(2,methods);
                methods.getItems().clear();
                methods.getItems().addAll("updateAttr", "insertAttrAtFront", "insertAttrAtEnd", "removeAttr", "removeElement");
                break;
            case "Search":
                logic.getChildren().removeAll(conditions, addLogic,methods,insertInReport,tags);
                logic.getChildren().add(1,tags);
                logic.getChildren().add(2,addLogic);
                break;
            case "Report":
                logic.getChildren().removeAll(conditions, addLogic,methods,insertInReport,tags);
                logic.getChildren().add(1,methods);
                methods.getItems().clear();
                methods.getItems().addAll("initRow", "addValue","nextRow");
                break;
        }
    }

    /**
     * This adds the actions in the ComboBox .
     */
    private void updateParamInputs() {
        String method = methods.getValue();
        switch (method){
            case "hasAttr":
                addParam(new String[]{"Attribute Name"});
                break;
            case "checkAttrVal":
                addParam(new String[]{"Attribute Name","Attribute Value"});
                break;
            case "updateAttr":
                addParam(new String[]{"Attribute Name","Update Value"});
                break;
            case "insertAttrAtFront":
                addParam(new String[]{"Attribute Name","Attribute Value"});
                break;
            case "insertAttrAtEnd":
                addParam(new String[]{"Attribute Name","Attribute Value"});
                break;
            case "removeAttr":
                addParam(new String[]{"Attribute Name"});
                break;
            case "removeElement":
                addParam(new String[]{});
                break;
            case "searchForTag":
                addParam(new String[]{});
                break;
            case "addValue":
                logic.getChildren().remove(insertInReport);
                logic.getChildren().add(2,insertInReport);
                paramsContainer.getChildren().clear();
                addReportVal();
                break;
            case "initRow":
                paramsContainer.getChildren().clear();
                break;
            case "nextRow":
                paramsContainer.getChildren().clear();
                break;

        }
    }

    /**
     * This adds the array of the Strings given in the Parameter in the next line of the UI.
     * @param params
     */
    private void addParam(String[] params) {
        paramsContainer.getChildren().clear();
        paramList = new ArrayList<>();
        for(String param: params){
            ParamBox paramBox = new ParamBox(param,isCSV,true);
            paramsContainer.getChildren().add(paramBox.render());
            paramList.add(paramBox);
        }
        box.getChildren().remove(paramsContainer);
        if(params.length !=0)
            box.getChildren().add(1, paramsContainer);
    }

    /**
     * Report Value is added .
     */
    private void addReportVal(){
        reportValList = new ArrayList<>();
        ReportBox reportBox = new ReportBox(isCSV);
        reportBox.remove.setOnAction(event -> {
            paramsContainer.getChildren().remove(reportBox.render());
            reportValList.remove(reportBox);
        });
        paramsContainer.getChildren().add(reportBox.render());
        reportValList.add(reportBox);
        box.getChildren().remove(paramsContainer);
        box.getChildren().add(1, paramsContainer);
    }

    /**
     * Returns box as Node.
     * @return Node
     */
    public Node render() {
        return box;
    }
}
