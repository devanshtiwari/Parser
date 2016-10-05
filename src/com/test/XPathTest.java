package com.test;

import com.xpathgenerator.*;

import java.util.HashMap;

/**
 * Created by devanshtiwari on 04-Oct-16.
 */
public class XPathTest {
    public static void main(String[] args) {
        HashMap<String, String> H=new HashMap<>();
        HashMap<String, String> H1=new HashMap<>();
        H.put("id","t9");
        H.put("features","off");
        H1.put("check","no");
        H1.put("check1","hello");
        Element E=new Element("retail:appsTable","appsTable","root",H,H1);
        System.out.println(E.getXpath());
    }
}
