package com.test;

import com.filemanager.*;
import com.parser.*;
import com.report.*;
import com.xpathgenerator.*;
import java.io.File;
import java.util.ArrayList;

public class ElementTest {
    public static void main(String[] args) throws ReportException {
        FileManager fil = new FileManager();
        Report R = new Report();
        Tag tag = new Tag();
        R.addColumn(new String[]{Report.SNO, Report.FILE_NAME,"Root Name","ID"});
        tag.setName("TransientExpression");
        ArrayList<File> files = fil.init("D:\\rms\\APP\\Clusters","xml");
        try {
            Parser parser= ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            for(File f: files)
            {
                vtdParser.parse(f);

                Element e = vtdParser.createElement(tag.getXpath());
                if(vtdParser.checkRootFor("ViewObject")) {
                    System.out.println(f.getName());
                    String key = R.getKey(f.getCanonicalPath());
                    R.initRow(key, f);
                    R.addValue(key, "Root Name", vtdParser.getRootElementName());
                    while (e.next() != -1) {
                        System.out.println("--" + e.getAttrVal("trustMode"));
//                        e.updateAttr("trustMode","updated",f);
                        if(e.hasAttr("trustMode"))
                            e.insertAttr(" trustMode=\"trusted\" ",f);
                        R.addValue(key , "ID", e.getAttrVal("trustMode"));
                    }
                }
            }
            R.consoleReport();
            System.out.println(R.getColumnsName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
