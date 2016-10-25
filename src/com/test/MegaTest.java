package com.test;
import com.filemanager.ReadSpreadSheet;
import com.filemanager.ReaderFactory;
import com.parser.Element;
import com.parser.ParserFactory;
import com.parser.ParserInterface;
import com.parser.VTDParser;
import com.report.Report;
import com.report.ReportException;
import com.xpathgenerator.Tag;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public class MegaTest {

    public static void main(String[] args) {
        Report opReport = new Report();
        try {
            opReport.addColumn(new String[] {Report.SNO,Report.FILE_NAME,"Added"});
        } catch (ReportException e) {
            e.printStackTrace();
        }
        Tag tag1 = new Tag();
        tag1.setName("set");
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.getReader("D:\\WorkUpon.csv","D:\\rms\\APP\\Clusters");
        reader.setFileNameColumn(2);
        reader.read();
        reader.out();
        LinkedHashMap<String, List<String>> inReport = reader.getReport();
        int fcolumn = reader.getFileNameColumn();
       try {
           ParserInterface parser = ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            for(String k : inReport.keySet())
            {
                File currentFile =new File(reader.getValue(k, Report.FILE_PATH));
                System.out.println(currentFile.getAbsolutePath());
                vtdParser.parse(currentFile);
                Element e = vtdParser.createElement(tag1.getXpath());
                Tag temp = new Tag("appsTable");
                temp.addAttribute("id",inReport.get(k).get(3),0);
                Element e1 = vtdParser.createElement(temp.getXpath());
                e.goToNext();
                String var = e.getAttrVal("var");
                while(e1.goToNext() != -1)
                {
                    String ins = "exportFilename=\"#{" + var + "." + inReport.get(k).get(5) + "}.xls\"";
                    String attrs[] = {currentFile.getCanonicalPath() , ins};
                    if(!e1.hasAttr("exportFilename"))
                        e1.insertAtEnd(ins,currentFile);
                    opReport.initRow(opReport.getKey(attrs), currentFile);
                    opReport.addValue(opReport.getKey(attrs),"Added",ins);
                }
            }
            opReport.consoleReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}