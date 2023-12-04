package com.martink.downloadanddbfill;

import static com.martink.downloadanddbfill.Constants.ELEMENT_MUNICIPALITY_SECTION_NAME;
import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY_SECTION;
import static com.martink.downloadanddbfill.Constants.ELEMENT_MUNICIPALITY_CODE;
import static com.martink.downloadanddbfill.Constants.ELEMENT_MUNICIPALITY_SECTION_CODE;
import static com.martink.downloadanddbfill.Constants.ELEMENT_MUNICIPALITY_NAME;


import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocumentBuilder {

    private Document document;

    public void dataParsing(String fileLocation) throws
            ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(new File(fileLocation));
    }

    public void nodeListing(String tagName, SQLiteJDBC dbIns) {

        NodeList nList = document.getElementsByTagName(tagName);

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                listValues(eElement, tagName, dbIns);
            }
        }
    }

    public void listValues(Element eElement, String tagName, SQLiteJDBC dbIns) {
        if (tagName.equals(TAG_NAME_MUNICIPALITY)) {

            String field1 = eElement.getElementsByTagName(ELEMENT_MUNICIPALITY_CODE).item(0).getTextContent();
            String field2 = eElement.getElementsByTagName(ELEMENT_MUNICIPALITY_NAME).item(0).getTextContent();

            dbIns.databaseInsert(tagName, field1, field2, "");

        } else if (tagName.equals(TAG_NAME_MUNICIPALITY_SECTION)) {

            String field1 = eElement.getElementsByTagName(ELEMENT_MUNICIPALITY_CODE).item(0).getTextContent();
            String field3 = eElement.getElementsByTagName(ELEMENT_MUNICIPALITY_SECTION_NAME).item(0).getTextContent();
            String field4 = eElement.getElementsByTagName(ELEMENT_MUNICIPALITY_SECTION_CODE).item(0).getTextContent();

            dbIns.databaseInsert(tagName, field1, field3, field4);

        }

    }

}
