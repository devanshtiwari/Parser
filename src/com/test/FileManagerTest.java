package com.test;
import com.filemanager.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by devanshtiwari on 07-Oct-16.
 */
public class FileManagerTest {
    public static void main(String[] args) {
        FileManager F=new FileManager();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        String ex[]={};
        ArrayList<File> All=F.init(path,ex);
        for(File temp:All)
        {
            System.out.println(temp.getAbsolutePath());
            System.out.println(F.getValueFromFilePath(temp.getAbsolutePath(),2));
            String[] farray=F.getArrayFromFilePath(temp.getAbsolutePath());
            for(String str:farray)
            {
                System.out.print(str+" ");
            }
        }
    }
}
