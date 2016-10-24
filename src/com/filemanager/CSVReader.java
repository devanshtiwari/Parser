package com.filemanager;

import com.report.ReportException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVReader extends ReadSpreadSheet {

    private BufferedReader br ;
    private String line;
    private final String COMMA_DELIMITER = ",";

    public CSVReader(String sspath, String workingDir) {
        super(sspath,workingDir);
        try {
            this.br = new BufferedReader(new FileReader(sspath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.line = "";
        this.getHeaders();
    }

    protected String[] getHeaders() {
        try {
            if ((line = br.readLine()) != null) {
                this.headers = line.split(COMMA_DELIMITER);
                this.internal.addColumn(headers);
                return this.headers;
            }
        } catch (ReportException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void read(){
        if(fileNameColumn != -1) {
            readCSV();
        }
    }

    protected void readCSV() {
        try {
            String[] row;
            while((line = br.readLine()) != null)
            {
                row = line.split(COMMA_DELIMITER);
                ArrayList<String> path = fastReference.Fsearch(row[fileNameColumn-1]);
                if(!path.isEmpty())
                {
                    if(path.size() == 1)
                    {
                        File file = new File(path.get(0));
                        internal.initRow(file);
                        int i=0;
                        for(String r : row)
                        {
                            internal.addValue(file,headers[i],r);
                            i++;
                        }
                    }
                    else
                    {
                        try {
                            throw new Exception("Multiple File with same name"+path.get(1));
                        } catch (Exception e) {
                            System.out.println("More than one File");
                            System.out.println(path);
                        }
                    }
                }
                else
                {
                    try {
                        throw new Exception("File Not Found");
                    } catch (Exception e) {
                        System.out.println("File not Found: "+row[fileNameColumn-1]);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void out()
    {
        String row;
        LinkedHashMap<String, List<String>> report = internal.getReportsMap();
        for(String key : report.keySet()){
            row = "";
            row += "[ ";
            for(String value : report.get(key)){
                row+= value+ ", ";
            }
            row = row.substring(0, row.length()-2);
            row += " ]";
            System.out.println(row + " " + key);
        }
        System.out.println(internal.getColumnsName());
    }

}
