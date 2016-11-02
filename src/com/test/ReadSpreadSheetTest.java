package com.test;
import com.filemanager.*;

public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory("D:\\rms\\APP\\Clusters");
        ReadSpreadSheet reader = readerFactory.getReader("D:\\Report.csv");
        reader.setFileNameColumn(2);
        reader.read();
        reader.consoleOut();
    }
}
