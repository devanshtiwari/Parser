package com.xpathgenerator;

import java.util.HashMap;

public class Tag {
    String name = "", parent = "", grandParent = "", xpath ="";
    HashMap<String,String> attrsOR = new HashMap<>();
    HashMap<String, String> attrsAND = new HashMap<>();
    private xPathGen xPathGen = new xPathGen();
    public static final String OR = "or";
    public static final String AND = "and";
    public Tag(){}
    public Tag(String name){
        this.name = name;
    }
    public Tag(String name, String parent){
        this.name = name;
        this.parent = parent;
    }
    public Tag(String name, String parent, String grandParent){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
    }
    public Tag(String name, String parent, String grandParent, HashMap<String,String> attrs){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attrsOR = attrs;
    }
    public Tag(String name, String parent, String grandParent, HashMap<String,String> attrs1, HashMap<String, String> attrs2){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attrsOR = attrs1;
        this.attrsAND=attrs2;
    }
    public String getXpath(){
        if(this.xpath == ""){
            this.xpath = xPathGen.getXpath(this);
        }
        return xpath;
    }
    public String combineXpath(Tag t){
        String combinedXpath = "";
        combinedXpath = this.getXpath()+"|"+ t.getXpath();
        return combinedXpath;
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
     * @param grandParent Grandparent the the Tag Node
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

    
}
