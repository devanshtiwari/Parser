package com.test;

import com.fastsearch.FastSearch;
import com.fastsearch.FileDetail;
import com.filemanager.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by avinaana on 11/4/2016.
 */
public class FileTest2 {
    public static void main(String[] args) {
        FastSearch F = new FastSearch();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        String ex[]={"xml"};
        F.init(path,ex);
        ArrayList<FileDetail> All = F.getFileList();
        for(FileDetail temp:All)
        {
            System.out.println(temp.getF().getName());
        }
    }
}
