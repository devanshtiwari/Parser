package com.parser;

public class ParserFactory {
    private static volatile ParserInterface INSTANCE;

    public enum Parsers{
        VTD,XOM
    }

    private ParserFactory(){}

    public static ParserInterface getParser(Parsers parserName) throws Exception {
        if(INSTANCE == null){
            synchronized (ParserFactory.class){
                if(INSTANCE == null){
                    if(parserName == Parsers.VTD){
                        INSTANCE = new VTDParser();
                    }
                    else{
                        throw new XMLParsingException("Parser is not supported.");
                    }
                }
            }
        }
        return INSTANCE;
    }
}

