package com.test;
import com.parser.*;
import com.filemanager.*;

import java.io.File;
import java.util.ArrayList;

public class ParserTest {

    public static void main(String[] args) {
        Index fil = new Index();
        ArrayList<File> files = fil.init("D:\\rms\\APP\\Clusters\\RmsFoundationHierarchy","xml");
        try {
            ParserInterface parser=ParserFactory.getParser(ParserFactory.Parsers.VTD);
            for(File f: files)
            {
                parser.parse(f);
                if(parser.checkRootFor("Entity"))
                    System.out.println(f.getName()+" -- "+ parser.getRootElementName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
