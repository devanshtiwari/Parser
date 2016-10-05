package com.xpathgenerator;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by avinaana on 10/4/2016.
 */

public class Element {
    private String name = "", parent = "", grandParent = "", xpath ="";
    private HashMap<String,String> attrs1 = new HashMap<>();
    private HashMap<String, String> attrs2 = new HashMap<>();
    public Element() {
    }
    public Element(String name){
        this.name = name;
    }
    public Element(String name, String parent){
        this.name = name;
        this.parent = parent;
    }
    public Element(String name, String parent, String grandParent){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
    }
    public Element(String name, String parent, String grandParent, HashMap<String,String> attrs1){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attrs1 = attrs1;
    }
    public Element(String name, String parent, String grandParent, HashMap<String,String> attrs1, HashMap<String, String> attrs2){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attrs1 = attrs1;
        this.attrs2=attrs2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setGrandParent(String grandParent) {
        this.grandParent = grandParent;
    }

    public void setAttrs1(HashMap<String,String> attrs1) {
        this.attrs1 = attrs1;
    }

    public void setAttrs2(HashMap<String,String> attrs2) { this.attrs2 = attrs2; }

    public void addAttribute(String key,int flag){
        addAttribute(key,"",flag);
    }

    /**
     *
     * @param key
     * @param value
     * @param flag IF 0 then OR otherwise AND
     */
    public void addAttribute(String key, String value,int flag){
        if(flag==0)
            this.attrs1.put(key, value);
        else
            this.attrs2.put(key,value);
    }

    public String getXpath(){
        xpath += "//";
        xpath += this.grandParent;
        addSlash();
        xpath += this.parent;
        addSlash();
        xpath += this.name;
        System.out.println(xpath);
        if(!attrs1.isEmpty() || !attrs2.isEmpty())
            xpath +="[";
        for(String key: attrs1.keySet()){
            xpath += (key+"="+ attrs1.get(key));
            xpath += " or ";
        }
        if(xpath.endsWith("or "))
            xpath=xpath.substring(0,xpath.length()-3);
        if(attrs2.isEmpty())
            xpath+="]";
        else
            xpath+=" and ";
        System.out.println(xpath);
        for(String key:attrs2.keySet())
        {
            xpath += (key+"="+ attrs2.get(key));
            xpath += " and ";
        }
        if(xpath.endsWith("and "))
            xpath=xpath.substring(0,xpath.length()-4);
        if(!attrs2.isEmpty())
            xpath+="]";
        addSlash();
        if(xpath.substring(xpath.length()-1).equals("/"))
            xpath=xpath.substring(0,xpath.length()-1);
       return xpath;
    }
    private void addSlash(){
        String slash = "/";
        String lastChar = xpath.substring(xpath.length()-1);
        if(!Objects.equals(lastChar, "/"))
            xpath += "/";
    }
}
