package xpathphro7r;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class xPathModifyPHRO7R {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("studentPHRO7R.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "/class/student[@id='01']";
            NodeList nodeList = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

            if (nodeList.getLength() == 0) {
                System.out.println("No student with id='01' found.");
                return;
            }

            Node node = nodeList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) node;

                NodeList firstNameNodeList = studentElement.getElementsByTagName("keresztnev");
                if (firstNameNodeList.getLength() > 0) {
                    Node firstNameNode = firstNameNodeList.item(0);

                    firstNameNode.setTextContent("Ádám");
                }

                System.out.println("Modified student (id=01):");
                System.out.println("Student ID   : " + studentElement.getAttribute("id"));
                System.out.println("  First name : "
                        + studentElement.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("  Last name  : "
                        + studentElement.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("  Nickname   : "
                        + studentElement.getElementsByTagName("becenev").item(0).getTextContent());
                System.out.println("  Age        : "
                        + studentElement.getElementsByTagName("kor").item(0).getTextContent());
            }

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
