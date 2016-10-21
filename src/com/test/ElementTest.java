package com.test;

import com.filemanager.Index;
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
        Index fil = new Index();
        Report R = new Report();
        Tag xPathGen = new Tag();
        R.addColumn(new String[]{R.SNO,R.FILE_NAME,"Root Name","ID"});
        xPathGen.setName("TransientExpression");
        ArrayList<File> files = fil.init("C:\\test\\APP\\Clusters\\RmsFoundationHierarchy","xml");
        try {
            ParserInterface parser= ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            for(File f: files)
            {
                vtdParser.parse(f);

                VTDParser.Element e = vtdParser.createElement(xPathGen.getXpath());
                if(vtdParser.checkRootFor("ViewObject")) {
                    System.out.println(f.getName());
                    System.out.println(xPathGen.getXpath());
                    R.initRow(f);
                    R.addValue(f, "Root Name", vtdParser.getRootElementName());
                    while (vtdParser.goToNext(e) != -1) {
                        System.out.println("--" + vtdParser.getAttrVal("trustMode"));
                        R.addValue(f, "ID", vtdParser.getAttrVal("trustMode"));
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
