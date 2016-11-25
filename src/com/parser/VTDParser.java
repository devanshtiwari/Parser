package com.parser;

import com.ximpleware.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * VTDParser implements all the functionalities of Parsing (along with {@link Element} to acheive the goal.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class VTDParser implements Parser {

    private VTDGen vg;
    static VTDNav vn;
    static XMLModifier xm;

    /**
     * Constructor generates new VTDGen and XMLModifier.
     */
    VTDParser() {
        vg = new VTDGen();
        xm = new XMLModifier();
    }

    /**
     * Returns autopilot which helps to move the cursor thorughout the file.
     * @return Returns Autopilot
     * @throws XMLParsingException
     */
    private AutoPilot getAutoPilot() throws XMLParsingException {
        AutoPilot ap=new AutoPilot();
        if(vn!=null)
            ap.bind(vn);
        else
            throw new XMLParsingException("vn is NULL");
        return ap;
    }

    /**
     * Parse binds the XMLModifier and parses the file. This is important to make the changes in the file.
     * @param file
     */
    public void parse(File file) {
        try {
            if((vg.parseFile(file.getCanonicalPath(),false))) {
                vn=vg.getNav();
                xm.bind(vn);
            }
        } catch (IOException | ModifyException e) {
            e.printStackTrace();
        }

    }

    /**
     * writeChanges is called when changes are made to the file and it is to be written back on the disk.
     * @param file
     */
    static void writeChanges(File file){
        try {
            xm.output(new FileOutputStream(file));
            System.gc();
        } catch (IOException | ModifyException | TranscodeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns first tag (element) of the file.
     * @return Evaluated index
     */

    private int getRootElement() {
        try {
            AutoPilot ap = getAutoPilot();
            ap.selectXPath("/*");
            return ap.evalXPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Return root element name, by converting its index to string.
     * @return String with root element name
     */
    public String getRootElementName(){
        try {
            return vn.toString(getRootElement());
        } catch (NavException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * If all the elements of string paramter is present in the root element, it returns true, otherwise false
     * @param checkStr
     * @return
     */
    public Boolean checkRootFor(String[] checkStr)
    {
        try {
            if(checkStr.length==0)
                return true;
            for(String str: checkStr) {
                System.out.println("here");
                if(vn.matchRawTokenString(getRootElement(), str))
                    return true;
            }
        } catch (NavException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks for only one String in the root, returns true or false
     * @param checkStr
     * @return
     */
    public Boolean checkRootFor(String checkStr)
    {
        String str[]={checkStr};
        return checkRootFor(str);
    }

    /**
     * Creates element by initializing Element object and taking parameter Xpath. Xpath is generated using {@link com.xpathgenerator}.
     * @param xPath
     * @return {@link Element} Object
     */
    public Element createElement(String xPath){
        try {
            Element newElement = new Element(getAutoPilot());
            newElement.ap.selectXPath(xPath);
            return newElement;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}