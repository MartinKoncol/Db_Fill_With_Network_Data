package com.martink.downloadanddbfill;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class DocumentBuilder {

    public void DataParsing(String fileLocation) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File("C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources\\20210331_OB_573060_UZSZ.xml"));

        Document documento = readXMLDocumentFromFile("C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources\\20210331_OB_573060_UZSZ.xml");

        Element root = documento.getDocumentElement();
        System.out.println(root.getNodeName());

        nodeListing(document, "vf:Obec");
        nodeListing(document, "vf:CastObce");


    }

    public static void nodeListing(Document document, String tagName) {

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

    public static void listValues(Element eElement, String tagName) {

        if (tagName == "vf:Obec") {
            System.out.println("obi:Nazev : " + eElement.getElementsByTagName("obi:Nazev").item(0).getTextContent());
            System.out.println("obi:Kod : " + eElement.getElementsByTagName("obi:Kod").item(0).getTextContent());
        }
        else if (tagName == "vf:CastObce"){
            System.out.println("obi:Kod : " + eElement.getElementsByTagName("obi:Kod").item(0).getTextContent());
            System.out.println("coi:Nazev : " + eElement.getElementsByTagName("coi:Nazev").item(0).getTextContent());
            System.out.println("coi:Kod : " + eElement.getElementsByTagName("coi:Kod").item(0).getTextContent());
        }
    }

    public static void DataValidator(Document document)
            throws IOException, SAXException {
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new File("C:\\Dev\\Udemy\\Db_Fill_With_Network_Data\\src\\main\\resources\\20210331_OB_573060_UZSZ.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
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
