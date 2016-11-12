package com.filemanager;
import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by devanshtiwari on 24-Oct-16.
 */
public class ExcelReader extends ReadSpreadSheet {

    HSSFWorkbook workbook;
    Sheet sheet;
    ArrayList<String> hdrs = new ArrayList<>();

    public ExcelReader(String sspath) {
        super(sspath);
        try {
            this.workbook = new HSSFWorkbook(new FileInputStream(sspath));
            sheet = this.workbook.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setHeaders();
    }

    protected String[] setHeaders() {
        Row temp = sheet.getRow(0);
        if (temp != null) {
            Iterator<Cell> celliterator = temp.cellIterator();
            while (celliterator.hasNext()) {
                Cell cell = celliterator.next();
                hdrs.add(cell.getStringCellValue());
            }
            String[] str = new String[hdrs.size()];
            this.headers = hdrs.toArray(str);
            this.internal.addColumn(headers);
        }
        return null;
    }

    public void read() {
        readExcel();
    }

    private void readExcel() {
        Iterator<Row> rowIterator = sheet.iterator();
        if (rowIterator.hasNext())
            rowIterator.next();
        int rowKey = 1;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            internal.initEmptyRow(String.valueOf(rowKey));
            int i = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellType(CellType.STRING);
                    this.internal.addValue(String.valueOf(rowKey), headers[i], cell.getStringCellValue());
                i++;
            }
            rowKey++;
        }
    }

    public void consoleOut()
    {
        internal.consoleReport();
        System.out.println(internal.getColumnsName());
    }

}
