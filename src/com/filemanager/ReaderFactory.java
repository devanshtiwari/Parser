package com.filemanager;

import java.io.File;

public class ReaderFactory {

    public ReaderFactory() {
    }


    public ReadSpreadSheet getReader(String sspath) {
        if (getFileExtension(new File(sspath)).equals("csv")) {
            return new CSVReader(sspath);
        }
        else if(getFileExtension(new File(sspath)).equals("xls"))
        {
            return new ExcelReader(sspath);
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
