package com.test;
import com.filemanager.*;

public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.getReader("D:\\Report.csv","D:\\rms\\APP\\Clusters");
        reader.setFileNameColumn(2);
        reader.read();
        reader.consoleOut();
    }
}
