package com.xpathgenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Xpath Generator</h1>
 * <p>Tag Class handles the creation of the tag, including management of Attribute using {@link Attribute} . This creates the xpath
 * as per requirements of the user.</p>
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class Tag {
    String name = "", parent = "", grandParent = "", xpath ="";
    List<Attribute> attributes = new ArrayList<>();
    private xPathGen xPathGen = new xPathGen();
    public static final String OR = "or";
    public static final String AND = "and";

    /**
     * Tag Constructor takes multiple parameters. It can take none, one, two, three or four parameters.
     */
    public Tag(){}

    /**
     * Name of the tag is set using single parameter.
     * @param name
     */
    public Tag(String name){
        this.name = name;
    }

    /**
     * Name and Parent are set using two parameters.
     * @param name
     * @param parent
     */
    public Tag(String name, String parent){
        this.name = name;
        this.parent = parent;
    }

    /**
     * Name, Parent and Grandparent are set using three parameters.
     * @param name
     * @param parent
     * @param grandParent
     */
    public Tag(String name, String parent, String grandParent){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
    }

    /**
     * Name, parent, grandparent and Attributes in form list of {@link Attribute} can be inserted in a single go.
     * @param name
     * @param parent
     * @param grandParent
     * @param attributes
     */
    public Tag(String name, String parent, String grandParent, List<Attribute> attributes){
        this.name = name;
        this.parent = parent;
        this.grandParent = grandParent;
        this.attributes = attributes;
    }

    /**
     *This method returns XPath using XPathGen Class.
     * @return
     */
    public String getXpath(){
        this.xpath = xPathGen.getXpath(this);
        return xpath;
    }

    /**
     * Xpaths can be combined using this method by giving another xpath.
     * @param t
     * @return
     */
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

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setGrandParent(String grandParent) {
        this.grandParent = grandParent;
    }

    /**
     * Attribute can be added by adding attribute name. This will add empty valued attribute.
     * @param name
     */
    public void addAttribute(String name){
        addAttribute(name,"");
    }

    /**
     * Addition of attribute along with condition of OR or AND
     * @param name
     * @param condition
     */
    public void addAttribute(String name, Attribute.Condition condition){
        addAttribute(name,"", condition);
    }

    /**
     * Addition of Attribute with attribute name and value along with condition of AND or OR.
     * @param name
     * @param value
     * @param condition
     */
    public void addAttribute(String name, String value, Attribute.Condition condition){
        Attribute attr = new Attribute(name,value,condition);
        attributes.add(attr);
    }

    /**
     * Attribute addition with name and value.(Default Condition is OR)
     * @param name
     * @param value
     */
    public void addAttribute(String name, String value) {
        this.addAttribute(name,value, Attribute.Condition.OR);
    }

    /**
     * Update Attribute Value by given name of the attribute and new value
     * @param name
     * @param value
     */
    public void updateAttrValue(String name, String value){
        Attribute attr = getAttrByName(name);
        attr.setValue(value);
    }

    /**
     * Condition of the attribute earlier set can be updated using this method.
     * @param name
     * @param condition
     */
    public void updateAttrCondition(String name, Attribute.Condition condition){
        Attribute attr = getAttrByName(name);
        attr.setCondition(condition);
    }

    /**
     * Attribute name in the xpath can be updated using this method by providing oldName and newName
     * @param oldName
     * @param newName
     */
    public void updateAttrName(String oldName, String newName){
        Attribute attr = getAttrByName(oldName);
        if(!newName.equals(""))
            attr.setName(newName);
    }

    /**
     * Returns attribute by Name.
     * @param name
     * @return
     */
    public Attribute getAttrByName(String name){
        for(Attribute attr: attributes){
            if(attr.getName().equals(name))
                return attr;
        }
        return null;
    }

    /**
     * Checks whether the Xpath contains the attribute by the  given string.
     * @param name
     * @return
     */
    public boolean hasAttribute(String name){
        for(Attribute attr: attributes){
            if(attr.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * Attribute from the Xpath can be removed by providing the name of the attribute.
     * @param name
     */
    public void removeAttr(String name){
        for(Attribute attr: attributes){
            if(attr.getName().equals(name)){
                attributes.remove(attr);
                break;
            }
        }
    }
}
