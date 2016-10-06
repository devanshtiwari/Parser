package com.xpathgenerator;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Devansh and Avinash on 10/4/2016.
 */

/**
 *
 */
public class Element {
    private String name = "", parent = "", grandParent = "", xpath ="";
    private HashMap<String,String> attrsOR = new HashMap<>();
    private HashMap<String, String> attrsAND = new HashMap<>();

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
        this.attrsOR = attrs;
    }
    public Element(String name, String parent, String grandParent, HashMap<String,String> attrs1, HashMap<String, String> attrs2){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attrsOR = attrs1;
        this.attrsAND=attrs2;
    }

    /**
     *
     * @param name name of the attribute node
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param parent Parent of the attribute node to be searched for
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     *
     * @param grandParent Grandparent the the Element Node
     */
    public void setGrandParent(String grandParent) {
        this.grandParent = grandParent;
    }

    /**
     *
     * @param attrs Addition of Attribute conditions with OR Condition as Hashmap
     */
    public void setAttrsOR(HashMap<String,String> attrs) {
        this.attrsOR = attrs;
    }

    /**
     *
     * @param attrs Addition of Attribute conditions with AND Condition as Hashmap
     */
    public void setAttrsAND(HashMap<String,String> attrs) { this.attrsAND = attrs; }

    public void addAttribute(String key,int flag){
        addAttribute(key,"",flag);
    }

    /**
     *
     * @param key Key for the new attribute
     * @param value Value related to the attribute
     * @param flag IF 0 then OR otherwise AND
     */
    public void addAttribute(String key, String value,int flag){
        if(flag==0)
            this.attrsOR.put(key, value);
        else
            this.attrsAND.put(key,value);
    }

    /**
     *
     * @return Returns the XPath on the given constraints.
     */

    public String getXpath(){
        xpath += "//";
        xpath += this.grandParent;
        addSlash();
        xpath += this.parent;
        addSlash();
        xpath += this.name;
        System.out.println(xpath);
        if(!attrsOR.isEmpty() || !attrsAND.isEmpty())
            xpath +="[ ";
        for(String key: attrsOR.keySet()){
            if(!attrsOR.get(key).equals(""))
                xpath += ("@"+key+"='"+ attrsOR.get(key))+"'";
            else
                xpath += ("@"+key);
            xpath += " or ";
        }
        if(xpath.endsWith("or "))
            xpath=xpath.substring(0,xpath.length()-3);
        if(attrsAND.isEmpty())
            xpath+="]";
        else
            xpath+="and ";
        System.out.println(xpath);
        for(String key:attrsAND.keySet())
        {
            if(!attrsAND.get(key).equals(""))
                xpath += ("@"+key+"='"+ attrsAND.get(key)+"'");
            else
                xpath += ('@'+key);
            xpath += " and ";
        }
        if(xpath.endsWith("and "))
            xpath=xpath.substring(0,xpath.length()-4);
        if(!attrsAND.isEmpty())
            xpath+="]";
        addSlash();
        if(xpath.substring(xpath.length()-1).equals("/"))
            xpath=xpath.substring(0,xpath.length()-1);
       return xpath;
    }

    /**
     * Utility Function to add Slash for Xpath.
     */
    private void addSlash(){
        String slash = "/";
        String lastChar = xpath.substring(xpath.length()-1);
        if(!Objects.equals(lastChar, "/"))
            xpath += "/";
    }
}
