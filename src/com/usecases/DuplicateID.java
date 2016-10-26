package com.usecases;

import com.filemanager.FileManager;
import com.parser.Element;
import com.parser.ParserFactory;
import com.parser.VTDParser;
import com.report.Report;
import com.xpathgenerator.Tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DuplicateID {

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        Report report = new Report();
        report.addColumn(new String[] {Report.SNO,Report.FILE_NAME,"ID","Duplicate"});
        ArrayList<File> files = fileManager.init("D:\\rms\\APP\\Clusters","xlf");
        VTDParser parser = (VTDParser) ParserFactory.getParser(ParserFactory.Parsers.VTD);
        Tag trans = new Tag("trans-unit");
        int count = 0;
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
    }
}
