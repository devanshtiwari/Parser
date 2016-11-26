package com.filemanager;
import java.io.File;

/**
 * ReaderFactory instance returns a single instance of the either CSVReader or ExcelReader, depending upon the file is csv or xls.
 * @since 2016-11-14
 * @author Devansh and Avinash
 */
public class ReaderFactory {

    public ReaderFactory() {
    }

    /**
     * Path of the file is taken as input and the its extension is checked for.
     * @param sspath
     * @return Returns Object of CSVReader or ExcelReader as per file.
     */
    public ReadSpreadSheet getReader(String sspath) {
        if (getFileExtension(new File(sspath)).equals("csv")) {
            return new CSVReader(sspath);
        }
        else if(getFileExtension(new File(sspath)).equals("xls"))
        {
            return new ExcelReader(sspath);
        }
        return null;
    }

    /**
     * It checks for extension of the file in a robust manner.
     * @param file
     * @return Extension of the file.
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            String[] tokens = name.split("\\.(?=[^\\.]+$)");
            System.out.println("Extension is: " + tokens[1]);
            return tokens[1];
        } catch (Exception e) {
            return "";
        }
    }
}
