package com.martink.downloadanddbfill;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import java.io.File;
import java.sql.SQLException;

public class DocumentBuilder {

    Document document;

    public void dataParsing(String fileLocation) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(new File("C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources\\20210331_OB_573060_UZSZ.xml"));

        Document documento = readXMLDocumentFromFile("C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources\\20210331_OB_573060_UZSZ.xml");

        Element root = documento.getDocumentElement();
        System.out.println(root.getNodeName());

    }

    public void nodeListing( String tagName) throws SQLException {

        NodeList nList = document.getElementsByTagName(tagName);
        System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                listValues(eElement,tagName);
            }
        }
    }

    public  void listValues(Element eElement, String tagName) throws SQLException {
        JDBC dbIns = new JDBC ();

        if (tagName.equals("vf:Obec")) {
            String obecfield1 = eElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
            String obecfield2 = eElement.getElementsByTagName("obi:Nazev").item(0).getTextContent();

            dbIns.databaseInsert(obecfield1,obecfield2,"");
            System.out.println("obec:Nazev : " + obecfield1);
            System.out.println("obec:Kod   : " + obecfield2);
        }
        else if (tagName.equals("vf:CastObce")){
            String obecfield1 = eElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
            String obecfield3 = eElement.getElementsByTagName("coi:Nazev").item(0).getTextContent();
            String obecfield4 = eElement.getElementsByTagName("coi:Kod").item(0).getTextContent();

            dbIns.databaseInsert(obecfield1,obecfield3,obecfield4);
            System.out.println("obec:Kod : " + obecfield1);
            System.out.println("-------------------------------------");
            System.out.println("\tcastObce:Nazev : " + obecfield3);
            System.out.println("\tcastObce:Kod   : " + obecfield4);
            System.out.println("-------------------------------------");
        }
    }
    
    public static Document readXMLDocumentFromFile(String fileNameWithPath) throws Exception {

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(new File(fileNameWithPath));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        return document;
    }

}
