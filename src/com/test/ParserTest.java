package com.test;
import com.parser.*;
import com.filemanager.*;
import com.report.Report;

import java.io.File;
import java.util.ArrayList;

public class ParserTest {

    public static void main(String[] args) {
        Index fil = new Index();
        Report R = new Report();
        R.addSNoToReport();
        R.addFileNameToReport();
        R.addColumn("Root Element");
        R.addFilePathToReport();
        ArrayList<File> files = fil.init("D:\\rms\\APP\\Clusters\\RmsFoundationItem","xml");
        try {
            ParserInterface parser=ParserFactory.getParser(ParserFactory.Parsers.VTD);
            for(File f: files)
            {
                System.out.println(f.getName());
                parser.parse(f);
                R.initRow(f);
                R.addValue(f,"Root Element",parser.getRootElementName());
                String str[]={"Entity","ViewObject"};
                    if(parser.checkRootFor(str))
                System.out.println(" -- "+ parser.getRootElementName());
            }
            R.consoleReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
