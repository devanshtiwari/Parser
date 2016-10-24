package com.filemanager;

import com.FastSearch.*;
import com.report.Report;
import java.io.*;
import java.util.LinkedHashMap;

public class ReadSpreadSheet {

    protected File ssFile;
    protected Report internal;
    protected int fileNameColumn;
    protected String[] headers;
    protected File workingDir;
    protected FastSearch fastReference;

    public ReadSpreadSheet(String sspath,String workingDir)
    {
        this.ssFile = new File(sspath);
        this.internal = new Report();
        this.fileNameColumn = -1;
        this.workingDir = new File(workingDir);
        fastReference = new FastSearch();
        fastReference.init(workingDir);
    }

    public void setFileNameColumn(int fileNameColumn) {
        this.fileNameColumn = fileNameColumn;
        System.out.println("File Column Set: "+ fileNameColumn);
    }

    private LinkedHashMap<String, Integer> getColumns()
    {
        return this.internal.getColumnsMap();
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

    protected void readCSV(){

    }

    public void read()
    {
        if(fileNameColumn != -1) {
            if (getFileExtension(ssFile).equals("csv"))
                readCSV();
        }
        else
            try {
                throw new Exception("setFileExtension not Called");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
