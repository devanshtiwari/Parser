package com.xpathgenerator;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by avinaana on 10/4/2016.
 */

public class Element {
    private String name = "", parent = "", grandParent = "", xpath ="";
    private HashMap<String,String> attrs = new HashMap<>();
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
    public Element(String name, String parent, String grandParent, HashMap<String,String> attrs){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attrs = attrs;
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

    public void setAttrs(HashMap<String,String> attrs) {
        this.attrs = attrs;
    }

    public void addAttribute(String key){
        addAttribute(key,"");
    }
    public void addAttribute(String key, String value){
        this.attrs.put(key, value);
    }

    public String getXpath(){
        xpath += "//";
        xpath += this.grandParent;
        addSlash();
        xpath += this.parent;
        addSlash();
        xpath += this.name;
        addSlash();

        for(String key: attrs.keySet()){
            
        }
       return xpath;
    }
    private void addSlash(){
        String slash = "/";
        String lastChar = xpath.substring(xpath.length()-1);
        if(!Objects.equals(lastChar, "/"))
            xpath += "/";
    }
}
