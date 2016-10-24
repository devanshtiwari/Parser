package com.test;
import com.filemanager.*;
/**
 * Created by devanshtiwari on 24-Oct-16.
 */
public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        CSVReader reader = new CSVReader("D:\\Report.csv","D:\\rms\\APP\\Clusters");
        reader.setFileNameColumn(2);
        reader.read();
        reader.out();
    }
}
