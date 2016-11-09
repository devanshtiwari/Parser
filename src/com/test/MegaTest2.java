package com.test;

import com.FastSearch.FastSearch;
import com.filemanager.*;
import com.parser.*;
import com.report.*;
import com.xpathgenerator.*;

import java.io.File;

public class MegaTest2 {
    public static void main(String[] args) throws Exception {
        FastSearch fastSearch = new FastSearch("D:\\rms\\APP\\Clusters");
        Report opReport = new Report();
        opReport.addColumn(new String[] {Report.SNO,Report.FILE_NAME,"Added"});
        //Tag for c:set element
        Tag tag1 = new Tag("set");
        //CSV Reader
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.getReader("D:\\WorkUpon.csv");
        reader.read();
        reader.consoleOut();
        Parser parser = ParserFactory.getParser(ParserFactory.Parsers.VTD);
        VTDParser vtdParser = (VTDParser) parser;
        ssIterator iter = reader.getIterator();
        while(iter.hasNext())
        {
            iter.next();
            File currentFile = fastSearch.Fsearch(iter.getValue(2)).get(0);
            System.out.println(currentFile.getCanonicalPath());
            vtdParser.parse(currentFile);
            Element e = vtdParser.createElement(tag1.getXpath());
            Tag temp = new Tag("appsTable");
            temp.addAttribute("id",iter.getValue(3));
            Element e1 = vtdParser.createElement(temp.getXpath());
            e.next();
            String var = e.getAttrVal("var");
            while(e1.next() != -1)
            {
                String ins = "exportFilename=\"#{" + var + "." + iter.getValue(5) + "}.xls\"";
                String attrs[] = {currentFile.getCanonicalPath() , ins};
                if(!e1.hasAttr("exportFilename"))
                    e1.insertAtEnd(ins,currentFile);
                opReport.initRow(opReport.getKey(attrs), currentFile);
                opReport.addValue(opReport.getKey(attrs),"Added",ins);
            }
        }
        opReport.consoleReport();
    }
}