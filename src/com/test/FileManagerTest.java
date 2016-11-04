package com.test;
import com.filemanager.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class FileManagerTest {
    public static void main(String[] args) {
        FileManager F=new FileManager();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        String ex[]={"xml"};
        ArrayList<File> All=F.init(path,ex);
        for(File temp:All)
        {
            System.out.println(temp.getName());
        }
    }
}
