package com.filemanager;

import com.report.*;

import java.io.*;
import java.util.ArrayList;

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
        this.setHeaders();
    }

    protected String[] setHeaders() {
        try {
            if ((line = br.readLine()) != null) {
                this.headers = line.split(COMMA_DELIMITER,-1);
                this.internal.addColumn(headers);
                this.internal.addColumn(Report.FILE_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void read(){
        if(fileNameColumn != -1) {
            readCSV();
        }
    }

    private  void readCSV() {
        try {
            String[] row;
            while((line = br.readLine()) != null)
            {
                row = line.split(COMMA_DELIMITER,-1);
                ArrayList<String> path = fastReference.Fsearch(row[fileNameColumn]);
                if(!path.isEmpty())
                {
                    if(path.size() == 1)
                    {
                        File file = new File(path.get(0));
                        internal.initRow(internal.getKey(row), file);
                        int i=0;
                        for(String r : row)
                        {
                            internal.addValue(internal.getKey(row),headers[i], r);
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
                        System.out.println("File not Found: "+row[fileNameColumn]);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
