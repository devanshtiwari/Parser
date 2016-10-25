package com.filemanager;

import com.FastSearch.*;
import com.report.Report;
import java.io.*;
import java.util.Iterator;
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
    public int getFileNameColumn() {
        return fileNameColumn;
    }

    public void setFileNameColumn(int fileNameColumn) {
        this.fileNameColumn = fileNameColumn;
    }
    public String[] getHeaders(){
        return this.headers;
    }

    private LinkedHashMap<String, Integer> getColumns()
    {
        return this.internal.getColumnsMap();
    }

    public LinkedHashMap<String, List<String>> getReport()
    {
        return internal.getReportsMap();
    }

    //Method definition in CSVReader class
    public String getValue(String key, String columnName){ return null;}
    public void out(){}
    public void read() {}

    public RIterator getIterator(){
        return new RIterator();
    }

    private class RIterator implements ssIterator{
        LinkedHashMap<String, List<String>> ssMap;
        Iterator<String> keySetIterator;
        String currentKey;
        RIterator(){
            ssMap = internal.getReportsMap();
            keySetIterator = ssMap.keySet().iterator();
        }
        @Override
        public boolean hasNext() {
            return keySetIterator.hasNext();
        }

        @Override
        public void next() {
            currentKey = keySetIterator.next();
        }
        @Override
        public String getValue(String columnName){
            return internal.getValue(currentKey, columnName);
        }

        @Override
        public String getFilePath() {
            return getValue(Report.FILE_PATH);
        }

        public String getValue(int columnIndex){
            return ssMap.get(currentKey).get(columnIndex);
        }
    }
}

