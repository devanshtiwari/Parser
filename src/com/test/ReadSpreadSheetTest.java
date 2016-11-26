package com.test;
import com.filemanager.*;

/**
 * Test for Reading CSV/Excel File
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class ReadSpreadSheetTest {
    public static void main(String[] args) {
        ReaderFactory readerFactory = new ReaderFactory();
        //TODO Change Directory of xls or csv
        ReadSpreadSheet reader = readerFactory.getReader("D:\\WorkUpon.xls");
        reader.read();
        reader.consoleOut();
    }
}
