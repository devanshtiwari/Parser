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
        ReadSpreadSheet reader = readerFactory.getReader("C:\\Users\\avinaana\\Documents\\RMS.csv","C:\\test\\APP\\Clusters");
        reader.setFileNameColumn(2);
        reader.read();
        reader.consoleOut();

        //Parser
        VTDParser parser = (VTDParser) ParserFactory.getParser(ParserFactory.Parsers.VTD);
        ssIterator iter = reader.getIterator();
        //Report
        Report report = new Report();
        report.addColumn(new String[]{Report.SNO,Report.FILE_NAME,"rowHeader"});
        while(iter.hasNext()){
            iter.next();
            //Column Tag
            Tag col = new Tag("column");
            col.addAttribute("id",iter.getValue(4));
            File currentFile = new File(iter.getFilePath());
            parser.parse(currentFile);
            Element e = parser.createElement(col.getXpath());
            report.initRow(iter.getValue(0),currentFile);
            while(e.next() != -1){
                if(e.hasAttr("rowHeader")){
                    e.updateAttr("rowHeader","true",currentFile);
                    report.addValue(iter.getValue(0),"rowHeader","set to TRUE");
                }
                else{
                    report.addValue(iter.getValue(0),"rowHeader","Not found");
                }
            }
        }
        report.consoleReport();
    }
}
