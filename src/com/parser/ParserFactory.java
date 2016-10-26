package com.parser;

public class ParserFactory {
    private static volatile Parser INSTANCE;
    public enum Parsers{
        VTD
    }

    private ParserFactory(){}

    public static Parser getParser(Parsers parserName)  {
        if(INSTANCE == null){
            synchronized (ParserFactory.class){
                if(INSTANCE == null){
                    if(parserName == Parsers.VTD){
                        INSTANCE = new VTDParser();
                    }
                    else{
                        try {
                            throw new XMLParsingException("Parser is not supported.");
                        } catch (XMLParsingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return INSTANCE;
    }
}

