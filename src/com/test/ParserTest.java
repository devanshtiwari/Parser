package com.test;
import com.parser.*;
import com.filemanager.*;
import com.report.Report;
import com.report.ReportException;

import java.io.File;
import java.util.ArrayList;

public class ParserTest {

    public static void main(String[] args) throws ReportException {
        Index fil = new Index();
        Report R = new Report();
        R.addColumn(new String[]{R.SNO,R.FILE_NAME});
        R.addColumn("Root Tag");
        R.addColumn(R.FILE_PATH);
        ArrayList<File> files = fil.init("C:\\test\\APP\\Clusters\\RmsFoundationHierarchy","xml");
        try {
            ParserInterface parser=ParserFactory.getParser(ParserFactory.Parsers.VTD);
            for(File f: files)
            {
                System.out.println(f.getName());
                parser.parse(f);
                R.initRow(f);
                R.addValue(f,"Root Tag",parser.getRootElementName());
                String str[]={"Entity","ViewObject"};
                    if(parser.checkRootFor(str))
                System.out.println(" -- "+ parser.getRootElementName());
            }
            R.consoleReport();
            System.out.println(R.getColumnsName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
