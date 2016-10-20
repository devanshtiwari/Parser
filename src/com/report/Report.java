package com.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
public class Report {
    private LinkedHashMap<String, List<String>> report = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> column = new LinkedHashMap<>();
    private int columnIndex = 0, counter = 0;
    private int sNoIndex = -1, fileNameIndex = -1, filePathIndex = -1;

    public void addColumn(String columnName){
        column.put(columnName, columnIndex++);
    }
    public void initRow(File file) throws IOException {
        String[] intial = new String[column.size()];
        String key = file.getCanonicalPath();
        if(sNoIndex != -1)
            intial[sNoIndex] = String.valueOf(counter++);
        if(fileNameIndex != -1)
            intial[fileNameIndex] = file.getName();
        if(filePathIndex != -1)
            intial[filePathIndex] = file.getCanonicalPath();

        report.put(key, Arrays.asList(intial));
    }
    public void addValue(File file, String columnName, String value) throws IOException {
        String key = file.getCanonicalPath();
        report.get(key).set(column.get(columnName),value);
    }
    public void addSNoToReport(){
        sNoIndex = columnIndex;
        column.put("SNo.",columnIndex++);
    }
    public void addFileNameToReport(){
        fileNameIndex = columnIndex;
        column.put("File Name", columnIndex++);
    }
    public void addFilePathToReport(){
        filePathIndex = columnIndex;
        column.put("File Path", columnIndex++);
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
}
