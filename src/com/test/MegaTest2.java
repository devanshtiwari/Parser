package com.test;

import com.FastSearch.FastSearch;
import com.filemanager.*;
import com.parser.*;
import com.report.*;
import com.xpathgenerator.*;

import java.io.File;

/**
 * This test includes creation of report, and addition of exportFileName attribute using the variables from root of the xml file.
 * This includes all the important actions that can be performed using the library.
 * @author Avinash and Devansh
 * @since 2016-11-14
 */
public class MegaTest2 {
    public static void main(String[] args) throws Exception {
        //Initialization of FastSearch
        //TODO Change Directory Accordingly
        FastSearch fastSearch = new FastSearch("D:\\rms\\APP\\Clusters");
        //Report Initialization
        Report opReport = new Report();
        opReport.addColumn(new String[] {Report.SNO,Report.FILE_NAME,"Added"});
        //Tag for c:set element
        Tag tag1 = new Tag("set");
        //CSV Reader
        //Getting a new Spreadsheetreader using ReaderFactory
        ReaderFactory readerFactory = new ReaderFactory();
        //TODO Change Directory
        ReadSpreadSheet reader = readerFactory.getReader("D:\\WorkUpon.csv");
        //Reading the csv file given
        reader.read();
        //Printing on the console for debugging purpose
        reader.consoleOut();
        //Getting VTD Parser from ParserFactory
        Parser parser = ParserFactory.getParser(ParserFactory.Parsers.VTD);
        //Referencing it into VTDParser
        VTDParser vtdParser = (VTDParser) parser;
        //Getting iterator for whole csv file
        ssIterator iter = reader.getIterator();
        while(iter.hasNext())
        {
            iter.next();
            //We know csv file have file name at index of 2 in each row. Fetching current file for the row.
            File currentFile = fastSearch.Fsearch(iter.getValue(2)).get(0);
            System.out.println(currentFile.getCanonicalPath());
            //Parsing the file.
            vtdParser.parse(currentFile);
            //Creating element from vtdparser using Xpath
            Element e = vtdParser.createElement(tag1.getXpath());
            //Tag for appsTable
            Tag temp = new Tag("appsTable");
            //Setting id in the tag so as to get the required node.
            temp.addAttribute("id",iter.getValue(3));
            //getting element with nodes appsTable and id.
            Element e1 = vtdParser.createElement(temp.getXpath());
            //Fetching set value from the root using e.next and saving var.
            e.next();
            String var = e.getAttrVal("var");
            //Looping through all the nodes in the file.
            while(e1.next() != -1)
            {

                String ins = "exportFilename=\"#{" + var + "." + iter.getValue(5) + "}.xls\"";
                String attrs[] = {currentFile.getCanonicalPath() , ins};
                //Inserting the attribute.
                if(!e1.hasAttr("exportFilename"))
                    e1.insertAtEnd(ins,currentFile);
                opReport.initRow(opReport.getKey(attrs), currentFile);
                opReport.addValue(opReport.getKey(attrs),"Added",ins);
            }
        }
        opReport.consoleReport();
    }
}