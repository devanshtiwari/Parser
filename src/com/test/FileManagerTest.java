package com.test;
import com.fastsearch.FastSearch;
import com.filemanager.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class FileManagerTest {
    public static void main(String[] args) {
        FastSearch F=new FastSearch();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        String ex[]={"xml"};
        F.init(path);
        ArrayList<File> All= F.ExSearch(ex);
        for(File temp:All)
        {
            System.out.println(temp.getName());
        }
    }
}
