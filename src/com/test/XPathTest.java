package com.test;

import com.xpathgenerator.*;

import java.util.HashMap;


public class XPathTest {
    public static void main(String[] args) {
        Tag t = new Tag("First");
        t.addAttribute("this","that");
        t.updateAttrValue("this","there");
        t.addAttribute("second","devansh");
        t.updateAttrCondition("second", Attribute.Condition.AND);
        t.addAttribute("avi");
        t.updateAttrName("avi","devatiwa");
        t.setGrandParent("Grand");
        System.out.println(t.getXpath());
    }
}
