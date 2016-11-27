package com.filemanager;
import java.io.*;

/**
 * <h1>CSVReader</h1>
 * <p>
 *     CSV Reader class extends ReadSpreadsheet, to have properties of reading spreadsheet. It performs reading, writing over the
 *     CSV Files and manage all the operations on it.
 * </p>
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class CSVReader extends ReadSpreadSheet {
    private BufferedReader br;
    private String line;
    private final String COMMA_DELIMITER = ",";

    /**
     * Contructor to initialize the spreadsheet reader with a CSV File. The first line is always taken as headers of the CSV File.
     * @param sspath Spreadsheet File Path
     */
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

    /**
     * The First Line of the CSV is BY DEFAULT set as headers.
     */
    protected void setHeaders() {
        try {
            if ((line = br.readLine()) != null) {
                this.headers = line.split(COMMA_DELIMITER,-1);
                this.internal.addColumn(headers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read function here calls readCSV.
     */
    public void read(){
        readCSV();
    }

    /**
     * ReadCSV Reades each line of the CSV Report and puts it in an object of Report Class which implments its structure.
     * @link internal is the Report object used inherited from the ReadSpradsheet
     */
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

    /**
     * Console Out prints the whole report in the console.
     */
    public void consoleOut()
    {
        internal.consoleReport();
        System.out.println(internal.getColumnsName());
    }

}
