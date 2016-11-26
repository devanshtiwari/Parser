package com.test;

import com.FastSearch.FastSearch;
import com.parser.*;
import com.report.*;
import com.sun.xml.internal.bind.v2.TODO;
import com.xpathgenerator.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Element Test is just to check the functionality of Element Class.
 * @author Devansh and Avinash
 * @since  2016-11-14
 */
public class ElementTest {
    public static void main(String[] args) throws ReportException {
        FastSearch fastSearch = new FastSearch();
        Report R = new Report();
        Tag tag = new Tag();
        //Addition of Columns in Report.
        R.addColumn(new String[]{Report.SNO, Report.FILE_NAME,"Root Name","ID"});
        //Setting up Tag Name
        tag.setName("TransientExpression");
        //Indexing the whole Project Structure
        //TODO MAKE SURE TO CHANGE DIRECTORY
        fastSearch.init("D:\\rms\\APP\\Clusters");
        //Filtering out xml files
        ArrayList<File> files = fastSearch.ExSearch("xml");
        try {
            //Getting Parser from ParserFactory
            Parser parser= ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            //For Each file
            for(File f: files)
            {
                //Parsing the file
                vtdParser.parse(f);
                //Creation of the element
                Element e = vtdParser.createElement(tag.getXpath());
                //Checking root for the ViewObject, only those files are required to be modified.
                if(vtdParser.checkRootFor("ViewObject")) {
                    System.out.println(f.getName());
                    //This example is of not setting default key. Here Key is set by using getKey which returns the key and then operation is performed.
                    String key = R.getKey(f.getCanonicalPath());
                    //Initialization of the row.
                    R.initRow(key, f);
                    //Adding value in the report.
                    R.addValue(key, "Root Name", vtdParser.getRootElementName());
                    //Visiting each node from the file
                    while (e.next() != -1) {
                        System.out.println("--" + e.getAttrVal("trustMode"));

                        if(e.hasAttr("trustMode"))
                            e.insertAttr(" trustMode=\"trusted\" ",f);
                        R.addValue(key , "ID", e.getAttrVal("trustMode"));
                    }
                }
            }
            R.consoleReport();
            System.out.println(R.getColumnsName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
