package com.FastSearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * <h1>FastSearch!</h1>
 * FastSearch class helps in fast repetitive search, which will help reduce time in searching directories again and again.
 *
 * @author  Devansh Tiwari
 * @version 1.0
 * @since   2016-08-11
 */


public class FastSearch extends Thread {

    /**
     * Variable ArrayList of type {@link FileDetail} which will be used to index the Files and Folders and thereafter searching in them.
     */
    private ArrayList<FileDetail> F=new ArrayList<>();

    /**
     * This method will take input the path of directory to be indexed and then index the whole directory.
     * @param filePath Path taken by the function init. This will the the directory which will be indexed wholly.
     * @return Nothing to be returned
     */
    public void init(String filePath, String exten)
    {
        String[] str= new String[]{exten};
        init(filePath,str);
    }

    public void init(String filePath)
    {
        String[] str= new String[]{};
        init(filePath,str);
    }
    public void  init(String filePath,String[] exten){
        indexit(filePath,exten);
    }

    /**
     * Inner method which is called by init method.
     * @param filePath This is the same filepath assigned from init method.
     * @exception IOException This exception might occur in case of File or Path doesnt exist.
     */
    private void indexit(String filePath,String[] exten) {
        Path dir = FileSystems.getDefault().getPath( filePath );
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream( dir );
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nUnable to read the Directory. Does not Exist");
        }
        for (Path path : stream) {
            FileDetail fil=new FileDetail();
            if(new File(String.valueOf(path)).isDirectory()) {
                fil.setDir(true);
                indexit(String.valueOf(path),exten);
                if(exten.length!=0)
                    continue;
            }
            else
                fil.setDir(false);
            int check=0;
            if(exten.length==0)
                check=1;
            for(String s: exten)
            {
                if(path.getFileName().toString().endsWith("."+s))
                    check++;
            }
            if(check==1) {
                fil.setF(new File(path.toString()));
                fil.setName(path.getFileName().toString());
                if (F.contains(fil)) {
                    FileDetail t = F.get(F.indexOf(fil));
                    while (t.getNext() != null) {
                        t = t.getNext();
                    }
                    t.setNext(fil);
                    F.add(fil);
                } else

                    F.add(fil);
            }
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
    public ArrayList<String> Fsearch(String filename) {
        return Fsearch(filename,false);
    }

    /**
     *
     * @param filename Takes input the name of the File to be searched
     * @param dir dir takes true if it is directory name to be searched or false when it is file to be searched.
     * @return ArrayList of String which is specific directory of search
     */
    public ArrayList<String> Fsearch(String filename, Boolean dir) {
        FileDetail temp = new FileDetail();
        temp.setName(filename);
        temp.setDir(dir);
        ArrayList<String> dirs=new ArrayList<>();
        if (F.contains(temp)) {
            int s=F.indexOf(temp);
            FileDetail t;
            t=F.get(s);
            while(t!=null){
                dirs.add(t.getF().getAbsolutePath());
                t=t.getNext();
            }
        }

        return dirs;
    }
}