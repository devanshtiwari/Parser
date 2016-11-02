package com.filemanager;

import com.FastSearch.FastSearch;

import java.io.File;

public class ReaderFactory {
    private FastSearch Fsearch = new FastSearch();
    public ReaderFactory(String workingDir) {
        Fsearch.init(workingDir);
    }

    public ReadSpreadSheet getReader(String sspath) {
        if (getFileExtension(new File(sspath)).equals("csv")) {
            return new CSVReader(sspath, Fsearch);
        }
        else if(getFileExtension(new File(sspath)).equals("xls"))
        {
            return new ExcelReader(sspath,Fsearch);
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
