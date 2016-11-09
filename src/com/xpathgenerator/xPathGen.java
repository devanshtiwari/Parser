package com.xpathgenerator;

import java.util.Objects;


class xPathGen {
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
