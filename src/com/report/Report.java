package com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by avinaana on 10/7/2016.
 */
public class Report {
    private LinkedHashMap<String, List<String>> report = new LinkedHashMap();
    private LinkedHashMap<String, Integer> column = new LinkedHashMap<>();
    private int columnIndex = 0;
    private int sNoIndex = -1, fileNameIndex = -1, filePathIndex = -1;

    public void addColumn(String columnName){
        column.put(columnName, columnIndex++);
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
    private String getFromFilePath(String path, int index){
        return path.split("\\\\")[index];
    }
    

}
