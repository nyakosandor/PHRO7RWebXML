package domphro7r1105;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

class DOMModify1 {

    public static void main(String[] args) {
        try {
            File inputFile = new File("orarendPHRO7R.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            NodeList lessonList = document.getElementsByTagName("ora");
            if (lessonList.getLength() > 0) {
                Element firstLessonElement = (Element) lessonList.item(0);

                Element teacherElement = document.createElement("oraado");
                teacherElement.setTextContent("Dr. Béla Tanár Úr");
                firstLessonElement.appendChild(teacherElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            System.out.println("=== 1) XML with new <oraado> element ===");
            DOMSource consoleSource = new DOMSource(document);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(consoleSource, consoleResult);

            File outputFile = new File("orarendModify1PHRO7R.xml");
            DOMSource fileSource = new DOMSource(document);
            StreamResult fileResult = new StreamResult(outputFile);
            transformer.transform(fileSource, fileResult);

            lessonList = document.getElementsByTagName("ora");

            for (int i = 0; i < lessonList.getLength(); i++) {
                Element lessonElement = (Element) lessonList.item(i);

                if (lessonElement.hasAttribute("tipus")) {
                    String typeAttrValue = lessonElement.getAttribute("tipus").trim();
                    if ("gyakorlat".equalsIgnoreCase(typeAttrValue)) {
                        lessonElement.setAttribute("tipus", "eloadas");
                    }
                }

                NodeList typeNodeList = lessonElement.getElementsByTagName("tipus");
                if (typeNodeList.getLength() > 0) {
                    Element typeElement = (Element) typeNodeList.item(0);
                    String typeText = typeElement.getTextContent().trim();
                    if ("gyakorlat".equalsIgnoreCase(typeText)) {
                        typeElement.setTextContent("eloadas");
                    }
                }
            }

            System.out.println("\n\n=== 2) Structured tree after type modification ===");
            printNode(document.getDocumentElement(), "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }

        Element element = (Element) node;
        System.out.println(indent + "element: " + element.getNodeName());

        NamedNodeMap attributeMap = element.getAttributes();
        int attributeCount = attributeMap.getLength();
        for (int i = 0; i < attributeCount; i++) {
            Node attribute = attributeMap.item(i);
            System.out.println(indent + "  @" + attribute.getNodeName()
                    + " = " + attribute.getNodeValue());
        }

        NodeList childNodes = element.getChildNodes();
        int childCount = childNodes.getLength();

        for (int i = 0; i < childCount; i++) {
            Node child = childNodes.item(i);

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
