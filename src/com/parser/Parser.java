package com.parser;

import java.io.File;

/**
 * Parser interface can be implement to any number of Parsers.
 */
public interface Parser {
    void parse(File file);
    String getRootElementName();
    Boolean checkRootFor(String check);
    Boolean checkRootFor(String[] check);
}
