package com.xpathgenerator;

import java.util.HashMap;
import java.util.Objects;


class xPathGen {
    public String getXpath(Tag tag){
        String xpath = "";
        xpath += "//";
        xpath += tag.grandParent;
        addSlash(xpath);
        xpath += tag.parent;
        addSlash(xpath);
        xpath += tag.name;

        if (!tag.attrsOR.isEmpty() || !tag.attrsAND.isEmpty())
            xpath += "[ ";
        for (String key : tag.attrsOR.keySet()) {
            if (!tag.attrsOR.get(key).equals(""))
                xpath += ("@" + key + "='" + tag.attrsOR.get(key)) + "'";
            else
                xpath += ("@" + key);
            xpath += " or ";
        }
        if (xpath.endsWith("or "))
            xpath = xpath.substring(0, xpath.length() - 3);
        if (tag.attrsAND.isEmpty() && !tag.attrsOR.isEmpty())
            xpath += "]";
        else if (!tag.attrsAND.isEmpty())
            xpath += "and ";
        for (String key : tag.attrsAND.keySet()) {
            if (!tag.attrsAND.get(key).equals(""))
                xpath += ("@" + key + "='" + tag.attrsAND.get(key) + "'");
            else
                xpath += ('@' + key);
            xpath += " and ";
        }
        if (xpath.endsWith("and "))
            xpath = xpath.substring(0, xpath.length() - 4);
        if (!tag.attrsAND.isEmpty())
            xpath += "]";
        addSlash(xpath);
        if (xpath.substring(xpath.length() - 1).equals("/"))
            xpath = xpath.substring(0, xpath.length() - 1);

        return xpath;
    }

    /**
     * Utility Function to add Slash for Xpath.
     */
    private void addSlash(String xpath){
        String slash = "/";
        String lastChar = xpath.substring(xpath.length()-1);
        if(!Objects.equals(lastChar, "/"))
            xpath += "/";
    }
}
