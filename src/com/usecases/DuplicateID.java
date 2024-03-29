package com.usecases;

import com.FastSearch.*;
import com.parser.*;
import com.report.Report;
import com.xpathgenerator.Tag;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Duplicate ID searches for duplicate ID and text in the viewcontroller bundle files and removes the duplicate and generates report also.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class DuplicateID {

    public static void main(String[] args) throws IOException {
        FastSearch fileManager = new FastSearch();
        Report report = new Report();
        report.addColumn(new String[] {Report.SNO,Report.FILE_NAME,"ID","Duplicate"});
        //TODO Change Directory
        fileManager.init("D:\\rms\\APP\\Clusters");
        ArrayList<File> files = fileManager.ExSearch("xlf");
        VTDParser parser = (VTDParser) ParserFactory.getParser(ParserFactory.Parsers.VTD);
        //Creation of tag
        Tag trans = new Tag("trans-unit");
        int count = 0;
        //For each file of xlf extension
        for(File f : files)
            if (f.getName().contains("ViewControllerBundle.xlf") && f.getCanonicalPath().contains("src")) {
                parser.parse(f);
                ArrayList<String> ids = new ArrayList<String>();
                Element e = parser.createElement(trans.getXpath());
                while (e.next() != -1) {
                    String key = report.getKey(String.valueOf(count++));
                    if(e.hasAttr("id")) {
                        if(ids.contains(e.getAttrVal("id"))){
                            e.removeElement(f);
                            report.initRow(key,f);
                            report.addValue(key,"ID",e.getAttrVal("id"));
                            report.addValue(key,"Duplicate","YES");
                        }
                        else {
                            ids.add(e.getAttrVal("id"));
                        }
                    }
                }
            }
            report.consoleReport();
            report.saveCSV("D:/","Test");
    }
}