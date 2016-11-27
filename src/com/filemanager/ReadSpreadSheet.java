package com.filemanager;

import com.report.Report;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ReadSpreadsheet is the base class for CSVReader and ExcelReader. It implements the common methods and variables in it.
 * @author Devansh and Avinahs
 * @since 2016-11-14
 */

public class ReadSpreadSheet {

    File ssFile;
    Report internal;
    String[] headers;

    /**
     * Constructor takes file path and initializes report.
     * @param sspath
     */
    ReadSpreadSheet(String sspath)
    {
        this.ssFile = new File(sspath);
        this.internal = new Report();
    }

    /**
     * Returns column index by taking header name.
     * @param name Name of Column is given as Parameter, index is returned.
     * @return index of the column
     */
    public int getColumnIndex(String name) {
        return internal.getColumnIndex(name);
    }

    /**
     * @return String Array of Headers.
     */
    public String[] getHeaders(){
        return this.headers;
    }

    /**
     *
     * @return LinkedHashMap of column name of the internal report
     */
    public LinkedHashMap<String, Integer> getColumns()
    {
        return this.internal.getColumnsMap();
    }

    /**
     * Returns the whole Report in LinkedHashMap{@code <String, List<String>>} as whole report.
     * @return LinkedHashmap of Report
     */
    public LinkedHashMap<String, List<String>> getReport()
    {
        return internal.getReportsMap();
    }

    /**
     * Function is overridden in CSVReader and ExcelReader
     */
    public void consoleOut(){}

    /**
     * Function is overridden in CSVReader and ExcelReader
     */

    public void read() {}

    /**
     *
     * @return Returns Iterator for Rows of the Report.
     */
    public RIterator getIterator(){
        return new RIterator();
    }

    /**
     * Iterator Class implementing ssIterator Interface to have Iterator for rows.
     * @since  2016-11-14
     */
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

        public String getValue(int columnIndex){
            return ssMap.get(currentKey).get(columnIndex);
        }
    }
}

