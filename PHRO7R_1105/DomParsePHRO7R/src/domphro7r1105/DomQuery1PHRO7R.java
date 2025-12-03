package domphro7r1105;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class DOMQuery1PHRO7R {

    public static void main(String[] args) {
        try {
            File inputFile = new File("orarendPHRO7R.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            System.out.println("Root element: " + document.getDocumentElement().getNodeName());

            List<String> courseNames = new ArrayList<>();
            NodeList subjectNodeList = document.getElementsByTagName("targy");

            for (int i = 0; i < subjectNodeList.getLength(); i++) {
                Node subjectNode = subjectNodeList.item(i);
                if (subjectNode.getNodeType() == Node.ELEMENT_NODE) {
                    String subjectName = subjectNode.getTextContent().trim();
                    if (!subjectName.isEmpty()) {
                        courseNames.add(subjectName);
                    }
                }
            }

            System.out.println("Kurzusnevek: " + courseNames);
            System.out.println();

            NodeList lessonNodeList = document.getElementsByTagName("ora");

            if (lessonNodeList.getLength() > 0) {
                Element firstLessonElement = (Element) lessonNodeList.item(0);

                System.out.println("=== Elso <ora> elem fastruktúrában ===");
                printNode(firstLessonElement, "");
                System.out.println();

                DocumentBuilder singleBuilder = factory.newDocumentBuilder();
                Document singleLessonDocument = singleBuilder.newDocument();

                Element rootElement =
                        singleLessonDocument.createElement(document.getDocumentElement().getNodeName());
                singleLessonDocument.appendChild(rootElement);

                Node importedLesson =
                        singleLessonDocument.importNode(firstLessonElement, true);
                rootElement.appendChild(importedLesson);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                File outputFile = new File("orarendPHRO7R_first.xml");
                DOMSource fileSource = new DOMSource(singleLessonDocument);
                StreamResult fileResult = new StreamResult(outputFile);
                transformer.transform(fileSource, fileResult);

                System.out.println("Elso ora kiirva fajlba: " + outputFile.getAbsolutePath());
                System.out.println();
            }

            List<String> teacherNames = new ArrayList<>();
            NodeList teacherNodeList = document.getElementsByTagName("oktato");

            for (int i = 0; i < teacherNodeList.getLength(); i++) {
                Node teacherNode = teacherNodeList.item(i);
                if (teacherNode.getNodeType() == Node.ELEMENT_NODE) {
                    String teacherName = teacherNode.getTextContent().trim();
                    if (!teacherName.isEmpty()) {
                        teacherNames.add(teacherName);
                    }
                }
            }

            System.out.println("Oktatók: " + teacherNames);
            System.out.println();

            List<String> tuesdayPracticeSubjects = new ArrayList<>();
            lessonNodeList = document.getElementsByTagName("ora");

            for (int i = 0; i < lessonNodeList.getLength(); i++) {
                Element lessonElement = (Element) lessonNodeList.item(i);

                String typeAttr = lessonElement.getAttribute("tipus").trim();

                String dayText = null;
                NodeList dayNodeList = lessonElement.getElementsByTagName("nap");
                if (dayNodeList.getLength() > 0) {
                    dayText = dayNodeList.item(0).getTextContent().trim();
                }

                if ("gyakorlat".equalsIgnoreCase(typeAttr)
                        && "Kedd".equalsIgnoreCase(dayText)) {

                    NodeList subjectNodesInLesson = lessonElement.getElementsByTagName("targy");
                    if (subjectNodesInLesson.getLength() > 0) {
                        String subjectName =
                                subjectNodesInLesson.item(0).getTextContent().trim();
                        if (!subjectName.isEmpty()) {
                            tuesdayPracticeSubjects.add(subjectName);
                        }
                    }
                }
            }

            System.out.println("Összetett lekérdezés – keddi gyakorlatok tárgyai: "
                    + tuesdayPracticeSubjects);

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
