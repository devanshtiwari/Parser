package com.parser;

import com.ximpleware.*;

import java.io.*;

import static com.parser.VTDParser.*;

public class Element {
    AutoPilot ap;
    Element(AutoPilot ap){
        this.ap = ap;
    }
    public int goToNext(){
        try {
            return this.ap.evalXPath();
        } catch (XPathEvalException | NavException e1) {
            e1.printStackTrace();
        }
        return 0;
    }
    public Boolean hasAttr(String attr){
        try {
            if(vn.hasAttr(attr))
                return true;
        } catch (NavException e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getAttrVal(String attr){
        if(hasAttr(attr))
            try {
                return vn.toString(vn.getAttrVal(attr));
            } catch (NavException e) {
                e.printStackTrace();
            }

        return null;
    }
    public void updateAttr(String attr,String updatedVal,File file){
        try {
            if(vn.hasAttr(attr)){
                xm.updateToken(vn.getAttrVal(attr),updatedVal);
                writeChanges(file);
            }
            else{
                throw new XMLParsingException("attr does not exit!");
            }
        } catch (NavException | XMLParsingException | ModifyException | IOException e) {
            e.printStackTrace();
        }
    }
}
