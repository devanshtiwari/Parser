package com.parser;

public class ParserFactory {
    private volatile ParserInterface INSTANCE;

    public enum Parsers{
        VTD,XOM
    }

    private ParserFactory(){}

    public ParserInterface getParser(Parsers parserName) throws Exception {
        if(INSTANCE == null){
            synchronized (ParserFactory.class){
                if(INSTANCE == null){
                    if(parserName == Parsers.VTD){
                        INSTANCE = new VTDParser();
                    }
                    else{
                        throw new UnsupportedParser("Parser is not supported.");
                    }
                }
            }
        }
        return INSTANCE;
    }
}

class UnsupportedParser extends Exception{
    public UnsupportedParser(String message) {
        super(message);
    }
}
