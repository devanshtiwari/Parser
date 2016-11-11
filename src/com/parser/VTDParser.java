package com.parser;

import com.ximpleware.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VTDParser implements Parser {

    private VTDGen vg;
    static VTDNav vn;
    static XMLModifier xm;

    VTDParser() {
        vg = new VTDGen();
        xm = new XMLModifier();
    }
    private AutoPilot getAutoPilot() throws XMLParsingException {
        AutoPilot ap=new AutoPilot();
        if(vn!=null)
            ap.bind(vn);
        else
            throw new XMLParsingException("vn is NULL");
        return ap;
    }

    public void parse(File file) {
        try {
            if((vg.parseFile(file.getCanonicalPath(),false))) {
                vn=vg.getNav();
                xm.bind(vn);
            }
        } catch (IOException | ModifyException e) {
            e.printStackTrace();
        }

    }
    static void writeChanges(File file){
        try {
            xm.output(new FileOutputStream(file));
            System.gc();
        } catch (IOException | ModifyException | TranscodeException e) {
            e.printStackTrace();
        }
    }

    private int getRootElement() {
        try {
            AutoPilot ap = getAutoPilot();
            ap.selectXPath("/*");
            return ap.evalXPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public String getRootElementName(){
        try {
            return vn.toString(getRootElement());
        } catch (NavException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkRootFor(String[] checkStr)
    {
        try {
            if(checkStr.length==0)
                return true;
            for(String str: checkStr) {
                if(vn.matchRawTokenString(getRootElement(), str))
                    return true;
            }
        } catch (NavException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean checkRootFor(String checkStr)
    {
        String str[]={checkStr};
        return checkRootFor(str);
    }
    public Element createElement(String xPath){
        try {
            Element newElement = new Element(getAutoPilot());
            newElement.ap.selectXPath(xPath);
            return newElement;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}