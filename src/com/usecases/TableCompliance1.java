package com.usecases;
import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import com.filemanager.ssIterator;
import com.parser.Element;
import com.parser.ParserFactory;
import com.parser.VTDParser;
import com.report.Report;
import com.xpathgenerator.Tag;

import java.io.File;
import java.io.IOException;

public class TableCompliance1 {

    public static void main(String[] args) throws IOException {
        ReaderFactory readerFactory = new ReaderFactory();
        readerFactory.index("D:\\rms\\APP\\Clusters");
        ReadSpreadSheet reader = readerFactory.getReader("C:\\Users\\devanshtiwari\\IdeaProjects\\TableComplaince\\Column.csv");
        reader.setFileNameColumn(2);
        reader.read();
        reader.consoleOut();
        Tag tag = new Tag("column");
        VTDParser parser = (VTDParser) ParserFactory.getParser(ParserFactory.Parsers.VTD);
        ssIterator iter = reader.getIterator();
        Report report = new Report();
        report.addColumn(new String[] {Report.SNO,Report.FILE_NAME,"sortProperty","filterable","sortable"});
        while(iter.hasNext())
        {
            iter.next();
            File current = new File(iter.getFilePath());
            parser.parse(current);
            Element e = parser.createElement(tag.getXpath());
            while(e.next() != -1)
            {
                if(e.hasAttr("id") && e.getAttrVal("id").equals(iter.getValue(4))) {
                    String key = current.getCanonicalPath() + iter.getValue(4);
                    report.initRow(key,current);
                    if (e.hasAttr("sortProperty")) {
                        e.removeAttribute("sortProperty",current);
                        report.addValue(key, "sortProperty", "Deleted");
                    }
                    if (e.hasAttr("filterable")) {
                        e.removeAttribute("filterable",current);
                        report.addValue(key, "filterable", "Deleted");
                    }
                    if (e.hasAttr("sortable")) {
                        e.removeAttribute("sortable",current);
                        report.addValue(key,"filterable","Deleted");
                    }
                }
            }
        }
        report.consoleReport();
        report.saveCSV("D:/","test");
    }
}
