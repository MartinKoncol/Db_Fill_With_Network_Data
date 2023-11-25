package com.martink.downloadanddbfill;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import java.io.File;
import java.sql.SQLException;

public class DocumentBuilder {

    private Document document;

    public void dataParsing(String fileLocation) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(new File(fileLocation));
    }

    public void nodeListing( String tagName,JDBC dbIns) throws SQLException {

        NodeList nList = document.getElementsByTagName(tagName);

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                listValues(eElement, tagName, dbIns);
            }
        }
    }

    public  void listValues(Element eElement, String tagName,JDBC dbIns) throws SQLException {
        if (tagName.equals("vf:Obec")) {

            String field1 = eElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
            String field2 = eElement.getElementsByTagName("obi:Nazev").item(0).getTextContent();

            dbIns.databaseInsert(tagName,field1,field2,"");

              }
        else if (tagName.equals("vf:CastObce")){

            String field1 = eElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
            String field3 = eElement.getElementsByTagName("coi:Nazev").item(0).getTextContent();
            String field4 = eElement.getElementsByTagName("coi:Kod").item(0).getTextContent();

            dbIns.databaseInsert(tagName,field1,field3,field4);

         }

    }

}
