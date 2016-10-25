package com.filemanager;

/**
 * Created by avinaana on 10/25/2016.
 */
public interface ssIterator {
    public boolean hasNext();
    public void next();
    public String getValue(String columnName);
    public String getValue(int columnIndex);
    public String getFilePath();
}
