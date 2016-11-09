package com.usecases;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by devanshtiwari on 09-Nov-16.
 */
public class TestforIF {

    public static void main(String[] args) {
        int i = 0;
        for(i=0 ; i<3;i++){

            if(i==3) {
                continue;
            }
            else break;
        }
        if(i==3)
        System.out.println("Hello");

    }
}
