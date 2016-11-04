package com.fastsearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>FastSearch!</h1>
 * FastSearch class helps in fast repetitive search, which will help reduce time in searching directories again and again.
 *
 * @author  Devansh Tiwari
 * @version 1.0
 * @since   2016-08-11
 */

public class FastSearch {

    /**
     * Variable ArrayList of type {@link FileDetail} which will be used to index the Files and Folders and thereafter searching in them.
     */
    private ArrayList<FileDetail> F = null;
    private List<String> extensions = new ArrayList<>();

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }

    /**
     * This method will take input the path of directory to be indexed and then index the whole directory.
     * @param filePath Path taken by the function init. This will the the directory which will be indexed wholly.
     * @return Nothing to be returned
     */

    public void init(String filePath)
    {
        F = new ArrayList<>();
        indexit(filePath);
    }

    /**
     * Inner method which is called by init method.
     * @param filePath This is the same filepath assigned from init method.
     * @exception IOException This exception might occur in case of File or Path doesnt exist.
     */
    private void indexit(String filePath) {
        Path dir = FileSystems.getDefault().getPath(filePath);
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream( dir );
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nUnable to read the Directory. Does not Exist OR Permission Denied");
        }
        for (Path path : stream) {
            FileDetail fil=new FileDetail();
            if(new File(String.valueOf(path)).isDirectory()) {
                fil.setDir(true);
                indexit(String.valueOf(path));
            }
            else
                fil.setDir(false);

            File file = new File(path.toString());
            fil.setF(file);
            fil.setName(path.getFileName().toString());
            fil.setExten(file.getName().substring(1+file.getName().lastIndexOf(".")));
            if (F.contains(fil)) {
                FileDetail t = F.get(F.indexOf(fil));
                while (t.getNext() != null) {
                    t = t.getNext();
                }
                t.setNext(fil);
            } else
                F.add(fil);
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to close File.");
        }
    }

    /**
     *
     * @param filename String Parameter which takes File Name to be searched.
     * @return ArrayList of String which is specific directory of search
     */
    public ArrayList<File> Fsearch(String filename) {
        return Fsearch(filename,false,false);
    }
    public ArrayList<File> Fsearch(String filename,Boolean extnCheck){
        return Fsearch(filename,extnCheck,false);
    }
    public ArrayList<File> Fsearch(String filename,Boolean extnCheck,Boolean dir){
        if(extnCheck){
            return Fsearch(filename,this.extensions, dir);
        }
        else {
            return Fsearch(filename,new String[]{}, dir);
        }
    }
    public ArrayList<File> Fsearch(String filename, List<String> extn, Boolean dir){
        return Fsearch(filename, (String[]) extn.toArray(),dir);
    }

    /**
     *
     * @param filename Takes input the name of the File to be searched
     * @param dir dir takes true if it is directory name to be searched or false when it is file to be searched.
     * @return ArrayList of String which is specific directory of search
     */
    public ArrayList<File> Fsearch(String filename,String[] exten, Boolean dir) {
        this.setExtensions(Arrays.asList(exten));
        FileDetail temp = new FileDetail();
        temp.setName(filename);
        temp.setDir(dir);
        ArrayList<File> dirs=new ArrayList<>();
        if (F.contains(temp)) {
            int s=F.indexOf(temp);
            FileDetail t;
            t=F.get(s);
            if(this.extensions.size() !=0 ) {
                if (this.getExtensions().contains(t.getExten())) {
                    while (t != null) {
                        dirs.add(t.getF());
                        t = t.getNext();
                    }
                }
            }
            else {
                while (t != null) {
                    dirs.add(t.getF());
                    t = t.getNext();
                }
            }
        }
        return dirs;
    }
    public ArrayList<File> ExSearch(String[] extn){
        return ExSearch(extn,false);
    }


    public ArrayList<File> ExSearch(String[] exten, Boolean ignoreDuplicate){
        this.setExtensions(Arrays.asList(exten));

        ArrayList<File> dirs = new ArrayList<>();
        FileDetail temp = new FileDetail();
        for(FileDetail f: F) {

            if(this.extensions.contains(f.getExten())) {
                if(ignoreDuplicate){
                    System.out.println("First if");
                    dirs.add(f.getF());
                }
                else {
                    System.out.println("First else");
                    FileDetail t = f;
                    while (t != null) {
                        try {
                            System.out.println("Loop:" +t.getF().getCanonicalPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dirs.add(t.getF());
                        t = t.getNext();
                    }
                }
            }
        }
        return dirs;
    }

    public ArrayList<File> ExSearch(String exten){
        return ExSearch(new String[] {exten});
    }
    public ArrayList<File> ExSearch(String exten,Boolean ignoreDuplicate){
        return ExSearch(new String[] {exten},ignoreDuplicate);
    }

    public ArrayList<FileDetail> getFileList() {
        return F;
    }

    public String getValueFromFilePath(String path, int index){
        return path.split("\\\\")[index];
    }

    public String[] getArrayFromFilePath(String path){  return path.split("\\\\");}
}