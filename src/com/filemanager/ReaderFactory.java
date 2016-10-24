package com.filemanager;

import com.parser.ParserInterface;

import java.io.File;

public class ReaderFactory {
    public ReadSpreadSheet getReader(String sspath,String workingDir) {
        if (getFileExtension(new File(sspath)).equals("csv")) {
            return new CSVReader(sspath, workingDir);
        }
        else if(getFileExtension(new File(sspath)).equals("xls"))
        {
            return new ExcelReader(sspath,workingDir);
        }
        return null;
    }
    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            String[] tokens = name.split("\\.(?=[^\\.]+$)");
            System.out.println("Extension is: " + tokens[1]);
            return tokens[1];
        } catch (Exception e) {
            return "";
        }
    }
}
