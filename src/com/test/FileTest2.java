package com.test;


import com.FastSearch.FastSearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTest2 {
    public static void main(String[] args) throws IOException {
        FastSearch F = new FastSearch();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        while (true) {
            System.out.println("Enter Extension Name: ");
            String exten = in.nextLine();
            ArrayList<File> All = F.ExSearch(exten);
            int count = 1;
            for (File temp : All) {
                System.out.println((count++)+"--"+temp.getCanonicalPath());
            }
            System.out.println(All.size());

        }
    }
}
