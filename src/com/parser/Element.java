package com.parser;

import com.ximpleware.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.parser.VTDParser.*;

public class Element {
    AutoPilot ap;
    Element(AutoPilot ap){
        this.ap = ap;
    }
    public int next(){
        try {
            return this.ap.evalXPath();
        } catch (XPathEvalException | NavException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

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
    public Boolean hasAttr(String attr){
        try {
            if(vn.hasAttr(attr))
                return true;
        } catch (NavException e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getAttrVal(String attr){
        if(hasAttr(attr))
            try {
                return vn.toString(vn.getAttrVal(attr));
            } catch (NavException e) {
                e.printStackTrace();
            }

        return null;
    }
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
    public void insertAttr(String insert,File file){
        try {
            xm.insertAttribute(insert);
            writeChanges(file);
        } catch (ModifyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void insertAttrAtIndex(int index, String attr) throws ModifyException, UnsupportedEncodingException {
        int type = vn.getTokenType(index);
        int offset = vn.getTokenOffset(index);
        int len = vn.getTokenLength(index)&0xffff;

        if(vn.getEncoding() < VTDNav.FORMAT_UTF_16BE)
            xm.insertBytesAt(offset+len+1, attr.getBytes(getCharSet()));
        else
            xm.insertBytesAt(offset+len+1<<1,attr.getBytes(getCharSet()));
    }



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
            int whiteSpace = mostCommon(diffArray) - 2;
            indent = new String(new char[whiteSpace]).replace("\0", " ");
            if(whiteSpace != 0)
                indent = "\r\n" + indent;
            else
                indent = " ";
            insert = indent + insertAttr;
            insertAttrAtIndex(j,insert);
            writeChanges(file);
        } catch (XPathParseException | XPathEvalException | NavException | ModifyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    //Utility Functions
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
