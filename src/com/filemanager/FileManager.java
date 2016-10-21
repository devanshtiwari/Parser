package com.filemanager;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by devanshtiwari on 07-Oct-16.
 */
public class FileManager {
    public ArrayList<File> getFileList() {
        return fileList;
    }
    private ArrayList<File> fileList = new ArrayList<>();

    /**
     * This method will take input the path of directory to be indexed and then index the whole directory.
     * @param filePath Path taken by the function init. This will the the directory which will be indexed wholly.
     * @return Nothing to be returned
     */
    public ArrayList<File> init(String filePath, String exten)
    {
        String[] str= new String[]{exten};
        return init(filePath,str);
    }

    public ArrayList<File> init(String filePath)
    {
        String[] str= new String[]{};
        return init(filePath,str);
    }
    public ArrayList<File>  init(String filePath,String[] exten){
        indexit(filePath,exten);
        return fileList;
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
            File fil=new File(path.toString());
            if(new File(String.valueOf(path)).isDirectory()) {
                indexit(String.valueOf(path),exten);
                if(exten.length!=0)
                    continue;
            }
            int check=0;
            if(exten.length==0)
                check=1;
            for(String s: exten)
            {
                if(path.getFileName().toString().endsWith("."+s))
                    check++;
            }
            if(check==1) {
                    fileList.add(fil);
            }
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to close File.");
        }
    }
    public String getValueFromFilePath(String path, int index){
        return path.split("\\\\")[index];
    }

    public String[] getArrayFromFilePath(String path){  return path.split("\\\\");}

}