package com.filemanager;

import com.report.Report;

import java.io.*;
import java.util.ArrayList;

public class CSVReader extends ReadSpreadSheet {

    private BufferedReader br;
    private String line;
    private final String COMMA_DELIMITER = ",";

    public CSVReader(String sspath) {
        super(sspath);
        try {
            this.br = new BufferedReader(new FileReader(sspath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.line = "";
        this.setHeaders();
    }

    protected String[] setHeaders() {
        try {
            if ((line = br.readLine()) != null) {
                this.headers = line.split(COMMA_DELIMITER,-1);
                this.internal.addColumn(headers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void read(){
        readCSV();
    }

    private  void readCSV() {
        try {
            br = new BufferedReader(new FileReader(ssFile));
            br.readLine();
            String[] row;
            int rowKey = 1;
            while((line = br.readLine()) != null)
            {
                row = line.split(COMMA_DELIMITER,-1);
                internal.initEmptyRow(String.valueOf(rowKey));
                int i=0;
                for(String r : row)
                {
                    internal.addValue(String.valueOf(rowKey),headers[i], r);
                    i++;
                }
                rowKey++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void consoleOut()
    {
        internal.consoleReport();
        System.out.println(internal.getColumnsName());
    }

}
