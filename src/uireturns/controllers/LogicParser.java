package uireturns.controllers;

import com.parser.Element;
import com.parser.Parser;
import com.parser.ParserFactory;
import com.parser.VTDParser;
import com.report.Report;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LogicParser {
    private Report opReport;
    private VTDParser vtdParser;
    private HashMap<String ,Element> elements;
    LogicParser() {
        opReport = new Report();
        opReport.addColumn(new String[]{Report.SNO,Report.FILE_NAME,Report.FILE_PATH});
        opReport.setDefaultKey();
        Parser parser = ParserFactory.getParser(ParserFactory.Parsers.VTD);
        vtdParser = (VTDParser) parser;
        elements = new HashMap<>();
    }
    public void parseXML(File file, String[] roots) {
        System.out.println("Inside ParseXML");
        System.out.println(file.getAbsolutePath());
        vtdParser.parse(file);
        createElementFromTags();
        opReport.initRow(file);
        if(vtdParser.checkRootFor(roots)){
            csvParse.logicBoxes.forEach((box) -> runLogic(box, file));
            opReport.incrementKey();
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
                report(box);
                break;
        }
    }

    private void report(LogicBox box) {
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
                e.updateAttr(box.paramList.get(0),box.paramList.get(1),file);
                break;
            case "insertAttrAtFront":
                break;
            case "insertAttrAtEnd":
                String insert = box.paramList.get(0)+"=\""+box.paramList.get(1)+"\"";
                e.insertAtEnd(insert,file);
                break;
            case "removeAttr":
                break;
            case "removeElement":
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
        System.out.println("evalParentCondition: "+evalParentConditions(box));
        if(evalParentConditions(box))
            processChildren(box,file);
    }

    private void runElseIf(LogicBox box, File file) {
        System.out.println("Inside runElseIf");
        System.out.println(evalCondition(box));
        System.out.println(evalParentConditions(box));
        if(evalCondition(box)){
            if(evalParentConditions(box))
                processChildren(box,file);
        }
    }

    private void runIf(LogicBox box, File file) {
        System.out.println("inside runIf");
        System.out.println("Result: " +evalCondition(box));
        if(evalCondition(box))
            processChildren(box,file);
    }
    private Boolean evalCondition(LogicBox box){
        Element e = elements.get(box.tags.getValue());
        String method = box.methods.getValue();
        Boolean result = false;
        switch(method){
            case "hasAttr":
                result = e.hasAttr(box.paramList.get(0));
                System.out.println("hasAttr: "+ e.hasAttr(box.paramList.get(0)));
                break;
            case "checkAttrVal":
                result = e.getAttrVal(box.paramList.get(0)).equals(box.paramList.get(1));
                break;
        }
        return result;
    }

    private Boolean evalParentConditions(LogicBox box){
        List<LogicBox> siblings = box.parent.childrens;
        int index = siblings.indexOf(box)-1;
        Boolean result = true;
        Boolean foundIf = false;
        LogicBox temp = siblings.get(index);
        while(index >= 0 && temp.logicType.getValue().equals("Condition")){
            System.out.println("Inside Parent check Loop"+temp.toString());
            if(temp.conditions.getValue().equals("IF")){
                foundIf = true;
                if(evalCondition(temp)){
                    result = false;
                    break;
                }
            }
            if(temp.conditions.getValue().equals("ELSE IF")){
                if(evalCondition(temp)){
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