package com.test;
import com.filemanager.*;

public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.getReader("D:\\XML Parser Projects\\trustmode\\work.csv");
        reader.read();
        reader.consoleOut();
    }
}
