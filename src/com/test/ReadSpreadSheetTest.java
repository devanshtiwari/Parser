package com.test;
import com.filemanager.*;

public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.getReader("D:\\WorkUpon.xls");
        reader.read();
        reader.consoleOut();
    }
}
