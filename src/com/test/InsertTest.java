package com.test;

import com.filemanager.FileManager;
import com.parser.*;
import com.report.*;
import com.xpathgenerator.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Insert Test is the test for insertion of attribute in all the files with given conditions.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */

public class InsertTest {
    public static void main(String[] args) throws ReportException {
        FileManager fil = new FileManager();
        Report R = new Report();
        Tag tag = new Tag();
        R.addColumn(new String[]{Report.SNO, Report.FILE_NAME,"Root Name","ID"});
        tag.setName("appsTable");
        ArrayList<File> files = fil.init("C:\\test\\APP\\Clusters\\RmsFoundationHierarchy","jsff");
        try {
            Parser parser= ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            for(File f: files)
            {
                vtdParser.parse(f);

                Element e = vtdParser.createElement(tag.getXpath());
                    String key = R.getKey(f.getCanonicalPath());
                    System.out.println(f.getName());
                    R.initRow(key, f);
                    R.addValue(key, "Root Name", vtdParser.getRootElementName());
                    while (e.next() != -1) {
                        e.insertAtEnd("trustMode=\"trusted\"",f);
                }
            }
            R.consoleReport();
            System.out.println(R.getColumnsName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
