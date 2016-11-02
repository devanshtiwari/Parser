package com.usecases;

import com.filemanager.*;
import com.parser.*;
import com.report.Report;
import com.xpathgenerator.Tag;

import java.io.File;
import java.io.IOException;

public class RowHeader {
    public static void main(String[] args){
        ReaderFactory readerFactory = new ReaderFactory();
        readerFactory.index("D:\\rms\\APP\\Clusters");
        ReadSpreadSheet reader = readerFactory.getReader("C:\\Users\\devanshtiwari\\IdeaProjects\\TableCompliance2\\RMS.csv");
        reader.setFileNameColumn(2);
        reader.read();
        reader.consoleOut();

        //Parser
        VTDParser parser = (VTDParser) ParserFactory.getParser(ParserFactory.Parsers.VTD);
        ssIterator iter = reader.getIterator();
        //Report
        Report report = new Report(true);
        report.addColumn(new String[]{Report.SNO,Report.FILE_NAME,"rowHeader"});
        while(iter.hasNext()){
            iter.next();
            //Column Tag
            Tag col = new Tag("column");
            col.addAttribute("id",iter.getValue(4));
            File currentFile = new File(iter.getFilePath());
            parser.parse(currentFile);
            Element e = parser.createElement(col.getXpath());
            report.initRow(currentFile);
            while(e.next() != -1){
                if(e.hasAttr("rowHeader")){
                    e.updateAttr("rowHeader","true",currentFile);
                    report.addValue("rowHeader","set to TRUE");
                }
                else{
                    report.addValue("rowHeader","Not found");
                }
                report.incrementKey();
            }

        }
        report.consoleReport();
    }
}
