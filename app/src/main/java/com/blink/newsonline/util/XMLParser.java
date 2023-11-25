package com.blink.newsonline.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

    public Document getDocument(String xml) {
        Document document;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            is.setEncoding("UTF-8");
            document = db.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
        return document;
    }

    public String getValue(Element item, String name) {
        NodeList nodes = item.getElementsByTagName(name);
        return getTextNodeValue(nodes.item(0));
    }

    private String getTextNodeValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    } else if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
                        return child.getTextContent();
                    }
                }
            }
        }
        return "";
    }

    public String[] getImgSource(String valueElement) {
        char[] arrStr = valueElement.toCharArray();
        int length = arrStr.length;
        int firstIdx2 = 0, lastIdx2 = 0;
        int firstIdx3 = 0;
        int count = 0;
        for (int i = 8; i < length; ++i) {
            if (arrStr[i] == '\"') {
                count += 1;
                switch (count) {
                    case 3:
                        firstIdx2 = i + 1;
                        break;
                    case 4:
                        lastIdx2 = i;
                }
            } else if (count >= 4) {
                if (arrStr[i] == 'r') {
                    firstIdx3 = i + 2;
                    break;
                }
            }
        }

        String linkImg = valueElement.substring(firstIdx2, lastIdx2);
        String description = valueElement.substring(firstIdx3, length - 1);
        String[] data = new String[2];
        data[0] = linkImg;
        data[1] = description;
        return data;
    }
}