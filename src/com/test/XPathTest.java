package com.test;

import com.xpathgenerator.*;

/**
 * This test is for creation of Tag using Tag, Attribute and generating xpath.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
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
