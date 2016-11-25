package com.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Report class manages all the operations related to CSV, Excel and creating report in a structure that is accessible thorughout the
 * program.
 * Report is implmeent using LinkedHashMap @report of String and List<String></String>
 * @author Avinash and Devansh
 * @since 2016-11-14
 */
public class Report {
    private LinkedHashMap<String, List<String>> report = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> columns = new LinkedHashMap<>();
    private int columnIndex = 0, counter = 1,defaultKey = 1;
    private int sNoIndex = -1, fileNameIndex = -1, filePathIndex = -1;
    private boolean defaultKeyType = false;
    //Default Columns
    public static final String SNO = "SNo.";
    public static final String FILE_NAME = "File Name";
    public static final String FILE_PATH = "File Path";

    public Report(){}

    /**
     * If Parameter is true, then default key is set to true, and if false, it is set to false.
     * If default key isnt set to true, one have to manually set the key for the row. (Key is the column that will identify the uniquely
     * the row.
     * @param b
     */
    public Report(Boolean b){
        this.defaultKeyType = b;
    }

    /**
     * Explicit option to set default key.
     */
    public void setDefaultKey() {
        this.defaultKeyType = true;
    }

    /**
     * Addition of Column(usually Headers). Must to be done to do further operations on the table.
     * @param columnName
     */
    public void addColumn(String columnName){
        if(Objects.equals(columnName, SNO) && sNoIndex == -1)
            sNoIndex = columnIndex;
        if(Objects.equals(columnName, FILE_NAME) && fileNameIndex == -1)
            fileNameIndex = columnIndex;
        if(Objects.equals(columnName, FILE_PATH) && filePathIndex == -1)
            filePathIndex = columnIndex;

        columns.put(columnName, columnIndex++);
    }

    /**
     * Addition of columnNames using a String array.
     * @param columnNames
     */
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

    /**
     * Returns Key Column
     * @param keyattr
     * @return
     */
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

    /**
     * Initializes a row with empty data. This is extremely important before adding some data to the table. initRow or initEmptyRow is
     * mandatory to run before any addition of row data in table.
     * @param key
     */
    public void initEmptyRow(String key){
        String[] initial = new String[columns.size()];
        report.put(key, Arrays.asList(initial));
    }

    /**
     * Initializes row with data of the file given as parameter.
     * @param key
     * @param file
     */
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

    /**
     * Initializes row with default key incrementation.
     * @param file
     */
    public void initRow(File file){
        if(this.defaultKeyType)
           initRow(String.valueOf(defaultKey),file);

        else
            try {
                throw new ReportException("Default key option is not set to true.");
            } catch (ReportException e) {
                e.printStackTrace();
            }
    }

    /**
     * A value can be added in a specific row by providing the key and columnname and the value to be entered.
     * @param key
     * @param columnName
     * @param value
     */
    public void addValue(String key, String columnName, String value){
        report.get(key).set(columns.get(columnName),value);
    }
    public void addValue(String columnName, String value) {
        if(this.defaultKeyType)
            addValue(String.valueOf(defaultKey),columnName,value);
        else
            try {
                throw new ReportException("Default key option is not set to true.");
            } catch (ReportException e) {
                e.printStackTrace();
            }
    }

    /**
     * This is important to do before going to next line. This increments the key so as to initialize next row.
     */
    public void incrementKey(){
        if(this.defaultKeyType)
            this.defaultKey++;

        else
            try {
                throw new ReportException("Default key option is not set to true.");
            } catch (ReportException e) {
                e.printStackTrace();
            }
    }

    /**
     * Prints the report in the console.
     */
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

    /**
     * This method lets you to save the report structure in a csv file format. So when report is complete, csv can be saved using this
     * method.
     * @param dir
     * @param filename
     * @return
     */
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

    /**
     *
     * Index of the Column will be returned having parameter String ColumnName.
     * @param columnName
     * @return
     */

    public int getColumnIndex(String columnName){
        return columns.get(columnName);
    }

    /**
     * Method can be used to get value by providing the row key and columnName.
     * @param key
     * @param columnName
     * @return
     */
    public String getValue(String key, String columnName) {
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

