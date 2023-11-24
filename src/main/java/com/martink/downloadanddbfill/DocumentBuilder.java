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
        Document documento = readXMLDocumentFromFile(fileLocation);

        Element root = documento.getDocumentElement();
        System.out.println(root.getNodeName());

    }

    public void nodeListing( String tagName,JDBC dbIns) throws SQLException {

        NodeList nList = document.getElementsByTagName(tagName);
        System.out.println("============================");

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
            String obecfield1 = eElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
            String obecfield2 = eElement.getElementsByTagName("obi:Nazev").item(0).getTextContent();

            dbIns.databaseInsert(tagName,obecfield1,obecfield2,"");

              }
        else if (tagName.equals("vf:CastObce")){
            String obecfield1 = eElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
            String obecfield3 = eElement.getElementsByTagName("coi:Nazev").item(0).getTextContent();
            String obecfield4 = eElement.getElementsByTagName("coi:Kod").item(0).getTextContent();

            dbIns.databaseInsert(tagName,obecfield1,obecfield3,obecfield4);

         }

    }
    
    public static Document readXMLDocumentFromFile(String fileNameWithPath) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(fileNameWithPath));

        document.getDocumentElement().normalize();

        return document;
    }

}
