package com.xpathgenerator;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    String name = "", parent = "", grandParent = "", xpath ="";
    List<Attribute> attributes = new ArrayList<>();
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
    public Tag(String name, String parent, String grandParent, List<Attribute> attributes){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attributes = attributes;
    }
    public String getXpath(){
        this.xpath = xPathGen.getXpath(this);
        return xpath;
    }
    public String combineXpath(Tag t){
        String combinedXpath = "";
        combinedXpath = this.getXpath()+"|"+ t.getXpath();
        return combinedXpath;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public String getGrandParent() {
        return grandParent;
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

    public void addAttribute(String name){
        addAttribute(name,"");
    }
    public void addAttribute(String name, Attribute.Condition condition){
        addAttribute(name,"", condition);
    }

    public void addAttribute(String name, String value, Attribute.Condition condition){
        Attribute attr = new Attribute(name,value,condition);
        attributes.add(attr);
    }

    public void addAttribute(String name, String value) {
        //Default OR
        this.addAttribute(name,value, Attribute.Condition.OR);
    }
    public void updateAttrValue(String name, String value){
        Attribute attr = getAttrByName(name);
        attr.setValue(value);
    }
    public void updateAttrCondition(String name, Attribute.Condition condition){
        Attribute attr = getAttrByName(name);
        attr.setCondition(condition);
    }
    public void updateAttrName(String oldName, String newName){
        Attribute attr = getAttrByName(oldName);
        if(!newName.equals(""))
            attr.setName(newName);
    }
    public Attribute getAttrByName(String name){
        for(Attribute attr: attributes){
            if(attr.getName().equals(name))
                return attr;
        }
        return null;
    }
    public boolean hasAttribute(String name){
        for(Attribute attr: attributes){
            if(attr.getName().equals(name))
                return true;
        }
        return false;
    }
    public void removeAttr(String name){
        for(Attribute attr: attributes){
            if(attr.getName().equals(name)){
                attributes.remove(attr);
                break;
            }
        }
    }
}
