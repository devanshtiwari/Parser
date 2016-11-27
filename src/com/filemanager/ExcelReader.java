package com.filemanager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <h1>ExcelReader</h1>
 * <p>
 *     ExcelReader extends ReadSpreadsheet in a similar fasion as CSV Reader.
 *     It reads Excel using Apache POI Reader and saves it in internal Report which extends from ReadSpreadsheet.
 * </p>
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class ExcelReader extends ReadSpreadSheet {

    HSSFWorkbook workbook;
    Sheet sheet;
    ArrayList<String> hdrs = new ArrayList<>();

    /**
     * Constructor takes file directory of xls file as path. xlsx files are not supported, only xls are supported by the structure.
     * First line of the excel file will be taken as header by default
     * @param sspath Path of the xls file
     */
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

    /**
     * Sets headers in the internal report object
     */

    protected void setHeaders() {
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
    }

    /**
     * Read function here class readExcel().
     */
    public void read() {
        readExcel();
    }

    /**
     * ReadExcel reads the xls file using Apache POI reader. The usage of Apache POI reader can be referenced from Apache POI documentation.
     *
     */
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

    /**
     * Console out prints the whole report.
     */

    public void consoleOut()
    {
        internal.consoleReport();
        System.out.println(internal.getColumnsName());
    }

}
