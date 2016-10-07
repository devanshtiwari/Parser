package com.filemanager;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;

/**
 * @author Devansh Tiwari
 * @
 */
public class FileDetail {


    /**
     * Details of file as File Variable (Name and whether it is folder or not can be extracted from here)
     */
    private File f;

    /**
     * Linking next directory if the name of file/folder is same
     */
    private FileDetail next=null;

    /**
     * @param name
     * Setter of name
     */
//    public void setName(String name) {
//        this.name = name;
//    }

    /**
     * @param f
     * Setter of File varibale f
     */
    public void setF(File f) {
        this.f = f;
    }

    /**
     * @param dir
     * Setter of directory variable
     */


    /**
     * @param next
     * Setter for next variable
     */
    public void setNext(FileDetail next) {
        this.next = next;
    }

    public File getF() {
        return f;
    }

    public FileDetail getNext() {
        return next;
    }


    @Override
    /**
     * Overriding equals function which will compare only the name and whether they both are directory or file.
     */
    public boolean equals(Object obj) {
        FileDetail t= (FileDetail) obj;
        if(this.f.getName().equals(t.getF().getName()))
            return true;
        else
            return false;
    }
}
