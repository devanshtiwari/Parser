package com.filemanager;

import com.FastSearch.*;
import com.report.Report;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;

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
    }

    private LinkedHashMap<String, Integer> getColumns()
    {
        return this.internal.getColumnsMap();
    }

    public LinkedHashMap<String, List<String>> getReport()
    {
        return internal.getReportsMap();
    }


    protected void readCSV(){}
    public void out(){}
    public void read() {}
}
