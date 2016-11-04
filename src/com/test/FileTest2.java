package com.test;

import com.fastsearch.FastSearch;
import com.fastsearch.FileDetail;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTest2 {
    public static void main(String[] args) {
        FastSearch F = new FastSearch();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        while (true) {
            System.out.println("Enter Extension Name: ");
            String exten = in.nextLine();
            F.init(path);
            ArrayList<File> All = F.ExSearch(exten);
            for (File temp : All) {
                System.out.println(temp.getName());
            }

        }
    }
}
