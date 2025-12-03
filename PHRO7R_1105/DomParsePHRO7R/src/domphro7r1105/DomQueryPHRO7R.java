package domphro7r1105;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

class DOMQueryPHRO7R {

    public static void main(String[] args) {
        try {
            File inputFile = new File("PHRO7Rhallgato.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            System.out.println("Root element: " + document.getDocumentElement().getNodeName());

            NodeList lastNameNodeList = document.getElementsByTagName("vezeteknev");

            for (int i = 0; i < lastNameNodeList.getLength(); i++) {
                Node lastNameNode = lastNameNodeList.item(i);

                if (lastNameNode.getNodeType() == Node.ELEMENT_NODE) {
                    String lastName = lastNameNode.getTextContent().trim();
                    System.out.println((i + 1) + ". student's last name: " + lastName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
