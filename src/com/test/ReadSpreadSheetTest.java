package com.test;
import com.filemanager.*;
/**
 * Created by devanshtiwari on 24-Oct-16.
 */
public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.ReaderFactory("D:\\Report.csv","D:\\rms\\APP\\Clusters");
        reader.setFileNameColumn(2);
        reader.read();
        reader.out();
    }
}
