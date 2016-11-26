package com.usecases;

import com.FastSearch.FastSearch;
import com.filemanager.*;
import com.parser.*;
import com.report.Report;
import com.xpathgenerator.Tag;

import java.io.File;

/**
 * RowHeader Compliance is a use case which checks for compliance of rowHeaders(TRUE OR FASLE)
 * @author Avinash and Devansh
 * @since 2016-11-14
 */

public class RowHeader {
    public static void main(String[] args){
        ReaderFactory readerFactory = new ReaderFactory();
        //TODO Change Directories
        FastSearch fastSearch = new FastSearch("D:\\rms\\APP\\Clusters");
        ReadSpreadSheet reader = readerFactory.getReader("C:\\Users\\devanshtiwari\\IdeaProjects\\TableCompliance2\\RMS.csv");
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
            File currentFile = fastSearch.Fsearch(iter.getValue(2)).get(0);
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
