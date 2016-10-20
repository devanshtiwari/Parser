package com.parser;

import com.ximpleware.*;

import java.io.File;
import java.io.IOException;

public class VTDParser implements ParserInterface {

    private VTDGen vg;
    private VTDNav vn;
    private XMLModifier xm;

    VTDParser()
    {
        vg = new VTDGen();
        xm = new XMLModifier();
    }
    private AutoPilot getAutoPilot() throws Exception {
        AutoPilot ap=new AutoPilot();
        if(vn!=null)
            ap.bind(vn);
        else
            throw new Exception("vn is NULL");
        return ap;
    }

    public void parse(File file) {
        try {
            if((vg.parseFile(file.getCanonicalPath(),false)))
            {
                vn=vg.getNav();
                xm.bind(vn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ModifyException e) {
            e.printStackTrace();
        }

    }

    private int getRootElement() {
        try {
            AutoPilot ap = getAutoPilot();
            ap.selectXPath("/*");
            int i = ap.evalXPath();
            return i;
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
                if(vn.matchRawTokenString(getRootElement(), str)==true)
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
}