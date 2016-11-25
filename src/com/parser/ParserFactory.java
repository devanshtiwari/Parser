package com.parser;

/**
 * ParserFactory class have function getParser which returns VTD Parser as of now.
 * It is kept scalable so if further development requires the need to put another parser, one can put in the factory and implement
 * Parser interface as it is done in VTDParser.
 * @author  Avinash and Devansh
 * @since 2016-11-14
 */
public class ParserFactory {
    private static volatile Parser INSTANCE;

    /**
     * ENUM to have multiple parsers. Till now, only one is present.
     */
    public enum Parsers{
        VTD
    }

    private ParserFactory(){}

    /**
     * Method returns VTD Parser and keeps only single instance of Parser as it is static.
     * @param parserName
     * @return
     */
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

