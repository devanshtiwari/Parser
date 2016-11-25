package uireturns.controllers;

import com.FastSearch.FastSearch;
import com.parser.Element;
import com.parser.Parser;
import com.parser.ParserFactory;
import com.parser.VTDParser;
import com.report.Report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogicParser {
    private Report opReport;
    private VTDParser vtdParser;
    private HashMap<String ,Element> elements;
    private List<String> parameters;
    LogicParser() {
        opReport = new Report();
        opReport.addColumn(new String[]{Report.SNO});
        if(outputReportController.inputColumn.size() == 1){
            opReport.addColumn(outputReportController.inputColumn.get(0));
            if(outputReportController.reportColumn.size() > 0)
                for(String column: outputReportController.reportColumn)
                    opReport.addColumn(column);
        }
        else if(outputReportController.inputColumn.size() == 2){
            opReport.addColumn(outputReportController.inputColumn.get(0));
            if(outputReportController.reportColumn.size() > 0)
                for(String column: outputReportController.reportColumn)
                    opReport.addColumn(column);
            opReport.addColumn(outputReportController.inputColumn.get(1));
        }
        opReport.setDefaultKey();
        Parser parser = ParserFactory.getParser(ParserFactory.Parsers.VTD);
        vtdParser = (VTDParser) parser;
        elements = new HashMap<>();
        parameters = new ArrayList<>();
    }
    public void parseXML(File file, String[] roots) {
        System.out.println("Inside ParseXML");
        System.out.println(file.getAbsolutePath());
        vtdParser.parse(file);
        createElementFromTags();
        if(vtdParser.checkRootFor(roots)){
            try{
            csvParse.logicBoxes.forEach((box) -> runLogic(box, file));
            opReport.incrementKey();}
            catch(Exception e)
            {
                e.printStackTrace();
            }

            }
    }

    public Report getOpReport() {
        return opReport;
    }

    private void runLogic(LogicBox box, File file) {
        String type = box.logicType.getValue();
        switch (type) {
            case "Condition":
               runCondition(box,file);
                break;
            case "Action":
                doAction(box,file);
                break;
            case "Search":
                search(box,file);
               break;
            case "Report":
                report(box,file);
                break;
        }
    }

    private void report(LogicBox box,File file) {
        String method = box.methods.getValue();
        switch (method){
            case "initRow":
                opReport.initRow(file);
                break;
            case "addValue":
                evalReportBox(box,file);
                break;
            case "nextRow":
                opReport.incrementKey();
                break;
        }
    }

    private void search(LogicBox box,File file) {
        Element e = elements.get(box.tags.getValue());
        while(e.next() != -1)
            processChildren(box,file);
    }

    private void doAction(LogicBox box, File file) {
        Element e = elements.get(box.tags.getValue());
        String method = box.methods.getValue();
        switch (method){
            case "updateAttr":
                parameters = evalParmBox(box,file);
                e.updateAttr(parameters.get(0),parameters.get(1),file);
                break;
            case "insertAttrAtFront":
                parameters = evalParmBox(box,file);
                String insertAtFront = parameters.get(0)+"=\""+parameters.get(1)+"\"";
                e.insertAttr(insertAtFront,file);
                break;
            case "insertAttrAtEnd":
                parameters = evalParmBox(box,file);
                String insert = parameters.get(0)+"=\""+parameters.get(1)+"\"";
                e.insertAtEnd(insert,file);
                break;
            case "removeAttr":
                parameters = evalParmBox(box,file);
                e.removeAttribute(parameters.get(0),file);
                break;
            case "removeElement":
                e.removeElement(file);
                break;
        }
    }

    private void runCondition(LogicBox box, File file) {
        String condition = box.conditions.getValue();
        switch (condition){
            case "IF":
                runIf(box,file);
                break;
            case "ELSE IF":
                runElseIf(box,file);
                break;
            case "ELSE":
                runElse(box,file);
                break;
        }
    }

    private void runElse(LogicBox box, File file) {
        System.out.println("Inside Else");
        System.out.println("evalParentCondition: "+evalParentConditions(box,file));
        if(evalParentConditions(box,file))
            processChildren(box,file);
    }

    private void runElseIf(LogicBox box, File file) {
        System.out.println("Inside runElseIf");
        System.out.println(evalCondition(box,file));
        System.out.println(evalParentConditions(box,file));
        if(evalCondition(box,file)){
            if(evalParentConditions(box,file))
                processChildren(box,file);
        }
    }

    private void runIf(LogicBox box, File file) {
        System.out.println("inside runIf");
        System.out.println("Result: " +evalCondition(box,file));
        if(evalCondition(box,file))
            processChildren(box,file);
    }
    private List<String> evalParmBox(LogicBox box, File file){
        List<ParamBox> paramBoxes = box.paramList;
        List<String> params = new ArrayList<>();
        for(ParamBox paramBox: paramBoxes){
            List<Param> concats = paramBox.concats;
            String parameter = "";
            for(Param param: concats){
                parameter += evalParam(param, file);
            }
            params.add(parameter);
        }
        System.out.println(params.toString());
        return params;
    }
    private String evalParam(Param param,File file){
        String parameter = "";
        String type = param.inputType.getValue();
        switch (type){
            case "TextField":
                parameter = param.inputField.getText();
                break;
            case "From File Path":
                try {
                    parameter = FastSearch.getValueFromFilePath(file.getCanonicalPath(),Integer.parseInt(param.inputField.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Attribute Value":
                Element e = elements.get(param.tags.getValue());
                parameter = e.getAttrVal(param.inputField.getText());
                break;
            case "From CSV":
                parameter = csvParse.iter.getValue(param.csvColumns.getValue());
                break;
        }
        return parameter;
    }
    private void evalReportBox(LogicBox box,File file){
        List<ReportBox> reportBoxList = box.reportValList;
        for(ReportBox reportBox: reportBoxList){
            String column = reportBox.reportColumns.getValue();
            List<Param> concats = reportBox.paramBox.concats;
            String value = "";
            for(Param param: concats){
                value += evalParam(param, file);
            }
            opReport.addValue(column,value);
        }
    }
    private Boolean evalCondition(LogicBox box,File file){
        Element e = elements.get(box.tags.getValue());
        String method = box.methods.getValue();
        Boolean result = false;
        switch(method){
            case "hasAttr":
                parameters = evalParmBox(box,file);
                result = e.hasAttr(parameters.get(0));
                System.out.println("hasAttr: "+ e.hasAttr(parameters.get(0)));
                break;
            case "checkAttrVal":
                parameters = evalParmBox(box,file);
                if(e.hasAttr(parameters.get(0)))
                    result = e.getAttrVal(parameters.get(0)).equals(parameters.get(1));
                else
                    result = false;
                break;
        }
        return result;
    }

    private Boolean evalParentConditions(LogicBox box, File file){
        List<LogicBox> siblings = box.parent.childrens;
        int index = siblings.indexOf(box)-1;
        Boolean result = true;
        Boolean foundIf = false;
        LogicBox temp = siblings.get(index);
        while(index >= 0 && temp.logicType.getValue().equals("Condition")){
            System.out.println("Inside Parent check Loop"+temp.toString());
            if(temp.conditions.getValue().equals("IF")){
                foundIf = true;
                if(evalCondition(temp,file)){
                    result = false;
                    break;
                }
            }
            if(temp.conditions.getValue().equals("ELSE IF")){
                if(evalCondition(temp,file)){
                    result = false;
                    break;
                }
            }
            index--;
            if(index > 0)
                temp = siblings.get(index);
        }
        if(!foundIf){
            try {
                throw new Exception("IF not Found in parent");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Found IF: " + foundIf + " result: "+ result);
        return result && foundIf;
    }

    private void createElementFromTags() {
        for(tagVM t: tagsController.tags){
            if(elements!=null && vtdParser!=null)
            elements.put(t.tag.getName(),vtdParser.createElement(t.tag.getXpath()));
        }
    }

    //Logic Parsing
    private void processChildren(LogicBox box,File file){
        box.childrens.forEach((box1) -> runLogic(box1,file));
    }
}