package com.FastSearch;

import java.io.File;

/**
 * <h1>FileDetail</h1>
 * <p>FileDetail Class encapsulates the File details which will ultimately help in searching of files. </p>
 * @author Devansh Tiwari
 * @version 1.0
 * @since 2016-08-11
 */

class FileDetail {

    /**
     * Name of the Folder or file.
     */
    private String name;
    /**
     * Details of file as File Variable
     */
    private File f;
    /**
     * Variable whether it is directory or not
     */
    private Boolean dir;
    /**
     * Linking next directory if the name of file/folder is same
     */
    private FileDetail next=null;

    /**
     * @param name
     * Setter of name
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * @param f
     * Setter of File varibale f
     */
    void setF(File f) {
        this.f = f;
    }

    /**
     * @param dir
     * Setter of directory variable
     */
    void setDir(Boolean dir) {
        this.dir = dir;
    }

    /**
     * @param next
     * Setter for next variable
     */
    void setNext(FileDetail next) {
        this.next = next;
    }

    String getName() {
        return name;
    }

    File getF() {
        return f;
    }

    Boolean getDir() {
        return dir;
    }

    FileDetail getNext() {
        return next;
    }


    @Override
    /**
     * Overriding equals function which will compare only the name and whether they both are directory or file.
     */
    public boolean equals(Object obj) {
        FileDetail t= (FileDetail) obj;
        if(this.name.equals(t.name) && this.dir.equals(t.dir))
            return true;
        else
            return false;
    }
}
