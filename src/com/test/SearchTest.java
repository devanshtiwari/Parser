package com.test;
import com.filemanager.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by devanshtiwari on 07-Oct-16.
 */
public class SearchTest {
    public static void main(String[] args) {
        Search F=new Search();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter file Path");
        String path=in.nextLine();
        System.out.println(new File(path).getName());
        F.init(path);
        System.out.println("Enter File Name to Search: ");
        String search=in.nextLine();
        ArrayList<String> S =F.Fsearch(search);
        for(String temp:S)
        {
            System.out.println(temp);
        }

    }
}
