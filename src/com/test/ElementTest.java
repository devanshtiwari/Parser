package com.test;

import com.filemanager.FileManager;
import com.parser.Element;
import com.parser.ParserFactory;
import com.parser.ParserInterface;
import com.parser.VTDParser;
import com.report.Report;
import com.report.ReportException;
import com.xpathgenerator.Tag;
import java.io.File;
import java.util.ArrayList;

public class ElementTest {
    public static void main(String[] args) throws ReportException {
        FileManager fil = new FileManager();
        Report R = new Report();
        Tag tag = new Tag();
        R.addColumn(new String[]{Report.SNO, Report.FILE_NAME,"Root Name","ID"});
        tag.setName("TransientExpression");
        ArrayList<File> files = fil.init("C:\\test\\APP\\Clusters\\RmsFoundationHierarchy","xml");
        try {
            ParserInterface parser= ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            for(File f: files)
            {
                vtdParser.parse(f);

                Element e = vtdParser.createElement(tag.getXpath());
                if(vtdParser.checkRootFor("ViewObject")) {
                    System.out.println(f.getName());
                    R.initRow(f);
                    R.addValue(f, "Root Name", vtdParser.getRootElementName());
                    while (e.goToNext() != -1) {
                        System.out.println("--" + e.getAttrVal("trustMode"));
                        e.updateAttr("trustMode","updated",f);
                        R.addValue(f, "ID", e.getAttrVal("trustMode"));
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
