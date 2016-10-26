package com.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
    public void addColumn(String[] columnNames){
        if(columnNames.length == 0)
            try {
                throw new ReportException("Empty array of columns Names are passed!");
            } catch (ReportException e) {
                e.printStackTrace();
            }
        for(String s: columnNames)
            addColumn(s);
    }

    public String getKey(String keyattr)
    {
        return getKey(new String[] {keyattr});
    }
    public String getKey(String[] keyattrs)
    {
        String key = "";
        if(key.equals(""))
            for(String s : keyattrs) {
                    key += s;
            }
        return key;
    }

    public void initRow(String key, File file){
        String[] initial = new String[columns.size()];
        if(sNoIndex != -1)
            initial[sNoIndex] = String.valueOf(counter++);
        if(fileNameIndex != -1)
            initial[fileNameIndex] = file.getName();
        if(filePathIndex != -1)
            try {
                initial[filePathIndex] = file.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }

        report.put(key, Arrays.asList(initial));
    }
    public void addValue(String key, String columnName, String value){
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

    public String saveCSV(String dir, String filename){
        final String COMMA = ",";
        final String NEW_LINE = "\n";
        String csvPath = dir+"\\"+filename+".csv";
        try {
            PrintWriter pw = new PrintWriter(new File(csvPath));
            for(String col : columns.keySet()) {
                pw.append(col);
                pw.append(COMMA);
            }
            pw.append(NEW_LINE);
            for(String row: report.keySet()) {
                for(String cell : report.get(row)) {
                    pw.append(cell);
                    pw.append(COMMA);
                }
                pw.append(NEW_LINE);
            }
            pw.append(NEW_LINE);
            pw.append(new Date().toString());
            pw.flush();
            pw.close();
            return csvPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }


    public String getValue(String key,String columnName) {
        if(columns.containsKey(columnName))
            return report.get(key).get(columns.get(columnName));

        else
            try {
                throw new ReportException("No such column exit!");
            } catch (ReportException e) {
                e.printStackTrace();
            }
        return null;
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

