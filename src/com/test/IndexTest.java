package com.test;
import com.filemanager.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by devanshtiwari on 07-Oct-16.
 */
public class IndexTest {
    public static void main(String[] args) {
        Index F=new Index();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        String ex[]={};
        ArrayList<File> All=F.init(path,ex);
        for(File temp:All)
        {
            System.out.println(temp.getAbsolutePath());
        }
    }
}
