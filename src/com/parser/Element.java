package com.parser;

import com.ximpleware.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.parser.VTDParser.*;

/**
 * This class Element deals with all the VTD XML Parser {@link VTDParser} and  operations. Operations on the Element, like addition of attribute,
 * deletion of attribute, modification of attribute and modification of whole element is done in the class.
 * @author Devansh and Avinash
 * @since 2016-11-14
 */
public class Element {
    AutoPilot ap;

    /**
     * Constructor initializes the AutoPilot. Element is called in VTDParser.
     * @param ap
     */
    Element(AutoPilot ap){
        this.ap = ap;
    }

    /**
     * This function evaluates the Autopilot and moves the cursor to next position/node.
     * @return returns non zero value if moved to next node, -1 if no nodes are left.
     */
    public int next(){
        try {
            return this.ap.evalXPath();
        } catch (XPathEvalException | NavException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    /**
     * This function removes the whole tag. for example, it will remove whole {@code <tag attr="hello">tag1</tag>} .
     * @param file Takes the file as parameter to write the changes as it is executed.
     */
    public void removeElement(File file){
        long elementFragment= 0;
        try {
            elementFragment = vn.getElementFragment();
            xm.remove(vn.expandWhiteSpaces(elementFragment,VTDNav.WS_TRAILING));
            writeChanges(file);
        } catch (NavException | ModifyException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param attr Checks for the attribute attr in the Element.
     * @return Returns true or false.
     */
    public Boolean hasAttr(String attr){
        try {
            if(vn.hasAttr(attr))
                return true;
        } catch (NavException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param attr
     * @return String with the value of the attribute attr
     */
    public String getAttrVal(String attr){
        if(hasAttr(attr))
            try {
                return vn.toString(vn.getAttrVal(attr));
            } catch (NavException e) {
                e.printStackTrace();
            }

        return null;
    }

    /**
     * Removes the attribute attr. It takes file as input so as to write the changes atraight away.
     * @param attr
     * @param file
     */
    public void removeAttribute(String attr, File file){
        try {
            if(vn.hasAttr(attr)){
                xm.removeAttribute(vn.getAttrVal(attr)-1);
                writeChanges(file);
            }
        } catch (NavException | ModifyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates Attribute Value of attr to updatedVal. Takes File as input to write the changes staight away.
     * @param attr
     * @param updatedVal
     * @param file
     */
    public void updateAttr(String attr,String updatedVal,File file){
        try {
            if(vn.hasAttr(attr)){
                xm.updateToken(vn.getAttrVal(attr),updatedVal);
                writeChanges(file);
            }
            else{
                throw new XMLParsingException("attr does not exit!");
            }
        } catch (NavException | XMLParsingException | ModifyException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts attribute at the beginning of the Tag.
     * @param insert it contains attribute in the form attr="attrval" which is inserted in the Tag.
     * @param file Takes file as input to write changes.
     */
    public void insertAttr(String insert,File file){
        try {
            xm.insertAttribute(" "+insert);
            writeChanges(file);
        } catch (ModifyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function inserts attribute at a specific index in the tag. This method is created so as to implement the method insert
     * Attribute at End.
     * @param index Index at which attribute to be inserted
     * @param attr Attribute to be inserted
     * @throws ModifyException
     * @throws UnsupportedEncodingException
     */
    private void insertAttrAtIndex(int index, String attr) throws ModifyException, UnsupportedEncodingException {
        int type = vn.getTokenType(index);
        int offset = vn.getTokenOffset(index);
        int len = vn.getTokenLength(index)&0xffff;

        if(vn.getEncoding() < VTDNav.FORMAT_UTF_16BE)
            xm.insertBytesAt(offset+len+1, attr.getBytes(getCharSet()));
        else
            xm.insertBytesAt(offset+len+1<<1,attr.getBytes(getCharSet()));
    }


    /**
     * This method inserts attribute at end of the tag. If there are several attributes, it will insert new attribute after last attribute.
     * @param insertAttr
     * @param file
     */
    public void insertAtEnd(String insertAttr, File file) {
        AutoPilot apAttr = new AutoPilot();
        int i, j = -1, m = 0, diff = 0;
        ArrayList<Integer> diffArray = new ArrayList<>();
        String indent,insert = "";
        apAttr.bind(vn);
        try {
            apAttr.selectXPath("@*");
            while((i = apAttr.evalXPath()) != -1) {
                diff = vn.getTokenOffset(i) - diff;
                if(m == 0) {
                    diff = 2;
                    m++;
                }
                if(diff != 1) {
                    diffArray.add(diff);
                }
                diff = vn.getTokenOffset(i+1) + vn.getTokenLength(i+1)+1;
                j = i+1;
            }
            int whiteSpace = 0 ;
            if(diffArray.size() != 0) {
                whiteSpace = mostCommon(diffArray) - 2;
                indent = new String(new char[whiteSpace]).replace("\0", " ");
                if (whiteSpace != 0)
                    indent = "\r\n" + indent;
                else
                    indent = " ";
                insert = indent + insertAttr;
                insertAttrAtIndex(j, insert);
                writeChanges(file);
            }
            else
            {
                insertAttr(insertAttr,file);
            }
        } catch (XPathParseException | XPathEvalException | NavException | ModifyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    //Utility Functions

    /**
     * A utility function just to find the most number occurring spaces between two attributes.
     * @param list
     * @param <T>
     * @return
     */
    private  <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }


        if (max != null) {
            return max.getKey();
        }
        return null;
    }

    /**
     * Utitlity from VTDParser, just to implmenet insert at the end.
     * @return
     * @throws ModifyException
     */
    private String getCharSet() throws ModifyException {
        String charSet;
        switch(vn.getEncoding()){
            case VTDNav.FORMAT_ASCII:
                charSet = "ASCII";
                break;
            case VTDNav.FORMAT_ISO_8859_1:
                charSet = "ISO8859_1";
                break;
            case VTDNav.FORMAT_UTF8:
                charSet = "UTF8";
                break;
            case VTDNav.FORMAT_UTF_16BE:
                charSet = "UnicodeBigUnmarked";
                break;
            case VTDNav.FORMAT_UTF_16LE:
                charSet = "UnicodeLittleUnmarked";
                break;
            case VTDNav.FORMAT_ISO_8859_2:
                charSet = "ISO8859_2";
                break;
            case VTDNav.FORMAT_ISO_8859_3:
                charSet = "ISO8859_3";
                break;
            case VTDNav.FORMAT_ISO_8859_4:
                charSet = "ISO8859_4";
                break;
            case VTDNav.FORMAT_ISO_8859_5:
                charSet = "ISO8859_5";
                break;
            case VTDNav.FORMAT_ISO_8859_6:
                charSet = "ISO8859_6";
                break;
            case VTDNav.FORMAT_ISO_8859_7:
                charSet = "ISO8859_7";
                break;
            case VTDNav.FORMAT_ISO_8859_8:
                charSet = "ISO8859_8";
                break;
            case VTDNav.FORMAT_ISO_8859_9:
                charSet = "ISO8859_9";
                break;
            case VTDNav.FORMAT_ISO_8859_10:
                charSet = "ISO8859_10";
                break;
            case VTDNav.FORMAT_ISO_8859_11:
                charSet = "x-iso-8859-11";
                break;
            case VTDNav.FORMAT_ISO_8859_12:
                charSet = "ISO8859_12";
                break;
            case VTDNav.FORMAT_ISO_8859_13:
                charSet = "ISO8859_13";
                break;
            case VTDNav.FORMAT_ISO_8859_14:
                charSet = "ISO8859_14";
                break;
            case VTDNav.FORMAT_ISO_8859_15:
                charSet = "ISO8859_15";
                break;
            case VTDNav.FORMAT_WIN_1250:
                charSet = "Cp1250";
                break;
            case VTDNav.FORMAT_WIN_1251:
                charSet = "Cp1251";
                break;
            case VTDNav.FORMAT_WIN_1252:
                charSet = "Cp1252";
                break;
            case VTDNav.FORMAT_WIN_1253:
                charSet = "Cp1253";
                break;
            case VTDNav.FORMAT_WIN_1254:
                charSet = "Cp1254";
                break;
            case VTDNav.FORMAT_WIN_1255:
                charSet = "Cp1255";
                break;
            case VTDNav.FORMAT_WIN_1256:
                charSet = "Cp1256";
                break;
            case VTDNav.FORMAT_WIN_1257:
                charSet = "Cp1257";
                break;
            case VTDNav.FORMAT_WIN_1258:
                charSet = "Cp1258";
                break;
            default:
                throw new ModifyException("Master document encoding not yet supported by XML modifier");
        }
        return charSet;
    }
}
