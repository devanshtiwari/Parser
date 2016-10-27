package com.test;
import com.parser.*;
import com.filemanager.*;
import com.report.*;
import java.io.File;
import java.util.ArrayList;

public class ParserTest {

    public static void main(String[] args) throws ReportException {
        FileManager fil = new FileManager();
        Report R = new Report();
        R.addColumn(new String[]{R.SNO,R.FILE_NAME});
        R.addColumn("Root xPathGen");
        R.addColumn(R.FILE_PATH);
        ArrayList<File> files = fil.init("C:\\test\\APP\\Clusters\\RmsFoundationHierarchy","xml");
        try {
            Parser parser=ParserFactory.getParser(ParserFactory.Parsers.VTD);
            for(File f: files)
            {
                System.out.println(f.getName());
                parser.parse(f);
                R.initRow(R.getKey(f.getCanonicalPath()), f);
                R.addValue(R.getKey(f.getCanonicalPath()),"Root xPathGen",parser.getRootElementName());
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
