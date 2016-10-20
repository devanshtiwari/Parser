package com.parser;

import java.io.File;

/**
 * Created by avinaana on 10/19/2016.
 */
public interface ParserInterface {
    void parse(File file);
    String getRootElementName();
    Boolean checkRootFor(String check);
    Boolean checkRootFor(String[] check);
    Boolean hasAttr(String attr);

}