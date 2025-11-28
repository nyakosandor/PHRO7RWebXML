package domphro7r1015;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

class DOMPHRO7R {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("orarendPHRO7R.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            System.out.println("Root element: " + document.getDocumentElement().getNodeName());

            printNode(document.getDocumentElement(), "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }

        Element elem = (Element) node;
        System.out.println(indent + "element: " + elem.getNodeName());

        NamedNodeMap attrMap = elem.getAttributes();
        int attrCount = attrMap.getLength();
        for (int i = 0; i < attrCount; i++) {
            Node attr = attrMap.item(i);
            System.out.println(indent + "  @" + attr.getNodeName() + " = " + attr.getNodeValue());
        }

        NodeList children = node.getChildNodes();
        int childCount = children.getLength();
        for (int i = 0; i < childCount; i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE) {
                String text = child.getTextContent().trim();
                if (!text.isEmpty()) {
                    System.out.println(indent + "  text: " + text);
                }
            } else {
                printNode(child, indent + "    ");
            }
        }
    }
}
