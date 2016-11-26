package com.test;

import com.FastSearch.FastSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Test for Obsolete FileManager. Later on functionality is replaced using FastSearch.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */

public class FileManagerTest {
    public static void main(String[] args) {

        //Object initialization
        FastSearch F=new FastSearch();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        //Extension String
        String ex[]={"xml"};
        F.init(path);
        //Extension Search of xml files
        ArrayList<File> All= F.ExSearch(ex);
        for(File temp:All)
        {
            System.out.println(temp.getName());
        }
    }
}
