package com.test;

import com.FastSearch.FastSearch;
import com.parser.*;
import com.report.*;
import com.xpathgenerator.*;
import java.io.File;
import java.util.ArrayList;

public class ElementTest {
    public static void main(String[] args) throws ReportException {
        FastSearch fastSearch = new FastSearch();
        Report R = new Report();
        Tag tag = new Tag();
        R.addColumn(new String[]{Report.SNO, Report.FILE_NAME,"Root Name","ID"});
        tag.setName("TransientExpression");
        fastSearch.init("D:\\rms\\APP\\Clusters");
        ArrayList<File> files = fastSearch.ExSearch("xml");
        try {
            Parser parser= ParserFactory.getParser(ParserFactory.Parsers.VTD);
            VTDParser vtdParser = (VTDParser) parser;
            for(File f: files)
            {
                vtdParser.parse(f);

                Element e = vtdParser.createElement(tag.getXpath());
                if(vtdParser.checkRootFor("ViewObject")) {
                    System.out.println(f.getName());
                    String key = R.getKey(f.getCanonicalPath());
                    R.initRow(key, f);
                    R.addValue(key, "Root Name", vtdParser.getRootElementName());
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
