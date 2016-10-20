package com.report;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class Report {
    private LinkedHashMap<String, List<String>> report = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> columns = new LinkedHashMap<>();
    private int columnIndex = 0, counter = 1;
    private int sNoIndex = -1, fileNameIndex = -1, filePathIndex = -1;

    //Default Columns
    public static final String SNO = "SNo.";
    public static final String FILE_NAME = "File Name";
    public static final String FILE_PATH = "File Path";

    public void addColumn(String columnName){
        if(Objects.equals(columnName, SNO) && sNoIndex == -1)
            sNoIndex = columnIndex;
        if(Objects.equals(columnName, FILE_NAME) && fileNameIndex == -1)
            fileNameIndex = columnIndex;
        if(Objects.equals(columnName, FILE_PATH) && filePathIndex == -1)
            filePathIndex = columnIndex;

        columns.put(columnName, columnIndex++);
    }
    public void addColumn(String[] columnNames) throws ReportException {
        if(columnNames.length == 0)
            throw new ReportException("Empty array of columns Names are passed!");
        for(String s: columnNames)
            addColumn(s);
    }
    public void initRow(File file) throws IOException {
        String[] initial = new String[columns.size()];
        String key = file.getCanonicalPath();
        if(sNoIndex != -1)
            initial[sNoIndex] = String.valueOf(counter++);
        if(fileNameIndex != -1)
            initial[fileNameIndex] = file.getName();
        if(filePathIndex != -1)
            initial[filePathIndex] = file.getCanonicalPath();

        report.put(key, Arrays.asList(initial));
    }
    public void addValue(File file, String columnName, String value) throws IOException {
        String key = file.getCanonicalPath();
        report.get(key).set(columns.get(columnName),value);
    }
    public void consoleReport(){
        String row;
        for(String key : report.keySet()){
            row = "";
            row += "[ ";
            for(String value : report.get(key)){
                row+= value+ ", ";
            }
            row = row.substring(0, row.length()-2);
            row += " ]";
            System.out.println(row);
        }
    }
    public LinkedHashMap<String, List<String>> getReportsMap(){
        return report;
    }
    public LinkedHashMap<String, Integer> getColumnsMap(){
        return columns;
    }
    public String getColumnsName(){
        return columns.toString();
    }
}

