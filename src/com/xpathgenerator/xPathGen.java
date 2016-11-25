package com.xpathgenerator;

import java.util.Objects;

/**
 * <h1>XPath Generator</h1>
 * <p>Xpath Generator generates the final Xpath using the objects of Tag, which contains the value of attributes, names etc. </p>
 * @author Avinash and Devansh
 * @since 2016-11-14
 */
class xPathGen {
    /**
     * This is the method which will generate XPath, whenever called, using the values of objects of Tag and Attribute.
     * @param tag
     * @return
     */
    String getXpath(Tag tag){
        String xpath = "";
        xpath += "//";
        xpath += tag.grandParent;
        xpath = addSlash(xpath);
        xpath += tag.parent;
        xpath = addSlash(xpath);
        xpath += tag.name;
        int count = 0;
        if (!tag.attributes.isEmpty()) {
            xpath += "[";
            for (Attribute attr : tag.attributes) {
                count++;
                if (!attr.getValue().equals(""))
                    xpath += ("@" + attr.getName() + "='" + attr.getValue() + "'");
                else
                    xpath += ("@" + attr.getName());
                if (count != (tag.attributes.size()))
                    xpath += " "+attr.getCondition().getValue()+" ";
            }
            xpath += "]";
        }
        if (xpath.substring(xpath.length() - 1).equals("/") && !xpath.substring(xpath.length() - 2).equals("//"))
            xpath = xpath.substring(0, xpath.length() - 1);

        return xpath;
    }

    /**
     * Utility Function to add Slash for Xpath.
     */
    private String addSlash(String xpath){
        String slash = "/";
        String lastChar = xpath.substring(xpath.length()-1);
        if(!Objects.equals(lastChar, "/"))
            xpath += "/";

        return xpath;
    }
}
