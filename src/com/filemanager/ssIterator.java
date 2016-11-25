package com.filemanager;

/**
 * Interface ssIterator to be implemented in others.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public interface ssIterator {
    public boolean hasNext();
    public void next();
    public String getValue(String columnName);
    public String getValue(int columnIndex);
}
