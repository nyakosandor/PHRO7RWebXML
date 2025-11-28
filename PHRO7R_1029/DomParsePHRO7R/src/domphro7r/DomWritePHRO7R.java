package domphro7r1029;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

class DOMWrite {

    public static void main(String[] args) {
        try {
            File inputFile = new File("hallgatoPHRO7R.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            System.out.println("Root element: " + document.getDocumentElement().getNodeName());;
            printNode(document.getDocumentElement(), "");

            File outputFile = new File("hallgato1PHRO7R.xml");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);

            transformer.transform(source, result);

            System.out.println("Documentum saved: " + outputFile.getAbsolutePath());

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

        NodeList children = elem.getChildNodes();
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
