package com.test;
import com.filemanager.*;

public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory();
        ReadSpreadSheet reader = readerFactory.getReader("C:\\Users\\devan\\Desktop\\csss_2013_accepted_csss-c.xls");
        reader.read();
        reader.consoleOut();
    }
}
